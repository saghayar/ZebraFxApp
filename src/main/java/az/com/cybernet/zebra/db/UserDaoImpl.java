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
public class UserDaoImpl implements UserDao {

    DbImpl dbImpl = new DbImpl("az.com.cybernet.zebra_ZebraAppFx_jar_1.0-SNAPSHOTPU");

    @Override
    public User login(String username, String password) {
       return dbImpl.getEm()
                .createQuery("Select u from User u WHERE u.username=:username AND u.password=:password AND u.state=:state", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .setParameter("state", "1")
                .getSingleResult();
        
    }
}
