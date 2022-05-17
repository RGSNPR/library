package ru.lofty.library.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Lavrentyev
 */
public enum Role {
    ADMIN(List.of(Permission.BOOKS_GET, Permission.BOOKS_POST, Permission.AUTHORS_GET, Permission.AUTHORS_POST,
            Permission.USERS_GET, Permission.USERS_POST)),
    USER(List.of(Permission.BOOKS_GET, Permission.AUTHORS_GET, Permission.USERS_GET));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
    }
}
