package com.itheima.dao;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    /**
     * 用于spring-security框架登录权限的操作
     * @param userName
     * @return
     * @throws Exception
     */
    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "statusStr",column = "statusStr"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.dao.RoleDao.findByUserId"))
    })
    public UserInfo findByUserName(String userName) throws Exception;

    /**
     * 查询所有的用户
     * @return
     * @throws Exception
     */
    @Select("select * from users")
    public List<UserInfo> findAll() throws Exception;

    /**
     * 保存用户功能
     * @param userInfo
     * @throws Exception
     */
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo) throws Exception;


    /**
     * 查询单个用户,用户下的多个角色及权限,用于用户管理功能
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "statusStr",column = "statusStr"),
            @Result(property = "roles",column = "id",many = @Many(select = "com.itheima.dao.RoleDao.findByUserId"))
    })
    UserInfo findById(String id) throws Exception;

    /**
     * 搜索出用户还没有的角色
     * @param userId
     * @return
     * @throws Exception
     */
    @Select("select * from role where id not in (select roleId from users_role where userId = #{userId})")
    List<Role> findRoleByUserId(String userId) throws Exception;

    /**
     * 给用户添加角色
     * @param userId
     * @param roleId
     */
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void saveUserWithRole(@Param("userId") String userId,@Param("roleId") String roleId);
}
