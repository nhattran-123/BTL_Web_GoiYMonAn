/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

import java.util.Date;
/**
 *
 * @author dkhai
 */
public class User_login {
    private int id;
    private Date login_date;

    public User_login(int id, Date login_date) {
        this.id = id;
        this.login_date = login_date;
    }

    public User_login() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }
    
}
