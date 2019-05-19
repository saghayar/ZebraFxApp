package az.com.cybernet.zebra.main;

import az.com.cybernet.zebra.create.InsertController;
import az.com.cybernet.zebra.create.InsertProduct;
import az.com.cybernet.zebra.db.ProductDao;
import az.com.cybernet.zebra.db.ProductDaoImpl;
import az.com.cybernet.zebra.delete.DeleteController;
import az.com.cybernet.zebra.delete.DeleteProduct;
import az.com.cybernet.zebra.edit.EditController;
import az.com.cybernet.zebra.edit.EditProduct;
import az.com.cybernet.zebra.model.Product;
import az.com.cybernet.zebra.model.User;
import az.com.cybernet.zebra.print.PrintWithZPL2;
import az.com.cybernet.zebra.util.QRUtils;
import az.com.cybernet.zebra.util.SignUtils;
import fr.w3blog.zpl.model.ZebraPrintException;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SAMIR-PC
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private ImageView qrCode;

    @FXML
    private TextField search;

    public static ObservableList<Product> tableData;

    public static User currentUser;
    public String finalData, productName;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        ProductDao productDao = new ProductDaoImpl();
        List<Product> products = productDao.getAllProducts();
        tableData = FXCollections.observableArrayList(products);

        TableColumn<Product, String> columnHSCode = new TableColumn<>("HS-Code");
        columnHSCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        columnHSCode.prefWidthProperty().bind(productTableView.widthProperty().divide(5));

        TableColumn<Product, String> columnGS1Code = new TableColumn<>("GS1-Code");
        columnGS1Code.setCellValueFactory(new PropertyValueFactory<>("gs1Code"));
        columnGS1Code.prefWidthProperty().bind(productTableView.widthProperty().divide(5));

        TableColumn<Product, String> columnProductName = new TableColumn<>("Name");
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductName.prefWidthProperty().bind(productTableView.widthProperty().divide(5));

        TableColumn<Product, String> columnDate = new TableColumn<>("DATE");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.prefWidthProperty().bind(productTableView.widthProperty().divide(5));

        TableColumn<Product, String> columnDesc = new TableColumn<>("Description");
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDesc.prefWidthProperty().bind(productTableView.widthProperty().divide(5));

        productTableView.getColumns().add(columnHSCode);
        productTableView.getColumns().add(columnGS1Code);
        productTableView.getColumns().add(columnProductName);
        productTableView.getColumns().add(columnDate);
        productTableView.getColumns().add(columnDesc);

        productTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Product p = productTableView.getSelectionModel().getSelectedItem();
                String data = p.getProductId() + "_" + p.getGs1Code() + "_" + p.getDate();
                String signedData = SignUtils.signData(data, currentUser.getPrivateKey());
                finalData = data + "_" + signedData;
                productName = p.getName();
                System.out.println(finalData.length());
                BufferedImage image = QRUtils.getImageQR(finalData);

                qrCode.setImage(SwingFXUtils.toFXImage(image, null));
            }
        });
        updateFilter(search, productTableView);

    }

    public static void updateFilter(TextField search, TableView<Product> productTableView) {

        FilteredList<Product> filteredData = new FilteredList<>(tableData, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getProductId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches by id
                } else if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  name.
                } else if (product.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches   date.
                } else if (product.getGs1Code().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches   date.
                } else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches   date.
                }
                return false; // Does not match.
            });
        });

        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedData);
    }

    public void insertSection() {
        try {
            InsertController.tableView = productTableView;
            InsertController.txtSearch = search;
            InsertProduct insert = new InsertProduct();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL.APPLICATION_MODAL);
            insert.start(stage);

        } catch (Exception ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSection() {
        try {
            EditController.selectedProductId = productTableView.getSelectionModel().getSelectedItem().getId();
            EditController.index = productTableView.getSelectionModel().getSelectedIndex();
            EditController.tableView = productTableView;
            EditController.txtSearch = search;
            EditProduct edit = new EditProduct();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            edit.start(stage);

        } catch (Exception ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSection() {
        try {
            DeleteController.tableView = productTableView;
            DeleteController.txtSearch = search;
            DeleteController.selectedProductId = productTableView.getSelectionModel().getSelectedItem().getId();
            DeleteProduct delete = new DeleteProduct();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            delete.start(stage);

        } catch (Exception ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printQr() {
        try {
            PrintWithZPL2.print(finalData, productName);

        } catch (ZebraPrintException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
