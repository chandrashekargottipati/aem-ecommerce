package com.myecommerce.core.services;

public interface UserService {
    boolean isUserLoggedIn();
    String getCurrentUserName();
    String getCurrentUserId();
}
