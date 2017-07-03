package com.cfg.shop.config.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new User("ClientName", "ClientPwd", getGrantedAuthorities());
    }

    //internal helpers
    private Collection<? extends GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_APP");
        grantedAuthorities.add(authority);
        return grantedAuthorities;
    }
}
