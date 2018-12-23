/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import java.util.*;
import java.sql.*;

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
    private String bday;
    private String address;
    private String pcode;
    private String country;
    private int boughtFlights;
    
    private Connection con;
    private Statement set;
    private ResultSet rs;
    
    // Constructor of the User Object
    public User(int id, String pname, String sname1, String sname2, 
            String email, String pass, String bday, String address, 
            String pcode, String country, int flightsBought){
        super();
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
    public boolean insertUser(User user){
        boolean inserted = false;
        int insertedId = -1;
        String query = "INSERT INTO users (NOMBRE, CIUDAD, PAIS, LONGITUD, "
                + "N_C"
                + "URVAS, N_VUELTAS) VALUES ('"+user.pname+"',"
                + "'"+user.sname1+"','"+user.sname2+"',"+user.email+","
                + ""+user.pass+","+user.bday+","+user.address+","+user.pcode+","
                + ""+user.country+","+user.boughtFlights+")";
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
    public boolean updateUser(User user){
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE users SET prename="+user.pname+","
                + "surname1="+user.sname1+", surname2="+user.sname2+", "
                + "email= "+user.email+",password= "+user.pass+","
                + "birthday= "+user.bday+", address= "+user.address+", "
                + "postalcode= "+user.pcode+", country= "+user.country+","
                + "boughtFlights="+user.boughtFlights+
                "WHERE id = "+user.id+";";
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
        String query = "DELETE FROM users WHERE id="+id+";";
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
    
    public String getPname(){
        return pname;
    }
    
    public void setpname(String pname){
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
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }
    
    public String getBday(){
        return bday;
    }
    
    public void setBday(String bday){
        this.bday = bday;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String Address){
        this.address = address;
    }
    
    public String getPcode(){
        return pcode;
    }
    
    public void setPcode(String pcode){
        this.pcode = pcode;
    }
    
    public String getCountry(){
       return country;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public int getBoughtFlights(){
        return boughtFlights;
    }
    public void setBoughtFlights(int boughtFlights){
        this.boughtFlights = boughtFlights;
    }
}
