package com.leetnex.service;

import com.leetnex.model.Submission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeExecutionService {

    @Value("${code-execution.timeout}")
    private long executionTimeout;

    @Value("${code-execution.max-memory}")
    private long maxMemoryMB;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Submission.SubmissionStatus executeCode(String code, Submission.ProgrammingLanguage language,
            String testInput, String expectedOutput) {
        try {
            Path tempDir = Files.createTempDirectory("leetnex-execution");
            Path sourceFile = createSourceFile(tempDir, code, language);

            ProcessBuilder processBuilder = createProcessBuilder(language, sourceFile);
            processBuilder.directory(tempDir.toFile());

            Process process = processBuilder.start();

            // Send test input
            try (PrintWriter writer = new PrintWriter(process.getOutputStream())) {
                writer.println(testInput);
                writer.flush();
            }

            // Wait for execution with timeout
            boolean finished = process.waitFor(executionTimeout, TimeUnit.MILLISECONDS);

            if (!finished) {
                process.destroyForcibly();
                return Submission.SubmissionStatus.TIME_LIMIT_EXCEEDED;
            }

            int exitCode = process.exitValue();
            if (exitCode != 0) {
                return Submission.SubmissionStatus.RUNTIME_ERROR;
            }

            // Read output
            String output = readOutput(process.getInputStream());
            if (output.trim().equals(expectedOutput.trim())) {
                return Submission.SubmissionStatus.ACCEPTED;
            } else {
                return Submission.SubmissionStatus.WRONG_ANSWER;
            }

        } catch (Exception e) {
            log.error("Error executing code", e);
            return Submission.SubmissionStatus.RUNTIME_ERROR;
        }
    }

    private Path createSourceFile(Path tempDir, String code, Submission.ProgrammingLanguage language)
            throws IOException {
        String fileName = getFileName(language);
        Path sourceFile = tempDir.resolve(fileName);
        Files.write(sourceFile, code.getBytes());
        return sourceFile;
    }

    private ProcessBuilder createProcessBuilder(Submission.ProgrammingLanguage language, Path sourceFile) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        switch (language) {
            case JAVA:
                processBuilder.command("java", "-cp", ".", "Solution");
                // Compile first
                try {
                    Process compileProcess = new ProcessBuilder("javac", sourceFile.toString())
                            .directory(sourceFile.getParent().toFile())
                            .start();
                    compileProcess.waitFor();
                } catch (Exception e) {
                    log.error("Error compiling Java code", e);
                }
                break;
            case PYTHON:
                processBuilder.command("python", sourceFile.toString());
                break;
            case JAVASCRIPT:
                processBuilder.command("node", sourceFile.toString());
                break;
            case CPP:
                String compiledName = "solution";
                processBuilder.command("./" + compiledName);
                // Compile first
                try {
                    Process compileProcess = new ProcessBuilder("g++", "-o", compiledName, sourceFile.toString())
                            .directory(sourceFile.getParent().toFile())
                            .start();
                    compileProcess.waitFor();
                } catch (Exception e) {
                    log.error("Error compiling C++ code", e);
                }
                break;
            default:
                throw new UnsupportedOperationException("Language not supported: " + language);
        }

        return processBuilder;
    }

    private String getFileName(Submission.ProgrammingLanguage language) {
        switch (language) {
            case JAVA:
                return "Solution.java";
            case PYTHON:
                return "solution.py";
            case JAVASCRIPT:
                return "solution.js";
            case CPP:
                return "solution.cpp";
            default:
                throw new UnsupportedOperationException("Language not supported: " + language);
        }
    }

    private String readOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}
