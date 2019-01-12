/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Airport;
import java.util.ArrayList;
/**
 *
 * @author fabri
 */

/*
* This interface is based on the design pattern Repository
* by Eric Evans (Domain-Driven Design: Tackling Complexity in the Heart of Software)
 */
public interface AirportDAO {

    public Airport find(int id);

    public boolean insert(Airport airport);
    
    public ArrayList<Airport> findAll();

    public boolean update(Airport airport);

    public boolean delete(int id);

}
