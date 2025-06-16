package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.model.Location;
import com.fixmystreet.fixmystreet.model.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class LocationMapperTest {

    LocationMapper locationMapper;

    @BeforeEach
    void setUp(){
        locationMapper = Mappers.getMapper(LocationMapper.class);
    }

    @Test
    void shouldMapLocationDtoToLocation(){
        LocationDTO dto = new LocationDTO(8.2,1.5, "Main St");

        Location response = locationMapper.mapLocationDtoToLocation(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(8.2, response.getLatitude());
        Assertions.assertEquals(1.5, response.getLongitude());
        Assertions.assertEquals("Main St", response.getAddress());
    }

    @Test
    void shouldMapLocationToLocationDto(){
        Location location = new Location(8.2,1.5,"Main st", new Report());

        LocationDTO response = locationMapper.mapLocationToLocationDto(location);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(8.2, response.latitude());
        Assertions.assertEquals(1.5, response.longitude());
        Assertions.assertEquals("Main st", response.address());

    }
}
