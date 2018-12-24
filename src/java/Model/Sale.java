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
public class Sale {
    private int id;
    private int flightId;
    private int userId;
    private String place;
    private int noLuggage;
    private int ccId;
    
    private Connection con;
    private Statement set;
    private ResultSet rs;

    //Constructor
    public Sale(int id, int flightId, int userId, String place, int noLuggage, int ccId){
        this.id = id;
        this.flightId = flightId;
        this.userId = userId;
        this.place = place;
        this.noLuggage = noLuggage;
        this.ccId = ccId;
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
    public boolean insertUser(Sale sales){
        boolean inserted = false;
        int insertedId = -1;
        String query = "INSERT INTO sales (flight_id, user_id, place, number_Luggages, "
                + "credit_card) VALUES ("+sales.flightId+","+sales.userId+","
                + ""+sales.place+"',"+sales.noLuggage+","+sales.ccId+")";
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
    public boolean updateUser(Sale sales){
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE sales SET flight_id="+sales.flightId+","
                + "user_id="+sales.userId+", place="+sales.place+", "
                + "email= "+sales.noLuggage+",credid_card= "+sales.ccId+
                "WHERE id = "+sales.id+";";
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
    
    public int getFlightid(){
        return flightId;
    }
    
    public void setFlightid(int flightId){
        this.flightId = flightId;
    }
    
    public int getUserid(){
        return userId;
    }
    
    public void setUserid(){
        this.userId = userId;
    }
    
    public String getPlace(){
        return place;
    }
    
    public void setPlace(String place){
        this.place = place;
    }
    
    public int getNoluggage(){
        return noLuggage;
    }
    
    public void setNoluggage(int noLuggage){
        this.noLuggage = noLuggage;
    }
    
    public int getCcId(){
        return ccId;
    }
    public void setCcId(int ccId){
        this.ccId = ccId;
    }
    
}
