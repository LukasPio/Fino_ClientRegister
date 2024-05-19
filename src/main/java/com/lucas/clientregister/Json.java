package com.lucas.clientregister;

import com.lucas.clientregister.DTO.ClientListResponseDTO;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import com.lucas.clientregister.DTO.DisabledClientListResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Json {
    private final Map<String, Object> BODY = new HashMap<>();

    public Json(String message, String elapsedTime) {
        BODY.put("message", message);
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
        BODY.put("elapsedTime", elapsedTime + "ms");
    }

    public Json(String message, String elapsedTime, String error) {
        BODY.put("message", message);
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
        BODY.put("elapsedTime", elapsedTime + "ms");
        BODY.put("error", error);
    }

    public Json(ClientListResponseDTO clients, String message, String elapsedTime) {
        BODY.put("clients", clients);
        BODY.put("message", message);
        BODY.put("elapsedTime", elapsedTime + "ms");
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
    }

    public Json(DisabledClientListResponseDTO disabledClients, String message, String elapsedTime) {
        BODY.put("clients", disabledClients);
        BODY.put("message", message);
        BODY.put("elapsedTime", elapsedTime + "ms");
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
    }

    public Json(ClientResponseDTO client, String message, String elapsedTime) {
        BODY.put("client", client);
        BODY.put("message", message);
        BODY.put("elapsedTime", elapsedTime + "ms");
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
    }
}
