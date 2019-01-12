/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author marti
 */
public class Administrator {

    private int id;
    private String email;
    private String pass;
    private String pname;
    private String sname1;
    private String sname2;

    public Administrator() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
