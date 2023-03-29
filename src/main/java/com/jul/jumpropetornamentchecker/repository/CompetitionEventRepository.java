package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Long> {

    List<CompetitionEvent> findCompetitionEventByCompetition(Competition competition);
}
