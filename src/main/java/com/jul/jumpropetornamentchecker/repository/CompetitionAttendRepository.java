package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionAttendRepository extends JpaRepository<CompetitionAttend, Long> {

    List<CompetitionAttend> findByOrganization(Organization organization);

}
