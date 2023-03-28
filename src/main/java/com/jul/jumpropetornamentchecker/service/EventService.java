package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.event.EventData;
import com.jul.jumpropetornamentchecker.dto.event.EventResponseDto;
import com.jul.jumpropetornamentchecker.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void findEventDataById(Long id) {

    }
}
