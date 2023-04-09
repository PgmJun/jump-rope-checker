package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionAttendRepository extends JpaRepository<CompetitionAttend, String> {

    List<CompetitionAttend> findByOrganizationAndCompetition(Organization organization, Competition competition);

    List<CompetitionAttend> findByCompetition(Competition competition);

}



