package com.triumsys.split.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.ExpenseSplit;


public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
}
