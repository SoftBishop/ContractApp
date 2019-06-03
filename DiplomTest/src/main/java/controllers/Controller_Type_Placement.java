package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Type_Placement {

    @FXML
    private TextField nameTypePlacementTextField;

    @FXML
    private Button addTypePlacementButton;

    @FXML
    private Button deleteTypePlacmentButton;

    @FXML
    void AddTypePlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL insertplacementtype(?)");
            preparedStatement.setString(1,nameTypePlacementTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeleteTypePlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL removeplacementtype(?)");
            preparedStatement.setString(1,nameTypePlacementTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

}
