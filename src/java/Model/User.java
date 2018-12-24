/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author marti
 */
public class User {

    private int id;
    private String pname;
    private String sname1;
    private String sname2;
    private String email;
    private String pass;
    private Date bday;
    private String address;
    private String pcode;
    private String country;
    private int boughtFlights;

    public User(int id, String pname, String sname1, String sname2, String email, String pass, Date bday, String address, String pcode, String country, int boughtFlights) {
        this.id = id;
        this.pname = pname;
        this.sname1 = sname1;
        this.sname2 = sname2;
        this.email = email;
        this.pass = pass;
        this.bday = bday;
        this.address = address;
        this.pcode = pcode;
        this.country = country;
        this.boughtFlights = boughtFlights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getSname1() {
        return sname1;
    }

    public void setSname1(String sname1) {
        this.sname1 = sname1;
    }

    public String getSname2() {
        return sname2;
    }

    public void setSname2(String sname2) {
        this.sname2 = sname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBoughtFlights() {
        return boughtFlights;
    }

    public void setBoughtFlights(int boughtFlights) {
        this.boughtFlights = boughtFlights;
    }

}
