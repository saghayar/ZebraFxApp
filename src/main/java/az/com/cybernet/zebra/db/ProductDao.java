package az.com.cybernet.zebra.db;

import az.com.cybernet.zebra.model.Product;
import az.com.cybernet.zebra.model.User;
import java.util.List;

/**
 *
 * @author SAMIR-PC
 */
public interface ProductDao {

    void createProduct(Product product);

    void editProduct(Product product);

    List<Product> getAllProducts();

    Product findProductByProductId(String productId);

    Product findProductById(Long id);

    void removeProduct(Long id);
    
 

}
