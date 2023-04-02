package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAttendRepository extends JpaRepository<EventAttend, Long> {
}
