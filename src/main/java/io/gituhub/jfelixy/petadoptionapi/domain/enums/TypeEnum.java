package io.gituhub.jfelixy.petadoptionapi.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum TypeEnum {
    CAT("cat"),
    DOG("dog");

    @Getter
    private String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public static TypeEnum fromString(String type) {
        return Arrays.stream(values())
                .filter(ty -> ty.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }


}
