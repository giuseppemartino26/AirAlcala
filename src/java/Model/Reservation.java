/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;

/**
 *
 * @author marti
 */
public class Reservation {
    private int id;
    private int userId;
    private int flightId;
    
    private Connection con;
    private Statement set;
    private ResultSet rs;

    //Constructor
    public Reservation(int id, int userId, int flightId){
        this.id = id;
        this.userId = userId;
        this.flightId = flightId;

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
    public boolean insertUser(Reservation res){
        boolean inserted = false;
        int insertedId = -1;
        String query = "INSERT INTO reservation (user_id, flight_id) VALUES "
                + "("+res.userId+","+res.flightId+")";
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
    public boolean updateUser(Reservation res){
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE reservation SET user_id="+res.userId+","
                + "flight_id="+res.flightId+";";
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
        String query = "DELETE FROM reservation WHERE id="+id+";";
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
        
    public int getUserid(){
        return userId;
    }
    
    public void setUserid(){
        this.userId = userId;
    }
    
    public int getFlightid(){
        return flightId;
    }
    
    public void setFlightid(int flightId){
        this.flightId = flightId;
    }
}
