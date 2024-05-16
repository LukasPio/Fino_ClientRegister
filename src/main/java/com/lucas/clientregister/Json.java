package com.lucas.clientregister;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

    public Json(List<ClientResponseDTO> clientData, String message, String elapsedTime) {
        BODY.put("clients", clientData);
        BODY.put("message", message);
        BODY.put("elapsedTime", elapsedTime + "ms");
        BODY.put("timestamp", new Timestamp(System.currentTimeMillis()));
    }

}
