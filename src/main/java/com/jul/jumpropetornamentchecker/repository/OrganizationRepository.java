package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization save(Organization organization);

    List<Organization> findAll();



}
