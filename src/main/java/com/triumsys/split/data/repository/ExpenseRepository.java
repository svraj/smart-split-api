package com.triumsys.split.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.Expense;

import com.triumsys.split.data.entity.Person;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
