/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.CreditCard;
import Model.Repository.CreditCardDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabri
 */
public class JDBCCreditCardDAO implements CreditCardDAO {

    private static Connection connObj;
    private static PreparedStatement stmtObj;
    private static ResultSet rsObj;
    private User user;
    private UserDAO userDAO;

    public Connection dbConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connObj = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
            System.out.println("Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Not Connected. ");
            System.out.println(e);
        }
        return connObj;
    }

    public static void dbDisconnect() {
        try {
            //rsObj.close();
            stmtObj.close();
            connObj.close();
        } catch (Exception exObj) {
            exObj.printStackTrace();
        }
    }

    @Override
    public CreditCard find(int id) {
        CreditCard cc = null;
        userDAO = new JDBCUserDAO();
        String query = "SELECT * FROM creditcards WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();

            if (rsObj.next()) {
                int cc_id = rsObj.getInt("id");
                int userId = rsObj.getInt("user_id");
                user = userDAO.find(userId);
                long number = rsObj.getLong("number");
                int year=rsObj.getInt("expiration_year");
                int month=rsObj.getInt("expiration_month");
                int cvc = rsObj.getInt("securitycode");
                cc = new CreditCard();
                cc.setId(cc_id);
                cc.setUser(user);
                cc.setNumber(number);
                cc.setSecurityCode(cvc);
                cc.setYear(year);
                cc.setMonth(month);

            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return cc;
    }

    @Override
    public CreditCard findByUserId(int userId) {
        CreditCard cc = new CreditCard();
        userDAO = new JDBCUserDAO();
        String query="SELECT * FROM creditcards WHERE user_id = ?";
        System.out.println("text");
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, userId);
            rsObj = stmtObj.executeQuery();

            if (rsObj.next()) {
                cc.setId(rsObj.getInt("id"));
                user = userDAO.find(userId);
                cc.setUser(user);
                cc.setNumber(rsObj.getLong("number"));
                cc.setMonth(rsObj.getInt("expiration_month"));
                cc.setYear(rsObj.getInt("expiration_year"));
                cc.setSecurityCode(rsObj.getInt("securitycode"));
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return cc;
    }
    @Override
    public boolean insert(CreditCard creditCard) {
        boolean inserted = false;
        int insertedId=0;
        
        String query="INSERT INTO creditcards (user_id, number, expiration_year, expiration_month, securitycode)"
                +"VALUES (?,?,?,?,?)";
        
        try {
            connObj=dbConnect();
            stmtObj= connObj.prepareStatement(query);
            //stmtObj.setInt(1, creditCard.getId());
            stmtObj.setInt(1, creditCard.getUser().getId());
            stmtObj.setLong(2, creditCard.getNumber());
            stmtObj.setInt(3, creditCard.getYear());
            stmtObj.setInt(4, creditCard.getMonth());
            stmtObj.setInt(5, creditCard.getSecurityCode());
            
            insertedId=stmtObj.executeUpdate();
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
            }
        if(insertedId>0){
            inserted=true;
        }
        return inserted;
    }

    @Override
    public boolean update(CreditCard creditCard) {
        boolean updated=false;
        int updatedId=0;
        String query = "UPDATE creditcards SET user_id= ?, number= ?, expiration_year= ?, "
                +"expiration_month= ?, securitycode= ? WHERE id = ?";
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, creditCard.getUser().getId());
            stmtObj.setLong(2, creditCard.getNumber());
            stmtObj.setInt(3, creditCard.getYear());
            stmtObj.setInt(4, creditCard.getMonth());
            stmtObj.setInt(5, creditCard.getSecurityCode());
            stmtObj.setInt(6, creditCard.getId());
            
            updatedId=stmtObj.executeUpdate();
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (updatedId > 0) {
            updated = true;
        }
        return updated;
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        int deletedId = 0;
        String query = "DELETE FROM creditcards WHERE id=?";
        try{
            connObj=dbConnect();
            stmtObj=connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            
            deletedId=stmtObj.executeUpdate();
            
            dbDisconnect();
        }catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (deletedId > 0) {
            deleted = true;
        }
        return deleted;
    }

}
