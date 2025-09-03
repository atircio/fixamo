package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "TB_KEYWORD")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String word;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public Keyword(String word, Report report){
        this.word = word;
        this.report = report;
    }

    public Keyword() {
    }

}
