package com.Shiwans.portfolio.dao.interfaces;

import com.Shiwans.portfolio.entity.Role;

public interface RoleDAO {
    public Role findRoleByName(String roleName);
}
