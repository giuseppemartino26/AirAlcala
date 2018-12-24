/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Administrator;

/**
 *
 * @author fabri
 */
public interface AdministratorDAO {
    
    public Administrator find(int id);

    public boolean insert(Administrator admin);

    public boolean update(Administrator admin);

    public boolean delete(int id);
    
    
}
