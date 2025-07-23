package com.carlosarroyoam.ws.client.config;

import com.carlosarroyoam.ws.client.countries.CountryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryClientConfig {
  @Value("${app.ws.countries.uri}")
  private String countriesWsUri;

  @Bean
  Jaxb2Marshaller marshaller() {
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.carlosarroyoam.ws.client.wsdl");
    return marshaller;
  }

  @Bean
  CountryClient countryClient(Jaxb2Marshaller marshaller) {
    CountryClient client = new CountryClient();
    client.setDefaultUri(countriesWsUri);
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
