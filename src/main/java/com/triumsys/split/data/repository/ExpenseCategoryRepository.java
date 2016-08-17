package com.triumsys.split.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.ExpenseCategory;


public interface ExpenseCategoryRepository extends
		JpaRepository<ExpenseCategory, Long> {
}
