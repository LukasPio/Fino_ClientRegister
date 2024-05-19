package com.lucas.clientregister.DTO;

import com.lucas.clientregister.Model.DisabledClientModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DisabledClientListResponseDTO {
    private List<DisabledClientModel> disabledClientList;
}
