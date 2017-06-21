/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.create;

import az.com.cybernet.zebra.db.ProductDao;
import az.com.cybernet.zebra.db.ProductDaoImpl;
import az.com.cybernet.zebra.main.MainController;
import az.com.cybernet.zebra.model.Product;
import az.com.cybernet.zebra.util.OidGenerator;
import az.com.cybernet.zebra.util.QRUtils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author SAMIR-PC
 */
public class InsertController implements Initializable {

    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField quantity;

    @FXML
    private DatePicker date;

    public static TableView<Product> tableView; 
    public static TextField txtSearch;

    @FXML
    private void createProduct(ActionEvent event) {
        ProductDao productDao = new ProductDaoImpl();
        Product product = new Product();
        product.setProductId(productId.getText().trim());
        product.setName(productName.getText().trim()); 
        product.setDate(date.getValue().format(DateTimeFormatter.BASIC_ISO_DATE));
        product.setState("1");
        productDao.createProduct(product);

//        tableView.getItems().add(product);without filter
        List<Product> products = productDao.getAllProducts();
        MainController.tableData = FXCollections.observableArrayList(products);
        MainController.updateFilter(txtSearch, tableView);
        handleBtnClose(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        String pId = UUID.randomUUID().toString();
        String oid = OidGenerator.generateOid();
        String pId = oid.substring(oid.length() - 3, oid.length() - 2) + QRUtils.generateRandom(7) + oid.substring(oid.length() - 2, oid.length() - 1);

//        pId = pId.length() > 9 ? pId.substring(0, 9) : StringUtils.leftPad(pId, 9, '1');

        productId.setText(pId);
//        Image imageDecline = new Image(getClass().getResourceAsStream("/img/browse.png"));
//        create.setGraphic(new ImageView(imageDecline));
    }

    public void handleBtnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

}
