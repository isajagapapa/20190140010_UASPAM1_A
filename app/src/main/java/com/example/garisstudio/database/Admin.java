package com.example.garisstudio.database;

//Model data admin untuk set dan get
public class Admin {
    String id_admin, password;

    public Admin(String id_admin, String password) {
        this.id_admin = id_admin;
        this.password = password;
    }

    public Admin() {
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
