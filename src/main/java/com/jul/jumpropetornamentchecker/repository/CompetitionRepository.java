package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition save(Competition competition);

    List<Competition> findByCompetitionId(Long competitionId);

    List<Competition> findByCompetitionName(String competitionName);

    List<Competition> findAll();
}
