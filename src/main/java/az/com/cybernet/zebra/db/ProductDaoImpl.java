/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.db;

import az.com.cybernet.zebra.model.Product;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author SAMIR-PC
 */
public class ProductDaoImpl implements ProductDao {

    DbImpl dbImpl = new DbImpl("az.com.cybernet.zebra_ZebraAppFx_jar_1.0-SNAPSHOTPU");

    @Override
    public void createProduct(Product product) {
        try {
            dbImpl.getTransaction().begin();
            dbImpl.getEm().persist(product);
            dbImpl.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        TypedQuery<Product> query = dbImpl.getEm().createQuery("SELECT p FROM Product p where p.state=:state", Product.class);
        query.setParameter("state", "1");
        return query.getResultList();
    }

    @Override
    public Product findProductByProductId(String productId) {
        TypedQuery<Product> query = dbImpl.getEm()
                .createQuery("SELECT p FROM Product p WHERE p.productId=:productId AND p.state=:state ", Product.class);
        query.setParameter("productId", productId);
        query.setParameter("state", "1");
        return query.getSingleResult();
    }

    @Override
    public Product findProductById(Long id) {
        return dbImpl.getEm().find(Product.class, id);
    }

    @Override
    public void removeProduct(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editProduct(Product product) {
        try {
            dbImpl.getTransaction().begin();
            dbImpl.getEm().merge(product);
            dbImpl.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
