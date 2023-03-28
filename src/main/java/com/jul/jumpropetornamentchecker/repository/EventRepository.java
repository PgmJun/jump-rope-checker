package com.jul.jumpropetornamentchecker.repository;


import com.jul.jumpropetornamentchecker.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    void deleteAll();
    List<Event> findAll();
    Optional<Event> findById(Long eventId);
}
