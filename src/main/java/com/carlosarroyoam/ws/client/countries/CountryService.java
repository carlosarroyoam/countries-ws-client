package com.carlosarroyoam.ws.client.countries;

import com.carlosarroyoam.ws.client.countries.dto.CountryDto;
import com.carlosarroyoam.ws.client.countries.dto.CountryDto.CountryDtoMapper;
import com.carlosarroyoam.ws.client.wsdl.GetCountryRequest;
import com.carlosarroyoam.ws.client.wsdl.GetCountryResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryService {
  private static final Logger log = LoggerFactory.getLogger(CountryService.class);
  private final CountryClient countryClient;

  public CountryService(CountryClient countryClient) {
    this.countryClient = countryClient;
  }

  public List<CountryDto> findAll() {
    return CountryDtoMapper.INSTANCE.toDtos(countryClient.findAll());
  }

  public CountryDto findByName(String countryName) {
    GetCountryRequest request = new GetCountryRequest();
    request.setName(countryName);

    GetCountryResponse response = countryClient.findByName(request);

    if (response.getCountry() == null) {
      log.warn("Country does not exists");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country does not exists");
    }

    return CountryDtoMapper.INSTANCE.toDto(response);
  }
}
