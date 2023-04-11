package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetitionAttendRepository extends JpaRepository<CompetitionAttend, String> {

    List<CompetitionAttend> findByOrganizationAndCompetition(Organization organization, Competition competition);

    List<CompetitionAttend> findByCompetition(Competition competition);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM CompetitionAttend c WHERE c.competition = ?1")
    void deleteByCompetition(Competition competition);

    @Modifying(clearAutomatically = true)
    void delete(CompetitionAttend competitionAttend);

}



