package io.gituhub.jfelixy.petadoptionapi.domain.enums;

public enum TemperamentEnum {
    DOCILE,
    AGGRESSIVE,
    FEARFUL,
    PLAYFUL,
    PROTECTIVE,
    INDEPENDENT,
    CALM,
    ANXIOUS,
    CURIOUS,
    TERRITORIAL;
    @Override
    public String toString() {
        String name = name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
