package com.triumsys.split.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.Group;

public interface PersonGroupRepository extends JpaRepository<Group, Long> {

}
