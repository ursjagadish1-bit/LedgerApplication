package com.ledgerapp.model;

public class SubmitEventResult {

    private final EventResponse event;
    private final boolean created;

    public SubmitEventResult(EventResponse event, boolean created) {
        this.event = event;
        this.created = created;
    }

    public EventResponse getEvent() {
        return event;
    }

    public boolean isCreated() {
        return created;
    }
}
