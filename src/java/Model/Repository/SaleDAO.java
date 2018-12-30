/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.Sale;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface SaleDAO {

    public Sale find(int id);

    public boolean insert(Sale sale);

    public boolean update(Sale sale);

    public boolean delete(int id);
    
    public ArrayList<Sale> findAll();


}
