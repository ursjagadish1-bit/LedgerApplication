INSERT INTO ledger_event (event_id, account_id, event_type, amount, currency, event_timestamp, metadata) VALUES
('evt-001', 'acct-123', 'CREDIT', 150.00, 'USD', '2026-05-15 14:02:11', '{"source":"mainframe-batch","batchId":"B-9042"}'),
('evt-002', 'acct-123', 'DEBIT', 25.00, 'USD', '2026-05-16 09:12:00', '{"source":"mobile-app","sessionId":"S-1845"}'),
('evt-003', 'acct-456', 'CREDIT', 200.00, 'USD', '2026-05-15 16:30:00', '{"source":"api-gateway","reference":"REF-778"}');
