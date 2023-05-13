package com.System.VaccineTracking.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotNull
    @Size(min = 13 , max = 13 )
    private String nationalId;

    @NotNull
    @Size(min = 8 , max = 32 )
    private String password;
}
