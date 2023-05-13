package com.System.VaccineTracking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty(access = Access.READ_ONLY)
    private String nationalId;

    @JsonIgnore
    private String password;

    @NotNull
    @Size(min = 3 , max = 64 )
    private String fullName;

    @NotNull
    private int age;

    @NotNull
    private String governorate;

    @NotNull
    private String gender;

    @NotNull @Min(0) @Max(2)
    private int doses;

    @JsonIgnore
    private Boolean waitingList = false;
}
