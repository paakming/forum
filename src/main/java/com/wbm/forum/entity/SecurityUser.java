package com.wbm.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2022/11/9 21:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails{

    private User user;
    private List<String> permission;
    private List<String> path;
    private List<String> component;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String s : permission) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(s);
            authorityList.add(authority);
        }*/
        return permission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
