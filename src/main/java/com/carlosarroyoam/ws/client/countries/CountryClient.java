package com.carlosarroyoam.ws.client.countries;

import com.carlosarroyoam.ws.client.wsdl.GetCountryRequest;
import com.carlosarroyoam.ws.client.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CountryClient extends WebServiceGatewaySupport {
  private static final Logger log = LoggerFactory.getLogger(CountryClient.class);

  public GetCountryResponse findByName(String country) {
    log.info("Requesting location for: {}", country);
    GetCountryRequest request = new GetCountryRequest();
    request.setName(country);

    return (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(request,
        new SoapActionCallback("http://carlosarroyoam.com/ws/countries/GetCountryRequest"));
  }
}
