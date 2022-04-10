package com.healthconnect.platform.security;

import com.healthconnect.platform.entity.core.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;


public class AuthenticatedUser implements Authentication {

	private static final long serialVersionUID = 6861381095901879822L;
	private String userId;
    private boolean authenticated = true;
    private Set<GrantedAuthority> authorities;
    private User user;

    public AuthenticatedUser(String userId, Set<GrantedAuthority> authorities, User user){
        this.userId = userId;
        this.authorities = authorities;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.user;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return this.userId;
    }
}