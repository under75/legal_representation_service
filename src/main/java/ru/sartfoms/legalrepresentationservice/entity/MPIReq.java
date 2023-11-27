package ru.sartfoms.legalrepresentationservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "mpi_req", schema = "OMCOWNER")
@IdClass(MPIReqId.class)
public class MPIReq {
	@Id
	@Column(name = "rid")
	private Long rid;
	@Id
	@Column(name = "dt")
	private LocalDateTime dt;
	
	@Lob
	@Column(name = "req")
	private byte[] req;
	
	@Lob
	@Column(name = "resp")
	private byte[] resp;
	
	@Column(name = "extrid")
	private String extrid;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public LocalDateTime getDt() {
		return dt;
	}

	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}

	public byte[] getReq() {
		return req;
	}

	public void setReq(byte[] req) {
		this.req = req;
	}

	public byte[] getResp() {
		return resp;
	}

	public void setResp(byte[] resp) {
		this.resp = resp;
	}

	public String getExtrid() {
		return extrid;
	}

	public void setExtrid(String extrid) {
		this.extrid = extrid;
	}
	
}
