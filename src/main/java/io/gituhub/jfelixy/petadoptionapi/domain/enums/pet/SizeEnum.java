package io.gituhub.jfelixy.petadoptionapi.domain.enums.pet;

import lombok.Getter;

import java.util.Arrays;


public enum SizeEnum {
    XS("Extra Small"),
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large");

    @Getter
    private String size;

    SizeEnum(String size){
        this.size = size;
    }

    public static SizeEnum fromString(String size){
        return Arrays.stream(values())
                .filter(sz -> sz.name().equalsIgnoreCase(size))
                .findFirst()
                .orElse(null);
    }
}
