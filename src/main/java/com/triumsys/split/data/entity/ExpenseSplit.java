package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.triumsys.split.data.enums.PaymentStatus;

@Entity
public class ExpenseSplit extends BaseEntity implements Serializable {

	@OneToOne
	private Expense expense;
	@OneToOne
	private Person from;
	@OneToOne
	private Person to;

	private BigDecimal amount;
	private String remarks;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + (manuallyEdited ? 1231 : 1237);
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseSplit other = (ExpenseSplit) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (expense == null) {
			if (other.expense != null)
				return false;
		} else if (!expense.equals(other.expense))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (manuallyEdited != other.manuallyEdited)
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (status != other.status)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	private boolean manuallyEdited;

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public Person getFrom() {
		return from;
	}

	public void setFrom(Person from) {
		this.from = from;
	}

	public Person getTo() {
		return to;
	}

	public void setTo(Person to) {
		this.to = to;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isManuallyEdited() {
		return manuallyEdited;
	}

	public void setManuallyEdited(boolean manuallyEdited) {
		this.manuallyEdited = manuallyEdited;
	}

	@Override
	public String toString() {
		return "ExpenseSplit [expense=" + (expense.getId()==null?"":expense.getId()) + ", from=" + from + ", to="
				+ to + ", amount=" + amount + ", remarks=" + remarks
				+ ", status=" + status + ", manuallyEdited=" + manuallyEdited
				+ "]";
	}

}
