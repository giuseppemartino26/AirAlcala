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
public class Route {

    private int id;
    private Airport origin;
    private Airport destination;
    private Airplane plane;
    private int ticketPrice;
    private int tax;
    private int luggagePrice;

    public Route(int id, Airport origin, Airport destination, Airplane plane, int ticketPrice, int tax, int luggagePrice) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.plane = plane;
        this.ticketPrice = ticketPrice;
        this.tax = tax;
        this.luggagePrice = luggagePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airplane getPlane() {
        return plane;
    }

    public void setPlane(Airplane plane) {
        this.plane = plane;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getLuggagePrice() {
        return luggagePrice;
    }

    public void setLuggagePrice(int luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

}
