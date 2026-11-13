
package com.Shiwans.portfolio.service;

import com.Shiwans.portfolio.dao.EducationRepository;
import com.Shiwans.portfolio.entity.Education;
import com.Shiwans.portfolio.service.interfaces.EducationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    public List<Education> getAllEducationDetails() {
        return educationRepository.findAll();
    }

    @Override
    public Education findById(int theId) {

        return educationRepository.findById(theId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Education doesn't exist in database. ID: " + theId
                        )
                );
    }

    @Override
    @Transactional
    public Education save(Education theEducation) {
        return educationRepository.save(theEducation);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        educationRepository.deleteById(theId);
    }
}
