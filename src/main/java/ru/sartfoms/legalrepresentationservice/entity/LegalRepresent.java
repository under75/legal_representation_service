package ru.sartfoms.legalrepresentationservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MPI_LEGAL_REPRESENT", schema="OMCOWNER")
public class LegalRepresent {
	@Id
	@Column(name = "rid")
	private Long rid;
	
	@Column(name = "dt_ins")
	private LocalDateTime dtIns;
	
	@Column(name = "usr")
	private String user;
	
	@Column(name = "oip")
	private String oip;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "isactual")
	private Boolean isActual;
	
	@Column(name = "dt_req")
	private LocalDateTime dtReq;
	
	@Column(name = "err")
	private Boolean hasError;
	
	@Column(name = "count")
	private Integer count;

	public Long getRid() {
		return rid;
	}

	public LocalDateTime getDtIns() {
		return dtIns;
	}

	public String getUser() {
		return user;
	}

	public String getOip() {
		return oip;
	}

	public String getType() {
		return type;
	}

	public Boolean getIsActual() {
		return isActual;
	}

	public LocalDateTime getDtReq() {
		return dtReq;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setDtIns(LocalDateTime dtIns) {
		this.dtIns = dtIns;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIsActual(Boolean isActual) {
		this.isActual = isActual;
	}

	public void setDtReq(LocalDateTime dtReq) {
		this.dtReq = dtReq;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
