package com.itcast.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.pojo.Permission;
import com.itcast.pojo.Role;
import com.itcast.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/19 20:58
 * @description ：
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * 用户权限管理
     *
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User userFromDb = userService.findAllDetailByUserName(userName);

        if (userFromDb == null) {
            return null;
        }

        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = userFromDb.getRoles();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        UserDetails userDetails = new
                org.springframework.security.core.userdetails.User(
                        userName,
                        userFromDb.getPassword(),
                        list);


        return userDetails;
    }
}
