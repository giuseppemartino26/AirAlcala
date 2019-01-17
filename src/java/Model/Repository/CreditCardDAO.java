/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.CreditCard;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface CreditCardDAO {

    public CreditCard find(int id);

    public ArrayList<CreditCard> findAllByUserId(int userId);

    public boolean insert(CreditCard creditCard);

    public boolean update(CreditCard creditCard);

    public boolean delete(int id);

}
