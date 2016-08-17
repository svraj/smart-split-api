package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

//@Entity
@Embeddable
public class ParticipantShare implements Serializable {

	@OneToOne
	private Person person;
	private Double share;
	private BigDecimal paidAmt;
	private BigDecimal shareAmt;
	private String remarks;

	/*
	 * @JsonIgnore
	 * 
	 * @ManyToOne private Expense expense; public Expense getExpense() { return
	 * expense; }
	 * 
	 * public void setExpense(Expense expense) { this.expense = expense; }
	 */
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ParticipantShare() {

	}

	public ParticipantShare(Person person, Double share, BigDecimal paidAmount,
			BigDecimal shareAmount, String remarks) {
		super();
		this.person = person;
		this.share = share;
		this.paidAmt = paidAmount;
		this.shareAmt = shareAmount;
		this.remarks = remarks;
		this.paidAmt = this.paidAmt.setScale(2, RoundingMode.CEILING);
		this.shareAmt = this.shareAmt.setScale(2, RoundingMode.CEILING);
	}

	public ParticipantShare(Person person, Double share, String remarks) {
		super();
		this.person = person;
		this.share = share;
		this.remarks = remarks;
	}

	public ParticipantShare(Person person, Double share, BigDecimal paidAmount,
			BigDecimal shareAmount) {
		super();
		this.person = person;
		this.share = share;
		this.paidAmt = paidAmount;
		this.shareAmt = shareAmount;
		this.paidAmt = this.paidAmt.setScale(2, RoundingMode.CEILING);
		this.shareAmt = this.shareAmt.setScale(2, RoundingMode.CEILING);
	}

	public ParticipantShare(Person person, Double share, BigDecimal shareAmount) {
		super();
		this.person = person;
		this.share = share;
		this.shareAmt = shareAmount;
		this.shareAmt = this.shareAmt.setScale(2, RoundingMode.CEILING);
	}

	public ParticipantShare(Person person, Double share) {
		super();
		this.person = person;
		this.share = share;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Double getShare() {
		return share;
	}

	public void setShare(Double share) {
		this.share = share;
	}

	public BigDecimal getShareAmt() {
		return shareAmt;
	}

	public void setShareAmt(BigDecimal shareAmount) {
		this.shareAmt = shareAmount;
	}

	public BigDecimal getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(BigDecimal paidAmount) {
		this.paidAmt = paidAmount;
	}

	@Override
	public String toString() {
		return "ParticipantShare [person=" + person + ", share=" + share
				+ ", paidAmt=" + paidAmt + ", shareAmt=" + shareAmt
				+ ", remarks=" + remarks + "]";
	}

}
