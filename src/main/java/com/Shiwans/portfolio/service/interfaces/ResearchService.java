package com.Shiwans.portfolio.service.interfaces;

import com.Shiwans.portfolio.entity.Research;

import java.util.List;

public interface ResearchService {
    List<Research> getAllResearch();

    Research findById(Integer id);

    Research save(Research research);

    void deleteById(Integer id);
}