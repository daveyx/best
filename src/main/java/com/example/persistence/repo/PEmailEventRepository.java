package com.example.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.model.PEmailEvent;

public interface PEmailEventRepository extends JpaRepository<PEmailEvent, Long> {
}
