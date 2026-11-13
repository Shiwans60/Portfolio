
package com.Shiwans.portfolio.dao;

import com.Shiwans.portfolio.dao.interfaces.RoleDAO;
import com.Shiwans.portfolio.entity.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final EntityManager entityManager;

    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String roleName) {

        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :roleName",
                Role.class
        );

        query.setParameter("roleName", roleName);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
