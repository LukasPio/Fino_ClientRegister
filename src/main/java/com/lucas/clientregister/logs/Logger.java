package com.lucas.clientregister.logs;

import com.lucas.clientregister.ClientRegisterApplication;

import java.sql.Timestamp;

public class Logger {

    private static final LogFileWriter logFileWriter = new LogFileWriter("C:\\Users\\lucas\\OneDrive\\Documentos\\programacao\\java\\Fino_ClientRegister\\logs");

    public static void info(String message) {
                ClientRegisterApplication.applicationLogger.info(
                        "\n{\ntimestamp - {}\nlevel - INFO\nmessage - {}\n}",
                        new Timestamp(System.currentTimeMillis()),
                        message
                );
                logFileWriter.writeFileLog("INFO", message);
    }
    public static void warn(String message) {
        ClientRegisterApplication.applicationLogger.warn(
                "\n{\ntimestamp - {}\nlevel - WARN\nmessage - {}\n}",
                new Timestamp(System.currentTimeMillis()),
                message
        );
        logFileWriter.writeFileLog("WARN", message);
    }
    public static void error(String message) {
        ClientRegisterApplication.applicationLogger.error(
                "\n{\ntimestamp - {}\nlevel - ERROR\nmessage - {}\n}",
                new Timestamp(System.currentTimeMillis()),
                message
        );
        logFileWriter.writeFileLog("ERROR", message);
    }
}
