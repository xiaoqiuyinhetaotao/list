package com.itheima.service;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAll(Integer page, Integer size) throws Exception;

    public void save(Role role)throws Exception;

    Role findById(String roleId) throws Exception;

    List<Permission> findRoleNotWithPermission(String roleId);

    void savePermissionToRole(String roleId, String[] permissionIds)throws Exception;

    Role findRoleAndPermission(String roleId) throws Exception;
}
