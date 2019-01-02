package nju.agilegroup.storymappingtool.view;

import nju.agilegroup.storymappingtool.model.User;

public class AccountInfo {

    private String name;
    private String email;
    private String password;

    public AccountInfo() {
    }

    public AccountInfo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toUser() {
        return new User(0, name, email, password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
