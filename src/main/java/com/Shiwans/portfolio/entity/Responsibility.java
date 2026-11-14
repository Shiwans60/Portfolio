package com.Shiwans.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "responsibilities")
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_title", nullable = false)
    private String roleTitle;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "period")
    private String period;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    public Responsibility() {
    }

    public Responsibility(String roleTitle, String organization, String period, String description, Integer displayOrder) {
        this.roleTitle = roleTitle;
        this.organization = organization;
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

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
        return "Responsibility{" +
                "id=" + id +
                ", roleTitle='" + roleTitle + '\'' +
                ", organization='" + organization + '\'' +
                ", period='" + period + '\'' +
                ", description='" + description + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
