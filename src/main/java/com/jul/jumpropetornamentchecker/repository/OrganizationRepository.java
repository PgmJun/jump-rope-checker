package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Organization;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization save(Organization organization);

    List<Organization> findAll();

    List<Organization> findByOrgName(String orgName);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteByOrgId(Long orgId);



}
