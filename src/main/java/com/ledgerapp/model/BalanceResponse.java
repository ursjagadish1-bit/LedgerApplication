package com.ledgerapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BalanceResponse {

    private final String accountId;
    private final BigDecimal balance;

    public BalanceResponse(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }
}
