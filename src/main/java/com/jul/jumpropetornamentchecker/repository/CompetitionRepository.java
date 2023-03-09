package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition save(Competition competition);

    Optional<Competition> findByCompetitionId(Long competitionId);

    @Query("SELECT c FROM Competition c WHERE c.competitionName like ?1%")
    List<Competition> findByCompetitionName(String competitionName);

    List<Competition> findAll();
}
