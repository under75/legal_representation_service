package ru.sartfoms.legalrepresentationservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "mpi_error", schema = "OMCOWNER")
@IdClass(CompositeKey.class)
public class MPIError {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;
	
	@Column(name = "typ")
	private Integer type;

	@Column(name = "cod")
	private String code;

	@Column(name = "message")
	private String message;

	@Column(name = "tag")
	private String tag;

	@Column(name = "val")
	private String value;
	
	@Column(name = "dt_ins")
	private LocalDateTime dtIns;
	
	@Column(name = "extrid")
	private String extrid;

	public MPIError() {
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getDtIns() {
		return dtIns;
	}

	public void setDtIns(LocalDateTime dtIns) {
		this.dtIns = dtIns;
	}

	public String getExtrid() {
		return extrid;
	}

	public void setExtrid(String extrid) {
		this.extrid = extrid;
	}

}
