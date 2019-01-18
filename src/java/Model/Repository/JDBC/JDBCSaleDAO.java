/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.CreditCard;
import Model.Flight;
import Model.Repository.CreditCardDAO;
import Model.Repository.FlightDAO;
import Model.Sale;
import java.sql.*;
import Model.Repository.SaleDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author fabri
 */
public class JDBCSaleDAO implements SaleDAO {

    private static Connection connObj;
    private static PreparedStatement stmtObj;
    private static ResultSet rsObj;
    private FlightDAO flightDAO;

    private Connection dbConnect() {
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
    public Sale find(String id) {
        Sale sale = null;
        String query = "SELECT * FROM sales WHERE id = '" + id + "'";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            //stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            if (rsObj.next()) {
                FlightDAO flightDAO = new JDBCFlightDAO();
                Flight flight = flightDAO.find(rsObj.getInt("flight_id"));
                

                UserDAO userDAO = new JDBCUserDAO();
                User user = userDAO.find(rsObj.getInt("user_id"));

                CreditCardDAO ccDAO = new JDBCCreditCardDAO();
                CreditCard cc = ccDAO.find(rsObj.getInt("creditcard_id"));

                sale = new Sale();
                sale.setId(rsObj.getString("id"));
                sale.setFlight(flight);
                sale.setUser(user);
                sale.setPlace(rsObj.getString("place"));
                sale.setPassengers(rsObj.getInt("passengers"));
                sale.setCreditCard(cc);
                sale.setPrice(rsObj.getDouble("price"));
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return sale;
    }

    public ArrayList<Sale> findAll() {
        ArrayList<Sale> saleList = new ArrayList<Sale>();
        String query = "SELECT * FROM sales";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                FlightDAO flightDAO = new JDBCFlightDAO();
                Flight flight = flightDAO.find(rsObj.getInt("flight_id"));
                

                UserDAO userDAO = new JDBCUserDAO();
                User user = userDAO.find(rsObj.getInt("user_id"));

                CreditCardDAO ccDAO = new JDBCCreditCardDAO();
                CreditCard cc = ccDAO.find(rsObj.getInt("creditcard_id"));

                Sale sale = new Sale();
                sale.setId(rsObj.getString("id"));
                sale.setFlight(flight);
                sale.setUser(user);
                sale.setPlace(rsObj.getString("place"));
                sale.setPassengers(rsObj.getInt("passengers"));
                sale.setCreditCard(cc);
                sale.setPrice(rsObj.getDouble("price"));

                saleList.add(sale);
            }
            rsObj.close();
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return saleList;
    }

    @Override
    public boolean insert(Sale sale) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO sales (id, flight_id, user_id, place, passengers, creditcard_id, price) "
                + "VALUES (?,?,?,?,?,?,?)";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, sale.getId());
            stmtObj.setInt(2, sale.getFlight().getId());
            stmtObj.setInt(3, sale.getUser().getId());
            stmtObj.setString(4, sale.getPlace());
            stmtObj.setInt(5, sale.getPassengers());
            stmtObj.setInt(6, sale.getCreditCard().getId());
            stmtObj.setDouble(7, sale.getPrice());
            insertedId = stmtObj.executeUpdate();
            flightDAO=new JDBCFlightDAO();
            int availableSeats=sale.getFlight().getAvailableSeats();
            sale.getFlight().setAvailableSeats(availableSeats-sale.getPassengers());
            flightDAO.update(sale.getFlight());
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (insertedId > 0) {
            System.out.println("Insertado");
            inserted = true;
        }
        return inserted;
    }

    @Override
    public boolean update(Sale sale) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE sales SET flight_id = ?, user_id = ?,"
                + "place = '"+sale.getPlace()+"', passengers = ?,creditcard_id = ?, price = ?"
                + "WHERE id = '" + sale.getId() + "';";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);

            stmtObj.setInt(1, sale.getFlight().getId());
            stmtObj.setInt(2, sale.getUser().getId());
            //stmtObj.setString(3, sale.getPlace());
            stmtObj.setInt(3, sale.getPassengers());
            stmtObj.setInt(4, sale.getCreditCard().getId());
            stmtObj.setDouble(5, sale.getPrice());
            //stmtObj.setInt(6, sale.getId());

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
    public boolean delete(String id) {
        boolean deleted = false;
        int deletedId = 0;
        String query = "DELETE FROM sales WHERE id= '"+id+"';";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            //stmtObj.setInt(1, id);

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

    public String generarID() {
        boolean success = false;
        Random aleatorio = new Random();
        String id = "";
        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        int numero, forma, j = 1;
        while (j < 7) {
            forma = (int) (aleatorio.nextDouble() * alfa.length() - 1 + 0);
            numero = (int) (aleatorio.nextDouble() * 99 );
            if (j == 4) {
                id = id + numero;
            } else if (j != 5) {
                id = id + alfa.charAt(forma);
            }
            j++;
        }
        return id;
    }

    @Override
    public boolean comprobarId(String id) {
        boolean success=true;
        ArrayList<Sale> all = this.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(id)) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public ArrayList<Sale> findByUserId(int userId) {
        ArrayList<Sale> saleList = new ArrayList<Sale>();
        String query = "SELECT * FROM sales WHERE user_id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, userId);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                FlightDAO flightDAO = new JDBCFlightDAO();
                Flight flight = flightDAO.find(rsObj.getInt("flight_id"));

                
                
                UserDAO userDAO = new JDBCUserDAO();
                User user = userDAO.find(rsObj.getInt("user_id"));

                CreditCardDAO ccDAO = new JDBCCreditCardDAO();
                CreditCard cc = ccDAO.find(rsObj.getInt("creditcard_id"));

                Sale sale = new Sale();
                sale.setId(rsObj.getString("id"));
                sale.setFlight(flight);
                sale.setUser(user);
                sale.setPlace(rsObj.getString("place"));
                sale.setPassengers(rsObj.getInt("passengers"));
                sale.setCreditCard(cc);
                sale.setPrice(rsObj.getDouble("price"));

                saleList.add(sale);
            }
            rsObj.close();
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return saleList;
    }

    @Override
    public int numberSales(int userId) {
        int number=0;
        String query="select count(id) from sales where user_id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, userId);
            rsObj = stmtObj.executeQuery();
            if (rsObj.next()) {
                number=rsObj.getInt(1);
            }
            rsObj.close();
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return number;
    }
    
    @Override
    public double sumByFlight(int flightId){
        double total = 0;
        String query = "SELECT SUM (price) FROM sales WHERE flight_id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, flightId);
            rsObj = stmtObj.executeQuery();
            while(rsObj.next())
                total+=rsObj.getDouble(1);
            rsObj.close();
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return total;
        
    }
    
    
}

