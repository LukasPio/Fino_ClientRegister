package com.lucas.clientregister.DTO;

import com.lucas.clientregister.Model.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClientListResponseDTO {
    private List<ClientModel> clientList;
}
