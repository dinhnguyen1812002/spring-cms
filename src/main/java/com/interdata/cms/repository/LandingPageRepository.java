package com.interdata.cms.repository;

import com.interdata.cms.model.LandingPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandingPageRepository extends JpaRepository<LandingPage, Long> {
    Optional<LandingPage> findBySlug(String slug);
}