/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.login;

import az.com.cybernet.zebra.db.UserDao;
import az.com.cybernet.zebra.db.UserDaoImpl;
import az.com.cybernet.zebra.main.MainApp;
import az.com.cybernet.zebra.main.MainController;
import az.com.cybernet.zebra.model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SAMIR-PC
 */
public class LoginController implements Initializable {

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @FXML
    public Label alertLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void handleBtnChooseClick() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Get Text");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("All Files", "*.*"));
        File phil = fc.showOpenDialog(null);
        if (phil != null) {
            try {
                String content
                        = new Scanner(phil)
                                .useDelimiter("\\Z")
                                .next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void authenticate(ActionEvent event) {
        UserDao userDao = new UserDaoImpl();
        String userName = Optional.ofNullable(this.username.getText()).orElse("");
        String password = Optional.ofNullable(this.password.getText()).orElse("");
        User user = userDao.login(userName, password);
        if (user != null) {
            try {
                MainController.currentUser = user;
                MainApp mw = new MainApp();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                mw.start(stage);
                handleBtnClose(event);
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alertLbl.setText("Invalid username or password");
        }

    }

    public void handleBtnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void handleBtnChooseClick(ActionEvent actionEvent) {

    }

}
