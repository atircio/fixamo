package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TB_LOCATION")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double latitude;
    private Double longitude;
    private String address;

    @OneToOne
    private Report report;


    public Location(Double latitude, Double longitude, String address, Report report) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.report = report;
    }

    public Location() {
    }

}
