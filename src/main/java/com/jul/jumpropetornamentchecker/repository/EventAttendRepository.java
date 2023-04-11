package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventAttendRepository extends JpaRepository<EventAttend, Long> {

    List<EventAttend> findByCompetitionAttend(CompetitionAttend competitionAttend);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM EventAttend e WHERE e.competitionAttend = ?1")
    void deleteByCompetitionAttend(CompetitionAttend competitionAttend);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE EventAttend e SET e.score = ?3 WHERE e.competitionAttend = ?1 AND e.competitionEvent = ?2")
    void updatePlayerEventScore(CompetitionAttend competitionAttend, CompetitionEvent cmptEventId, int score);

    List<EventAttend> findByCompetitionEvent(CompetitionEvent competitionEvent);
}
