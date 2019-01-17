/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Route;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface RouteDAO {

    public Route find(int id);

    public boolean insert(Route route);

    public boolean update(Route route);

    public boolean delete(int id);

    public ArrayList<Route> findAll();
    
    public ArrayList<Route> findRoute(int origin, int destination);

}
