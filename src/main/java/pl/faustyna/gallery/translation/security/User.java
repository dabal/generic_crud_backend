package pl.faustyna.gallery.translation.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

public class User implements UserDetails {
    private SecurityToken securityToken;

    public User() {
    }

    public User(final SecurityToken securityToken) {
        this.securityToken = securityToken;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(securityToken, user.securityToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityToken);
    }

    public SecurityToken getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(final SecurityToken securityToken) {
        this.securityToken = securityToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return securityToken.getSsoToken();
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
        return securityToken.getCreationDateTime().plus(24l, ChronoUnit.HOURS).isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
