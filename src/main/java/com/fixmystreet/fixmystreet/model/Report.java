package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String rewrittenMessage;
    private String Severity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "report")
    private List<ReportImage> reportImages;

    @OneToMany(mappedBy = "report")
    private List<Keyword> keywords;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private LocalDateTime createdAt;


    @PrePersist
    void setUp(){
        this.createdAt = LocalDateTime.now();
    }
}
