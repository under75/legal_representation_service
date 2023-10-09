package ru.sartfoms.legalrepresentationservice;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
@EnableScheduling
public class Config extends WsConfigurerAdapter {
	@Value("${defaultUri}")
	String defaultUri;
	@Value("${token}")
	String token;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this is the package name specified in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("ru.sartfoms.schemas.generated");
		marshaller.setMtomEnabled(true);

		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		WebServiceTemplate template = new WebServiceTemplate(marshaller);
		template.setDefaultUri(defaultUri);
		template.setMessageSender(defaultMyMessageSender());

		return template;
	}

	@Bean
	HttpClient createHttpClient() {
		List<Header> headers = new ArrayList<>();
		BasicHeader authHeader = new BasicHeader("X-Auth-Token", token);
		headers.add(authHeader);
		// add more header as more as needed

		RequestDefaultHeaders reqHeader = new RequestDefaultHeaders(headers);

		CloseableHttpClient httpClient = HttpClients.custom()
				.addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
				.addInterceptorLast(reqHeader).build();
		return httpClient;
	}

	@Bean
	public HttpComponentsMessageSender defaultMyMessageSender()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(createHttpClient());

		return messageSender;
	}
}

