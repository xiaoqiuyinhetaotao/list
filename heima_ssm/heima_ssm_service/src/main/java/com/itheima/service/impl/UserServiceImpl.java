package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService  {


    @Autowired
    private UserDao userDao;

    //加密类
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserInfo user = null;

        try {
           user = userDao.findByUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user.getPassword());
//        User user1 = new User(user.getUsername(),"{noop}"+user.getPassword(),user.getStatus() == 0 ? false:true,true,true,true,getAuthority(user.getRoles()));
        User user1 = new User(user.getUsername(),user.getPassword(),user.getStatus() == 0 ? false:true,true,true,true,getAuthority(user.getRoles()));
        return user1;
    }

    //自定义获取角色的方法
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
            System.out.println(role.getRoleName());
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //给用户加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<Role> findRoleByUserId(String userId) throws Exception {
        return userDao.findRoleByUserId(userId);
    }

    @Override
    public void saveUserWithRole(String userId, String[] roleIds) {
        for (String roleId : roleIds) {
            userDao.saveUserWithRole(userId,roleId);
        }
    }
}
