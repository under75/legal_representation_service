package ru.sartfoms.legalrepresentationservice.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="MPI_LEGAL_REPRESENT_RES", schema="OMCOWNER")
@IdClass(CompositeKey.class)
public class LegalRepresentRes {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;
	
	@Column(name = "oip")
	private String oip;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "effdate")
	private LocalDate effDate;
	
	@Column(name = "expdate")
	private LocalDate expDate;
	
	@Column(name = "status")
	private String status;

	public Long getRid() {
		return rid;
	}

	public Integer getNr() {
		return nr;
	}

	public String getOip() {
		return oip;
	}

	public String getType() {
		return type;
	}

	public LocalDate getEffDate() {
		return effDate;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public String getStatus() {
		return status;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEffDate(LocalDate effDate) {
		this.effDate = effDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
