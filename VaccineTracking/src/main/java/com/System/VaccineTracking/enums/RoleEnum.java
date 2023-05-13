package com.System.VaccineTracking.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");
    public final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public static List<String> getAllRoleEnums() {
        return Arrays.stream(RoleEnum.values())
                     .map(type -> type.value)
                     .collect(Collectors.toList());
    }
}
