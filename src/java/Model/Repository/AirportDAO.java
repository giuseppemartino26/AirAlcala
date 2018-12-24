/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Airport;

/**
 *
 * @author fabri
 */
public interface AirportDAO {

    public Airport find(int id);

    public boolean insert(Airport airport);

    public boolean update(Airport airport);

    public boolean delete(int id);

}
