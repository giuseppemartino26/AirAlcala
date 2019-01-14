/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Helpers.SecurePasswordHelper;
import Model.Repository.AirplaneDAO;
import Model.Airplane;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabri
 */
public class JDBCAirplaneDAO implements AirplaneDAO {

    private Connection connObj;
    private static PreparedStatement stmtObj;
    private static ResultSet rsObj;

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

    public void dbDisconnect() {
        try {
            rsObj.close();
            stmtObj.close();
            connObj.close();
        } catch (Exception exObj) {
            exObj.printStackTrace();
        }
    }

    @Override
    public Airplane find(int id) {
        Airplane airplane = null;
        String query = "SELECT * FROM airplanes WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();

            if (rsObj.next()) {
                airplane = new Airplane();

                airplane.setId(rsObj.getInt("id"));
                airplane.setName(rsObj.getString("code"));
                airplane.setPlaces(rsObj.getInt("places"));
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return airplane;
    }

    public ArrayList<Airplane> findAll() {
        ArrayList<Airplane> airplaneList = new ArrayList<Airplane>();
        String query = "SELECT * FROM airplanes";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                Airplane airplane = new Airplane();
                airplane.setId(rsObj.getInt("id"));
                airplane.setName(rsObj.getString("name"));
                airplane.setPlaces(rsObj.getInt("places"));
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return airplaneList;
    }

    @Override
    public boolean insert(Airplane airplane) {
        boolean inserted = false;
        int insertedId = 0;

        if (airplane.getName().equals("")) {
            String query = "INSERT INTO airplanes (name, places)"
                    + "VALUES (?,?)";

            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, airplane.getName());
                stmtObj.setInt(2, airplane.getPlaces());

                insertedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }
        } else {
            String query = "INSERT INTO airplanes (name, places)"
                    + "VALUES (?,?)";

            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, airplane.getName());
                stmtObj.setInt(2, airplane.getPlaces());

                insertedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }
        }

        if (insertedId > 0) {
            inserted = true;
        }
        return inserted;
    }

    @Override
    public boolean update(Airplane airplane) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE airplanes SET name= ?, places= ?"
                + "WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);

            stmtObj.setString(1, airplane.getName());
            stmtObj.setInt(2, airplane.getPlaces());

            updatedId = stmtObj.executeUpdate();

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
        String query = "DELETE FROM airplanes WHERE id=?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);

            deletedId = stmtObj.executeUpdate();

            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (deletedId > 0) {
            deleted = true;
        }
        return deleted;
    }

}
