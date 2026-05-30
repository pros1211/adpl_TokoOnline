package com.tokoonline.model;

public abstract class Pengguna {
    private int id;
    private String username;
    private String password;

    // constructor ketika user masuk ke akun (login)
    public Pengguna(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // constructor ketika user membuat akun (register)
    public Pengguna(String username, String password) {
        this.id = 0;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
