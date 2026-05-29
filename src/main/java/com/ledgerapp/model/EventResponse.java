package com.ledgerapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class EventResponse {

    private final String eventId;
    private final String accountId;
    private final EventType type;
    private final BigDecimal amount;
    private final String currency;
    private final Instant eventTimestamp;
    private final Map<String, Object> metadata;

    public EventResponse(String eventId, String accountId, EventType type, BigDecimal amount, String currency, Instant eventTimestamp, Map<String, Object> metadata) {
        this.eventId = eventId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.eventTimestamp = eventTimestamp;
        this.metadata = metadata;
    }

    @JsonProperty("eventId")
    public String getEventId() {
        return eventId;
    }

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("type")
    public EventType getType() {
        return type;
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("eventTimestamp")
    public Instant getEventTimestamp() {
        return eventTimestamp;
    }

    @JsonProperty("metadata")
    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
