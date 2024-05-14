package com.lucas.clientregister.utils;

import com.lucas.clientregister.ClientRegisterApplication;

import java.sql.Timestamp;

public class Logger {
    public static void info(String message) {
                ClientRegisterApplication.applicationLogger.warn(
                        "\n{\ntimestamp - {}\nlevel - INFO\nmessage - {}\n}",
                        new Timestamp(System.currentTimeMillis()),
                        message
                );
    }
    public static void warn(String message) {
        ClientRegisterApplication.applicationLogger.warn(
                "\n{\ntimestamp - {}\nlevel - WARN\nmessage - {}\n}",
                new Timestamp(System.currentTimeMillis()),
                message
        );
    }
    public static void error(String message) {
        ClientRegisterApplication.applicationLogger.warn(
                "\n{\ntimestamp - {}\nlevel - ERROR\nmessage - {}\n}",
                new Timestamp(System.currentTimeMillis()),
                message
        );
    }
}
