package io.gituhub.jfelixy.petadoptionapi.domain.enums;

import lombok.Getter;

import java.util.Arrays;

public enum TemperamentEnum {
    FRIENDLY("friendly"),
    AGGRESSIVE("aggressive"),
    FIERCE("fierce"),
    CURIOUS("curious"),
    FEARFUL("fearful"),
    CALM("calm"),
    PLAYFUL("playful"),
    TERRITORIAL("territorial"),
    INDEPENDENT("independent"),
    SOCIAL("social"),
    PROTECTIVE("protective"),
    NERVOUS("nervous"),
    DOCILE("docile"),
    SUSPICIOUS("suspicious"),
    RELAXED("relaxed"),
    ENERGETIC("energetic"),
    SUBMISSIVE("submissive"),
    DOMINANT("dominant"),
    ANXIOUS("anxious"),
    AFFECTIONATE("affectionate");

    @Getter
    private String temperament;

    TemperamentEnum(String temperament){
        this.temperament = temperament;
    }

    public static TemperamentEnum fromString(String value){
        return Arrays.stream(values())
                .filter(te -> te.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }


}
