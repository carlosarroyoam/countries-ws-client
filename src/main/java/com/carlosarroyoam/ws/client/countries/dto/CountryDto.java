package com.carlosarroyoam.ws.client.countries.dto;

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
  private String name;
  private int population;
  private String capital;
  private String currency;

  @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public interface CountryDtoMapper {
    CountryDtoMapper INSTANCE = Mappers.getMapper(CountryDtoMapper.class);

    @Mapping(target = ".", source = "response.country")
    CountryDto toDto(GetCountryResponse response);

    @Mapping(target = ".", source = "response.country")
    List<CountryDto> toDtos(List<GetCountryResponse> response);
  }
}
