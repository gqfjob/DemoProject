package com.andaily.domain.shared.security;

import com.andaily.domain.user.Privilege;
import com.andaily.domain.user.User;
import com.andaily.infrastructure.DateUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class AndailyUserDetails implements UserDetails {

    protected static final String ROLE_PREFIX = "ROLE_";

    protected User user;

    protected List<GrantedAuthority> authorities = new ArrayList<>();

    public AndailyUserDetails() {
    }

    public AndailyUserDetails(User user) {
        this.user = user;
        initialPrivileges();
    }

    private void initialPrivileges() {
        List<Privilege> privilegeList = privilegeList();
        for (Privilege privilege : privilegeList) {
            this.authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + privilege.name()));
        }
    }

    private List<Privilege> privilegeList() {
        if (user.defaultUser()) {
            return Arrays.asList(Privilege.values());
        } else {
            final List<Privilege> privileges = user.privileges();
            privileges.add(Privilege.DEFAULT);
            return privileges;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.username();
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

    public User user() {
        return user;
    }

    public String getLastLoginTime() {
        if (user != null && user.lastLoginTime() != null) {
            return DateUtils.toDateText(user.lastLoginTime(), DateUtils.DEFAULT_DATE_TIME_FORMAT);
        }
        return "---";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}