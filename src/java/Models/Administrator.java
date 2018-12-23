/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.*;

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
    
    private Connection con;
    private Statement set;
    private ResultSet rs;
    
    public Administrator(int id, String email, String pass, String pname, String sname1, String sname2){
        this.id = id;
        this. email = email;
        this.pass = pass;
        this.pname = pname;
        this.sname1 = sname1;
        this.sname2 = sname2;
    }
    
     // Connect to DataBase
    public void dbConnect() {
        try{
            Class.forName( "org.apache.derby.jdbc.ClientDriver" );
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/Formula1","app","app"); 
            System.out.println("Connected.");
        }
        catch(Exception e){
            System.out.println("Not Connected. ");
            System.out.println(e);
        }
    }
    
     // Insert User Object passed from Controller into DataBase
    public boolean insertUser(Administrator admin){
        boolean inserted = false;
        int insertedId = -1;
        String query = "INSERT INTO administrator (email, password, prename, surname1, "
                + "surname2) VALUES ("+admin.email+","+admin.pass+","
                + ""+admin.pname+"',"+admin.sname1+","+admin.sname2+")";
        try {
            set = con.createStatement();
            insertedId = set.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            rs.close();
            set.close();
        }catch(Exception e){
            System.out.println("Not inserted. "+e);
        }
        if(insertedId>-1){
            inserted = true;
        }
        return inserted;
    }
    
    // Update User by Object passed from Controller
    public boolean updateUser(Administrator admin){
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE sales SET email="+admin.email+","
                + "password="+admin.pass+", prename="+admin.pname+", "
                + "surname1= "+admin.sname1+",surname2= "+admin.sname2+
                "WHERE id = "+admin.id+";";
        try {
            set = con.createStatement();
            updatedId = set.executeUpdate(query,Statement.SUCCESS_NO_INFO);
            rs.close();
            set.close();
        }catch(Exception e){
            System.out.println("Not inserted. "+e);
        }
        if(updatedId>0){
            updated = true;
        }
        return updated;
    }
    
    public boolean deleteUser(int id){
        boolean deleted = false;
        int deletedId = -1;
        String query = "DELETE FROM sales WHERE id="+id+";";
        try {
            set = con.createStatement();
            deletedId = set.executeUpdate(query,Statement.SUCCESS_NO_INFO);
            rs.close();
            set.close();
        }catch(Exception e){
            System.out.println("Not inserted. "+e);
        }
        if(deletedId>-1){
            deleted = true;
        }
        return deleted;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPass(){
        return pass;
    }
    
    public void setPass(){
        this.pass = pass;
    }
    
    public String getPname(){
        return pname;
    }
    
    public void setPname(String pname){
        this.pname = pname;
    }
    
    public String getSname1(){
        return sname1;
    }
    
    public void setSname1(String sname1){
        this.sname1 = sname1;
    }
    
    public String getSname2(){
        return sname2;
    }
    public void setSname2(String sname2){
        this.sname2 = sname2;
    }
    
}