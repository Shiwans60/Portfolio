package com.Shiwans.portfolio.dao;

import com.Shiwans.portfolio.dao.interfaces.RoleDAO;
import com.Shiwans.portfolio.entity.Research;
import com.Shiwans.portfolio.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Integer> {
    List<Research> findAllByOrderByPublishedDateDesc();
}