package az.com.cybernet.zebra.delete;

import az.com.cybernet.zebra.db.ProductDao;
import az.com.cybernet.zebra.db.ProductDaoImpl;
import az.com.cybernet.zebra.main.MainController;
import az.com.cybernet.zebra.model.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author SAMIR-PC
 */
public class DeleteController implements Initializable {

    public static TableView<Product> tableView;
    public static TextField txtSearch;
    public static Long selectedProductId;

    @FXML
    private void deleteProduct(ActionEvent event) {
        ProductDao productDao = new ProductDaoImpl();
        Product p = productDao.findProductById(selectedProductId);
        p.setState("0");
        productDao.editProduct(p);

        List<Product> products = productDao.getAllProducts();
        MainController.tableData = FXCollections.observableArrayList(products);
        MainController.updateFilter(txtSearch, tableView);
//        tableView.getItems().remove(p);///without filter ,
        handleBtnClose(event);
    }

    public void handleBtnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
