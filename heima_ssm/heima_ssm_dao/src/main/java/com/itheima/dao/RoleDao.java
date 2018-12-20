package com.itheima.dao;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {


    @Select("select * from role where id in(select roleId from users_role where userId = #{userId} )")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",many = @Many(select = "com.itheima.dao.PermissionDao.findById")),
    })
    public List<Role> findByUserId(String userId) throws Exception;


    @Select("select * from role")
    public List<Role> findAll() throws Exception;


    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role)throws Exception;

    @Select("select * from role where id = #{roleId}")
    public Role findById(String roleId)throws Exception;


    /**
     * 查询用户具有的权限
     * @param roleId
     * @return
     * @throws Exception
     */
    @Select("select * from role where id = #{roleId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",many = @Many(select = "com.itheima.dao.PermissionDao.findById")),
    })
    public Role findRoleAndPermission(String roleId) throws Exception;

    /**
     * 查询角色不具备的权限
     * @param roleId
     * @return
     */
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId = #{roleId})")
    public List<Permission> findRoleNotWithPermission(String roleId);


    @Insert("insert into role_permission(permissionId,roleId) values(#{permissionId},#{roleId})")
    void savePermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId) throws Exception;
}
