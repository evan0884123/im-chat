/**
 * wesoft.com Inc.
 * Copyright (c) 2005-2016 All Rights Reserved.
 */
package com.imchat.web.auth;

import com.imchat.common.infra.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 *
 */
public class SecurityUser extends User implements UserDetails {

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public SecurityUser(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(",");
    }

    @Override
    public String getUsername() {
        return this.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SecurityUser that = (SecurityUser) o;
        return accountNonExpired == that.accountNonExpired && accountNonLocked == that.accountNonLocked
                && credentialsNonExpired == that.credentialsNonExpired && enabled == that.enabled;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), accountNonExpired, accountNonLocked, credentialsNonExpired,
                enabled);
    }
}
