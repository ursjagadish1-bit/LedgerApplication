package com.ledgerapp.controller;

import com.ledgerapp.model.BalanceResponse;
import com.ledgerapp.model.EventRequest;
import com.ledgerapp.model.EventResponse;
import com.ledgerapp.model.SubmitEventResult;
import com.ledgerapp.service.EventLedgerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Validated
public class EventController {

    private final EventLedgerService ledgerService;

    public EventController(EventLedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/events")
    public ResponseEntity<EventResponse> submitEvent(@Valid @RequestBody EventRequest request) {
        SubmitEventResult result = ledgerService.submitEvent(request);
        return ResponseEntity.status(result.isCreated() ? HttpStatus.CREATED : HttpStatus.OK)
                .body(result.getEvent());
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(ledgerService.getEvent(eventId));
    }

    @GetMapping(value = "/events", params = "account")
    public ResponseEntity<List<EventResponse>> listEvents(@RequestParam("account") String accountId) {
        return ResponseEntity.ok(ledgerService.listEventsForAccount(accountId));
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(ledgerService.getBalance(accountId));
    }
}
