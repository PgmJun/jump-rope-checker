package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition save(Competition competition);

    List<Competition> findByCompetitionId(Long competition_id);

    List<Competition> findByCompetitionName(String competition_name);

    List<Competition> findAll();
}
