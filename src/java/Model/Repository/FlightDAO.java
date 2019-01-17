/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Flight;
import Model.Route;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface FlightDAO {

    public Flight find(int id);
    
    public ArrayList<Flight> findAll();

    public boolean insert(Flight flight);

    public boolean update(Flight flight);

    public boolean delete(int id);
    
    public ArrayList<Flight> findFlights(Route route, String date, int passengers);

}
