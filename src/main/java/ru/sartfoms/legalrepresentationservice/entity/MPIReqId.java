package ru.sartfoms.legalrepresentationservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class MPIReqId implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long rid;

	private LocalDateTime dt;
	
	public MPIReqId() {
	}

	public MPIReqId(Long rid, LocalDateTime dt) {
		super();
		this.rid = rid;
		this.dt = dt;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(dt, rid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MPIReqId other = (MPIReqId) obj;
		return Objects.equals(dt, other.dt) && Objects.equals(rid, other.rid);
	}
	
}
