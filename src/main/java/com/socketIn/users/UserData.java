package com.socketIn.users;


import java.util.HashSet;
import java.util.Set;

public class UserData {

    private static UserData instance;
    private Set<String> users;

    private UserData() {
        users = new HashSet<>();
    }

    public static synchronized UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUser(String userName) throws Exception {
        if (users.contains(userName)) {
            throw new Exception("User aready exists with userName: " + userName);
        }
        users.add(userName);
    }
}
