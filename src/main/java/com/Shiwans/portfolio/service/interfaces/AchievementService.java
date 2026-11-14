package com.Shiwans.portfolio.service.interfaces;

import com.Shiwans.portfolio.entity.Achievement;

import java.util.List;

public interface AchievementService {
    List<Achievement> getAllAchievements();
    Achievement findById(Integer id);
    Achievement save(Achievement achievement);
    void deleteById(Integer id);
}
