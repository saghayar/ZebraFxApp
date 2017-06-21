/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.db;

import az.com.cybernet.zebra.model.User;

/**
 *
 * @author SAMIR-PC
 */
public interface UserDao {
       User login(String username,String password);
}
