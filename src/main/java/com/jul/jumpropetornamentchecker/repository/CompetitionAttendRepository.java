package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.Gender;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetitionAttendRepository extends JpaRepository<CompetitionAttend, String> {

    List<CompetitionAttend> findByOrganizationAndCompetition(Organization organization, Competition competition);

    List<CompetitionAttend> findByCompetition(Competition competition);
    List<CompetitionAttend> findByOrganization(Organization organization);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void delete(CompetitionAttend competitionAttend);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CompetitionAttend c SET c.department = ?2, c.playerGender = ?3, c.playerName = ?4, c.playerTel = ?5, c.playerBirth = ?6, c.playerAffiliation = ?7 WHERE c.cmptAttendId = ?1")
    void updateByCmptAttendIdAndUpdateDto(String cmptAttendId,
                                          Department department,
                                          Gender playerGender,
                                          String playerName,
                                          String playerTel,
                                          String playerBirth,
                                          String playerAffiliation);
}



