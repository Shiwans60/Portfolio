package com.Shiwans.portfolio.dao;

import com.Shiwans.portfolio.entity.Responsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsibilityRepository extends JpaRepository<Responsibility, Integer> {
    List<Responsibility> findAllByOrderByDisplayOrderAsc();
}
