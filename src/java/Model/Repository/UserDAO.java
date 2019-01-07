/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

import Model.User;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public interface UserDAO {
    
    public User find(int id);

    public boolean insert(User user);

    public boolean update(User user);

    public boolean delete(int id);
    
    public ArrayList<User> findAll();
    
    public int login(String email, String pass);


}
