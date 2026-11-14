package com.Shiwans.portfolio.service;

import com.Shiwans.portfolio.dao.ResponsibilityRepository;
import com.Shiwans.portfolio.entity.Responsibility;
import com.Shiwans.portfolio.service.interfaces.ResponsibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsibilityServiceImpl implements ResponsibilityService {

    private final ResponsibilityRepository responsibilityRepository;

    @Autowired
    public ResponsibilityServiceImpl(ResponsibilityRepository responsibilityRepository) {
        this.responsibilityRepository = responsibilityRepository;
    }

    @Override
    public List<Responsibility> getAllResponsibilities() {
        return responsibilityRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Override
    public Responsibility findById(Integer id) {
        Optional<Responsibility> result = responsibilityRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Responsibility details not found in database for id: " + id);
        }
    }

    @Override
    @Transactional
    public Responsibility save(Responsibility responsibility) {
        return responsibilityRepository.save(responsibility);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        responsibilityRepository.deleteById(id);
    }
}
