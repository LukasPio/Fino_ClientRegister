package com.lucas.clientregister.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class LogFileWriter {

    private final String logDirPath;

    public LogFileWriter(String logDirPath) {
        this.logDirPath = logDirPath;
    }

    public void writeFileLog(String level, String message) {
        createLogFileIfNotExists(level);
        try (FileWriter fileWriter = new FileWriter(logDirPath + "/" + level + ".log", true)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String log = "timestamp: " + timestamp + ",level: " + level + ",message: " + message + ";\n";
            fileWriter.write(log);
        } catch (IOException e) {
            System.out.println("An error occurred writing at a log file. message: " + e.getMessage());
        }
    }

    public void createLogFileIfNotExists(String level) {
        File logDir = new File(logDirPath);
        if (!logDir.exists()) {
            if (logDir.mkdirs()) {
                System.out.println("Log directory created successfully.");
            } else {
                System.out.println("Failed to create log directory.");
                return;
            }
        }

        File file = new File(logDirPath + "/" + level + ".log");
        try {
            if (file.createNewFile()) {
                System.out.println("Log file created successfully.");
            } else {
                System.out.println("Log file already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred at creating log file. message: " + e.getMessage());
        }
    }
}
