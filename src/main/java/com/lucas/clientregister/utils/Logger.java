package com.lucas.clientregister.utils;

import com.lucas.clientregister.ClientRegisterApplication;

import java.sql.Timestamp;

public class Logger {
    public static void log(String level, String message) {
        switch (level) {
            case "INFO":
                ClientRegisterApplication.applicationLogger.info(
                        "\n{\ntimestamp - {}\nlevel - {}\nmessage - {}\n}",
                        new Timestamp(System.currentTimeMillis()),
                        level,
                        message
                );
                break;
            case "WARN":
                ClientRegisterApplication.applicationLogger.warn(
                        "\n{\ntimestamp - {}\nlevel - {}\nmessage - {}\n}",
                        new Timestamp(System.currentTimeMillis()),
                        level,
                        message
                );
                break;
            case "ERROR":
                ClientRegisterApplication.applicationLogger.error(
                        "\n{\ntimestamp - {}\nlevel - {}\nmessage - {}\n}",
                        new Timestamp(System.currentTimeMillis()),
                        level,
                        message
                );
                break;
        }
    }
}
