package com.Shiwans.portfolio.service;

import com.Shiwans.portfolio.dao.AchievementRepository;
import com.Shiwans.portfolio.entity.Achievement;
import com.Shiwans.portfolio.service.interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Override
    public Achievement findById(Integer id) {
        Optional<Achievement> result = achievementRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Achievement details not found in database for id: " + id);
        }
    }

    @Override
    @Transactional
    public Achievement save(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        achievementRepository.deleteById(id);
    }
}
