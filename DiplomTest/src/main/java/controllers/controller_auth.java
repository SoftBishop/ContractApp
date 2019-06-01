package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    void TestAction(ActionEvent event) {

        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_contract.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                controller_contract = fxmlLoader.getController();
                controller_contract.setController_auth(this);
    }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }



}
