package io.gituhub.jfelixy.petadoptionapi.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum SexEnum {

    MALE("male"),
    FEMALE("female");

    @Getter
    private String sex;

    SexEnum(String sex){
        this.sex = sex;
    }

    public static SexEnum fromString(String sex){
        return Arrays.stream(values())
                .filter(sx -> sx.name().equalsIgnoreCase(sex))
                .findFirst()
                .orElse(null);
    }
}
