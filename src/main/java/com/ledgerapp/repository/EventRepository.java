package com.ledgerapp.repository;

import com.ledgerapp.model.EventEntity;
import com.ledgerapp.model.EventType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<EventEntity, String> {

    Optional<EventEntity> findByEventId(String eventId);

    List<EventEntity> findByAccountIdOrderByEventTimestampAsc(String accountId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM EventEntity e WHERE e.accountId = :accountId AND e.type = :type")
    BigDecimal sumAmountByAccountIdAndType(@Param("accountId") String accountId, @Param("type") EventType type);
}
