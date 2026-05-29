package com.ledgerapp.service;

import com.ledgerapp.exception.EventNotFoundException;
import com.ledgerapp.model.BalanceResponse;
import com.ledgerapp.model.EventEntity;
import com.ledgerapp.model.EventRequest;
import com.ledgerapp.model.EventResponse;
import com.ledgerapp.model.EventType;
import com.ledgerapp.model.SubmitEventResult;
import com.ledgerapp.repository.EventRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventLedgerService {

    private final EventRepository repository;

    public EventLedgerService(EventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SubmitEventResult submitEvent(EventRequest request) {
        Optional<EventEntity> existing = repository.findByEventId(request.getEventId());
        if (existing.isPresent()) {
            return new SubmitEventResult(toResponse(existing.get()), false);
        }

        EventEntity entity = new EventEntity(
                request.getEventId(),
                request.getAccountId(),
                request.getType(),
                request.getAmount(),
                request.getCurrency(),
                request.getEventTimestamp(),
                request.getMetadata() == null ? Map.of() : request.getMetadata()
        );

        try {
            EventEntity saved = repository.save(entity);
            return new SubmitEventResult(toResponse(saved), true);
        } catch (DataIntegrityViolationException ex) {
            Optional<EventEntity> duplicate = repository.findByEventId(request.getEventId());
            if (duplicate.isPresent()) {
                return new SubmitEventResult(toResponse(duplicate.get()), false);
            }
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public EventResponse getEvent(String eventId) {
        EventEntity entity = repository.findByEventId(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> listEventsForAccount(String accountId) {
        return repository.findByAccountIdOrderByEventTimestampAsc(accountId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(String accountId) {
        BigDecimal credits = repository.sumAmountByAccountIdAndType(accountId, EventType.CREDIT);
        BigDecimal debits = repository.sumAmountByAccountIdAndType(accountId, EventType.DEBIT);
        BigDecimal balance = credits.subtract(debits);
        return new BalanceResponse(accountId, balance);
    }

    private EventResponse toResponse(EventEntity entity) {
        return new EventResponse(
                entity.getEventId(),
                entity.getAccountId(),
                entity.getType(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getEventTimestamp(),
                entity.getMetadata()
        );
    }
}
