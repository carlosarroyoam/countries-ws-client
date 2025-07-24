package com.carlosarroyoam.ws.client.countries.dto;

import com.carlosarroyoam.ws.client.wsdl.Country;
import com.carlosarroyoam.ws.client.wsdl.GetCountriesResponse;
import com.carlosarroyoam.ws.client.wsdl.GetCountryResponse;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Data
@Builder
public class CountryDto {
  private Integer id;
  private String name;
  private Integer population;
  private String capital;
  private String currency;

  @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public interface CountryDtoMapper {
    CountryDtoMapper INSTANCE = Mappers.getMapper(CountryDtoMapper.class);

    CountryDto toDto(Country country);

    List<CountryDto> toDtos(List<Country> countries);

    @Mapping(target = ".", source = "response.country")
    CountryDto responseToDto(GetCountryResponse response);

    default List<CountryDto> responseToDtos(GetCountriesResponse response) {
      return toDtos(response.getCountries());
    }
  }
}
