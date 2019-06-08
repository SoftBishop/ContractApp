package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Works {

    ErrorFormClass errorFormClass;
    @FXML
    private TextField nameTypeWork;

    @FXML
    private Button addTypeWorkButton;

    @FXML
    private Button deleteTypeContractButton;

    @FXML
    void AddTypeWork(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN;\n" +
                    "INSERT INTO typeworks(typeworkid,typeworkname) \n" +
                    "VALUES (DEFAULT,?)\n" +
                    "END;");
            preparedStatement.setString(1,nameTypeWork.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
            connection.close();
            addTypeWorkButton.setStyle("-fx-background-color: #00ff00");
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void DeleteTypeWork(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN;\n" +
                    "DELETE FROM typeworks\n" +
                    "WHERE typeworkname = ?\n" +
                    "END;");
            preparedStatement.setString(1,nameTypeWork.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
            connection.close();

        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

}
