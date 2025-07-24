package com.carlosarroyoam.ws.client.countries;

import com.carlosarroyoam.ws.client.countries.dto.CountryDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {
  private final CountryService countryService;

  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<CountryDto>> findAll() {
    return ResponseEntity.ok(countryService.findAll());
  }

  @GetMapping(path = "/{countryName}", produces = "application/json")
  public ResponseEntity<CountryDto> findById(@PathVariable String countryName) {
    return ResponseEntity.ok(countryService.findByName(countryName));
  }
}
