package com.example.dildil.login_page.bean;

public class inputDto {
    private String account;
    private String password;
    private String email;
    private String username;


    public inputDto(String account,String password){
        this.account = account;
        this.password = password;
    }

    public inputDto(String account,String email,String password,String username){
        this.account = account;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
