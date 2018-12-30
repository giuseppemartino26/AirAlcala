/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Reservation;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface ReservationDAO {

    public Reservation find(int id);

    public boolean insert(Reservation reservation);

    public boolean update(Reservation reservation);

    public boolean delete(int id);
    
    public ArrayList<Reservation> findAll();


}
