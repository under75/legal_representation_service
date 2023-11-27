package ru.sartfoms.legalrepresentationservice.service;

import java.time.format.DateTimeFormatter;

public abstract class LegalRepresentationService {
	protected final static Integer SOAP_ERR = 1;
	protected final static Integer LOGIC_ERR = 2;
	protected final static String INTERNAL_SERVICE_ERROR = "500";
	protected final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	protected abstract void process();
}
