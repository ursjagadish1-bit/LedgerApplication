package com.ledgerapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LedgerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void idempotentDuplicateSubmissionsReturnExistingEvent() throws Exception {
        Map<String, Object> event = Map.of(
                "eventId", "evt-100",
                "accountId", "acct-001",
                "type", "CREDIT",
                "amount", 120.00,
                "currency", "USD",
                "eventTimestamp", "2026-05-15T14:02:11Z"
        );

        mockMvc.perform(post("/events")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eventId").value("evt-100"));

        mockMvc.perform(post("/events")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("evt-100"));
    }

    @Test
    void eventsAreReturnedInTimestampOrderAndBalanceIsComputedCorrectly() throws Exception {
        Map<String, Object> first = Map.of(
                "eventId", "evt-101",
                "accountId", "acct-002",
                "type", "CREDIT",
                "amount", 200.00,
                "currency", "USD",
                "eventTimestamp", "2026-05-16T10:00:00Z"
        );
        Map<String, Object> second = Map.of(
                "eventId", "evt-102",
                "accountId", "acct-002",
                "type", "DEBIT",
                "amount", 45.50,
                "currency", "USD",
                "eventTimestamp", "2026-05-15T09:00:00Z"
        );

        mockMvc.perform(post("/events").contentType("application/json").content(objectMapper.writeValueAsString(first)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/events").contentType("application/json").content(objectMapper.writeValueAsString(second)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/events").param("account", "acct-002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId").value("evt-102"))
                .andExpect(jsonPath("$[1].eventId").value("evt-101"));

        mockMvc.perform(get("/accounts/acct-002/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(new BigDecimal("154.50")));
    }

    @Test
    void missingAmountReturnsBadRequest() throws Exception {
        Map<String, Object> invalidEvent = Map.of(
                "eventId", "evt-103",
                "accountId", "acct-003",
                "type", "CREDIT",
                "currency", "USD",
                "eventTimestamp", "2026-05-15T14:02:11Z"
        );

        mockMvc.perform(post("/events")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidEvent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.details[0]").value("amount: amount is required"));
    }
}
