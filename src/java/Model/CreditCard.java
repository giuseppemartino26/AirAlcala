/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author fabri
 */
public class CreditCard {

    private int id;
    private User user;
    private int number;
    private java.sql.Date expiration;
    private int securityCode;
    private int month;
    private int year;

    public CreditCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public java.sql.Date getExpiration() {
        return expiration;
    }

    public void setExpiration(java.sql.Date expiration) {
        this.expiration = expiration;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

}
