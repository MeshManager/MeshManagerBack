package com.istizo.crd_service.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceType {
    StandardType("StandardType"),
    CanaryType("CanaryType"),
    StickyCanaryType("StickyCanaryType");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ServiceType fromValue(String value) {
        for (ServiceType type : ServiceType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
