package com.carlosarroyoam.countries.ws.client;

import com.carlosarroyoam.countries.ws.client.countries.CountryClient;
import com.carlosarroyoam.countries.ws.client.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CountriesWsClientApplication {
  private static final Logger log = LoggerFactory.getLogger(CountriesWsClientApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(CountriesWsClientApplication.class, args);
  }

  @Bean
  CommandLineRunner lookup(CountryClient countryClient) {
    return args -> {
      GetCountryResponse response = countryClient.findByName("Spain");
      log.info("Response: {}", response.getCountry().getCurrency());
    };
  }
}
