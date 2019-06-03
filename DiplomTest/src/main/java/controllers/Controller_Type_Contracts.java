package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Contracts {

    @FXML
    private TextField nmaeTypeContractTextField;

    @FXML
    private Button addTypeContractButton;

    @FXML
    private Button deleteTypeWorkButton;

    @FXML
    void AddTypeContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL inserttypecontracts(?)");
            preparedStatement.setString(1,nmaeTypeContractTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

    @FXML
    void DeleteTypeContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL removetypecontracts(?)");
            preparedStatement.setString(1,nmaeTypeContractTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

}
