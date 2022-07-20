package com.baranskagata.optometry.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordDto {
    private String currentPassword;
    private String newPassword;
}
