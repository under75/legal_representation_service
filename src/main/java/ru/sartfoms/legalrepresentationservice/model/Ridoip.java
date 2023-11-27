package ru.sartfoms.legalrepresentationservice.model;

public class Ridoip {
	Long rid;
	String oip;

	public Ridoip(Long rid, String oip) {
		this.rid = rid;
		this.oip = oip;
	}

	public Long getRid() {
		return rid;
	}

	public String getOip() {
		return oip;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

}