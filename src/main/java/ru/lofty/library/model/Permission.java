package ru.lofty.library.model;

/**
 * @author Alex Lavrentyev
 */
public enum Permission {

    BOOKS_GET("books:get"),
    BOOKS_POST("books:post"),
    AUTHORS_GET("authors:get"),
    AUTHORS_POST("authors:post"),
    USERS_GET("users:get"),
    USERS_POST("users:post");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
