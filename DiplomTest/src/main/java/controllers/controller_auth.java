package controllers;

import SqlClasses.SQLMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controller_auth {

    @FXML
    private TextField login_field;

    @FXML
    private Button buttonAuth;

    @FXML
    private PasswordField password_field;

    @FXML
    void TestAction(ActionEvent event) {
        try {
            SQLMethods sqlMethod = new SQLMethods();
            sqlMethod.getUsernameData(login_field.getText(), buttonAuth.getText());
            if(sqlMethod.getPrivelege() == 0)
            {
                System.out.println("Введены неверные данные!");
            }else if(sqlMethod.getPrivelege() == 1)
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_contract.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }else if(sqlMethod.getPrivelege() == 2)
            {
                System.out.println("estimate");
            }

        } catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

}
