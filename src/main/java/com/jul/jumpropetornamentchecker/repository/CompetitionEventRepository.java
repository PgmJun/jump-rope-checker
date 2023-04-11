package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Long> {

    List<CompetitionEvent> findCompetitionEventByCompetition(Competition competition);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM CompetitionEvent c WHERE c.competition = ?1")
    void deleteByCompetition(Competition competition);
}
