package com.example.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.model.PUserData;

public interface PUserDataRepository extends JpaRepository<PUserData, Long> {
}
