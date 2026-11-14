package com.Shiwans.portfolio.service.interfaces;

import com.Shiwans.portfolio.entity.Responsibility;

import java.util.List;

public interface ResponsibilityService {
    List<Responsibility> getAllResponsibilities();
    Responsibility findById(Integer id);
    Responsibility save(Responsibility responsibility);
    void deleteById(Integer id);
}
