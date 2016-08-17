package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.triumsys.split.data.enums.RecordStatus;

@SuppressWarnings("serial")
@MappedSuperclass
@JsonIgnoreProperties({ "recordCreatedDate", "recordModifiedDate",
		"recordCreatedBy", "recordModifiedBy", "recordCurrentStatus" })
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/*
	 * @Version int version;
	 */

	@JsonIgnore
	@Column(name = "record_created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordCreatedDate;

	@JsonIgnore
	@Column(name = "record_modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordModifiedDate;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_created_by")
	private UserDetail recordCreatedBy;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_modified_by")
	private UserDetail recordModifiedBy;

	@JsonIgnore
	@Column(name = "record_current_status")
	@Enumerated(EnumType.ORDINAL)
	private RecordStatus recordCurrentStatus = RecordStatus.ACTIVE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRecordCreatedDate() {
		return recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	public Date getRecordModifiedDate() {
		return recordModifiedDate;
	}

	public void setRecordModifiedDate(Date recordModifiedDate) {
		this.recordModifiedDate = recordModifiedDate;
	}

	public UserDetail getRecordCreatedBy() {
		return recordCreatedBy;
	}

	public void setRecordCreatedBy(UserDetail recordCreatedBy) {
		this.recordCreatedBy = recordCreatedBy;
	}

	public UserDetail getRecordModifiedBy() {
		return recordModifiedBy;
	}

	public void setRecordModifiedBy(UserDetail recordModifiedBy) {
		this.recordModifiedBy = recordModifiedBy;
	}

	public RecordStatus getRecordCurrentStatus() {
		return recordCurrentStatus;
	}

	public void setRecordCurrentStatus(RecordStatus recordCurrentStatus) {
		this.recordCurrentStatus = recordCurrentStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof BaseEntity && obj.getClass().equals(getClass())) {
			return this.id.equals(((BaseEntity) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.id);
		return hash;
	}

}
