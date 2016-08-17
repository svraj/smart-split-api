package com.triumsys.split.entity;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.triumsys.split.SplitApplication;
import com.triumsys.split.data.entity.Expense;
import com.triumsys.split.data.entity.ExpenseCategory;
import com.triumsys.split.data.repository.ExpenseCategoryRepository;
import com.triumsys.split.data.repository.ExpenseRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SplitApplication.class)
@WebAppConfiguration
public class TestExpense {

	@Autowired
	private ExpenseCategoryRepository expenseCategoryRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	// @Test
	public void testExpenseCreation() {

		ExpenseCategory category = new ExpenseCategory();
		category.setName("Food");
		category.setDescription("Category for Food");
		// expenseCategoryRepository.save(category);

		Expense expense = new Expense();
		expense.setName("Test");
		expense.setDescription("Test desc");
		expense.setExpenseCategory(category);

		expenseRepository.save(expense);

	}

}
