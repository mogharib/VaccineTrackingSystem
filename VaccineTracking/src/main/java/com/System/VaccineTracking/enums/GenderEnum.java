package com.System.VaccineTracking.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public enum GenderEnum {

    MALE("male"),
    FEMALE("female");
    public final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public static List<String> getAllGenderEnums() {
        return Arrays.stream(GenderEnum.values())
                .map(type -> type.value)
                .collect(Collectors.toList());
    }
}
