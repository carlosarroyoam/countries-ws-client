package com.carlosarroyoam.ws.client.countries;

import com.carlosarroyoam.ws.client.wsdl.GetCountryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {
  private final CountryClient countryClient;

  public CountryController(CountryClient countryClient) {
    this.countryClient = countryClient;
  }

  @GetMapping(path = "/{countryName}", produces = "application/json")
  public ResponseEntity<CountryDto> findById(@PathVariable String countryName) {
    CountryDto response = CountryDtoMapper.INSTANCE.toDto(countryClient.findByName(countryName));
    return ResponseEntity.ok(response);
  }
}
