/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author fabri
 */
public class Flight {

    private int id;
    private String locator;
    private Route route;
    private java.sql.Date departure;
    private java.sql.Date arrival;

    public Flight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
        
    }

    public java.sql.Date getDeparture() {
        return departure;
    }

    public void setDeparture(java.sql.Date departure) {
        this.departure = departure;
    }

    public java.sql.Date getArrival() {
        return arrival;
    }

    public void setArrival(java.sql.Date arrival) {
        this.arrival = arrival;
    }
}
