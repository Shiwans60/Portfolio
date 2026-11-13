package com.Shiwans.portfolio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "work_experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_on_portfolio", nullable = false)
    private boolean displayOnPortfolio = true;

    public List<String> getDescPoints() {

        if (description == null || description.isBlank()) {
            return List.of();
        }

        return Arrays.stream(
                        description.trim().split("(?<=\\.)\\s+|\\R+")
                )
                .map(String::trim)
                .filter(point -> !point.isEmpty())
                .toList();
    }

    public Experience() {
    }

    public Experience(String companyName, String jobTitle, LocalDate startDate, LocalDate endDate, String description) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplayOnPortfolio() {
        return displayOnPortfolio;
    }

    public void setDisplayOnPortfolio(boolean displayOnPortfolio) {
        this.displayOnPortfolio = displayOnPortfolio;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", descPoints=" +  getDescPoints() +
                '}';
    }
}