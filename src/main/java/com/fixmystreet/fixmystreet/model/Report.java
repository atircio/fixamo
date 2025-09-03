package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "TB_REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String rewrittenMessage;
    private String severity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportImage> reportImages;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    private LocalDateTime createdAt;


    @PrePersist
    void setUp(){
        this.createdAt = LocalDateTime.now();
    }

    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRewrittenMessage() {
        return rewrittenMessage;
    }

    public void setRewrittenMessage(String rewrittenMessage) {
        this.rewrittenMessage = rewrittenMessage;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ReportImage> getReportImages() {
        return reportImages;
    }

    public void setReportImages(List<ReportImage> reportImages) {
        this.reportImages = reportImages;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(title, report.title) && Objects.equals(severity, report.severity) && Objects.equals(user, report.user) && Objects.equals(createdAt, report.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, severity, user, createdAt);
    }
}
