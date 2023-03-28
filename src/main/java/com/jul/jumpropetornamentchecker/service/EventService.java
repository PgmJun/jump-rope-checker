package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.domain.event.EventData;
import com.jul.jumpropetornamentchecker.dto.event.EventResponseDto;
import com.jul.jumpropetornamentchecker.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jul.jumpropetornamentchecker.domain.event.EventData.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    public boolean resetAndSaveEventData() {
        boolean saveResult = true;
        try {
            eventRepository.deleteAll();
            getAllEventData().forEach(event -> eventRepository.save(event));
        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

    public List<EventResponseDto> findAllEventData() {
        return eventRepository.findAll().stream()
                .map(Event::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Event> findEventDataById(Long id) {
        Optional<Event> eventData = eventRepository.findById(id);
        return eventData;
    }
}
