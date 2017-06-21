package az.com.cybernet.zebra.edit;

import az.com.cybernet.zebra.db.ProductDao;
import az.com.cybernet.zebra.db.ProductDaoImpl;
import az.com.cybernet.zebra.main.MainController;
import az.com.cybernet.zebra.model.Product;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author SAMIR-PC
 */
public class EditController implements Initializable {

    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField quantity;
    @FXML
    private Button create;
    @FXML
    private DatePicker date;

    public static TableView<Product> tableView;
    public static TextField txtSearch;

    public static Long selectedProductId;
    public static int index;

    @FXML
    private void editProduct(ActionEvent event) {
        ProductDao productDao = new ProductDaoImpl();
        Product product = productDao.findProductById(selectedProductId);
        product.setProductId(productId.getText().trim());
        product.setName(productName.getText().trim());
        product.setDate(date.getValue().format(DateTimeFormatter.BASIC_ISO_DATE));
        productDao.editProduct(product);

        List<Product> products = new ProductDaoImpl().getAllProducts();
        MainController.tableData.clear();
        MainController.tableData = FXCollections.observableArrayList(products);
        MainController.updateFilter(txtSearch, tableView);
        handleBtnClose(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductDao productDao = new ProductDaoImpl();
        Product p = productDao.findProductById(selectedProductId);

        productId.setText(p.getProductId());
        productName.setText(p.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        date.setValue(LocalDate.parse(p.getDate(), formatter));
    }

    public void handleBtnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

}
