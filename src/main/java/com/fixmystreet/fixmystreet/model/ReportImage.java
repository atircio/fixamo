package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "TB_REPORT_IMAGE")
public class ReportImage {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public ReportImage(String imageUrl, Report report) {
        this.imageUrl = imageUrl;
        this.report = report;
    }

    public ReportImage() {
    }

}
