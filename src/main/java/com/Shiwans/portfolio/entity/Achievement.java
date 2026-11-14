package com.Shiwans.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "award")
    private String award;

    @Column(name = "period")
    private String period;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    public Achievement() {
    }

    public Achievement(String title, String award, String period, String description, Integer displayOrder) {
        this.title = title;
        this.award = award;
        this.period = period;
        this.description = description;
        this.displayOrder = displayOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", award='" + award + '\'' +
                ", period='" + period + '\'' +
                ", description='" + description + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
