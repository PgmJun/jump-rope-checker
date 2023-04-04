package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventAttendRepository extends JpaRepository<EventAttend, Long> {

    List<EventAttend> findByCompetitionAttend(CompetitionAttend competitionAttend);
}
