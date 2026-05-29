DROP TABLE IF EXISTS ledger_event;

CREATE TABLE ledger_event (
    event_id VARCHAR(100) NOT NULL PRIMARY KEY,
    account_id VARCHAR(100) NOT NULL,
    event_type VARCHAR(20) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    currency VARCHAR(20) NOT NULL,
    event_timestamp TIMESTAMP NOT NULL,
    metadata CLOB
);

CREATE INDEX idx_account_timestamp ON ledger_event (account_id, event_timestamp);
