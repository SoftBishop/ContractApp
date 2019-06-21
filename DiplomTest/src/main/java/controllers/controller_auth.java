package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.util.Properties;


public class Controller_Auth {

    @FXML
    private Controller_Contract controller_contract;




    @FXML
    private TextField login_field;

    @FXML
    private Button buttonAuth;

    @FXML
    private PasswordField password_field;


    @FXML
    public void initialize()
    {
        System.out.println("start");

    }
    ErrorFormClass errorForm;

    @FXML
    void Auth(ActionEvent event) {
        try {
            FileInputStream in = new FileInputStream("D:\\Diplom\\DiplomTest\\src\\main\\java\\SqlClasses\\database.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("D:\\Diplom\\DiplomTest\\src\\main\\java\\SqlClasses\\database.properties");
            props.setProperty("db.username", login_field.getText());
            props.setProperty("db.password", password_field.getText());
            props.store(out, null);
            out.close();

            try {
                Connection connection;
                connection = ConnectionPool.getDataSource().getConnection();


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_contract.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Договора");
                stage.show();
                controller_contract = fxmlLoader.getController();
                controller_contract.setController_auth(this);

                connection.close();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }catch (Exception ex)
            {
                errorForm = new ErrorFormClass();
                errorForm.OpenErrorForm(ex);

            }
         }
        catch (Exception ex)
        {
          errorForm = new ErrorFormClass();
          errorForm.OpenErrorForm(ex);
        }
    }



}
