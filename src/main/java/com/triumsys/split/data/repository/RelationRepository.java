package com.triumsys.split.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.Relation;


public interface RelationRepository extends JpaRepository<Relation, Long> {
}
