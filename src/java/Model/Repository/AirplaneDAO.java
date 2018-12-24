/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Airplane;

/**
 *
 * @author fabri
 */

/*
* This interface is based on the design pattern Repository
* by Eric Evans (Domain-Driven Design: Tackling Complexity in the Heart of Software)
 */
public interface AirplaneDAO { //DAO = Data Access Object

    public Airplane find(int id);

    public boolean insert(Airplane airplane);

    public boolean update(Airplane airplane);

    public boolean delete(int id);

}
