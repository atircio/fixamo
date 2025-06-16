package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "report", ignore = true)
    Location mapLocationDtoToLocation(LocationDTO dto);

    LocationDTO mapLocationToLocationDto(Location location);
}
