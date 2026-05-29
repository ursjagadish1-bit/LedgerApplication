package com.ledgerapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {
    CREDIT,
    DEBIT;

    @JsonCreator
    public static EventType from(String value) {
        if (value == null) {
            return null;
        }
        try {
            return EventType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("type must be CREDIT or DEBIT");
        }
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
