package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Expense extends BaseEntity implements Serializable {

	private String name;
	private String description;
	private String location;
	private Date expenseDate;

	@Transient
	private BigDecimal totalAmount;

	@OneToOne
	private ExpenseCategory expenseCategory;

	// @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER, cascade =
	// CascadeType.ALL)
	@ElementCollection(fetch = FetchType.EAGER)
	private List<ParticipantShare> participantShares;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getTotalAmount() {

		if (totalAmount == null) {
			BigDecimal calculatedTotalAmount = null;
			if (participantShares != null) {
				calculatedTotalAmount = BigDecimal.ZERO;
				for (ParticipantShare participantShare : participantShares) {
					if (participantShare.getPaidAmt() != null) {
						calculatedTotalAmount = calculatedTotalAmount
								.add(participantShare.getPaidAmt());
					}
				}
			}
			totalAmount = calculatedTotalAmount;
		}
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ExpenseCategory getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public List<ParticipantShare> getParticipantShares() {
		return participantShares;
	}

	public void setParticipantShares(List<ParticipantShare> participantShares) {
		this.participantShares = participantShares;
	}

	/*
	 * @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER) private
	 * List<ExpenseSplit> expenseSplits;
	 * 
	 * public List<ExpenseSplit> getExpenseSplits() { return expenseSplits; }
	 * 
	 * public void setExpenseSplits(List<ExpenseSplit> expenseSplits) {
	 * this.expenseSplits = expenseSplits; }
	 */

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	/*
	 * public Person getPaidBy() { return paidBy; }
	 * 
	 * public void setPaidBy(Person paidBy) { this.paidBy = paidBy; }
	 */
}
