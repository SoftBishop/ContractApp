package controllers;

import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Positions {

    @FXML
    private TextField namePositionTextField;

    @FXML
    private Button addPositionButton;

    @FXML
    private Button deletePositionButton;

    @FXML
    void AddPosition(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL insertpositions(?)");
            preparedStatement.setString(1,namePositionTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeletePosition(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CALL removepositions(?)");
            preparedStatement.setString(1,namePositionTextField.getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

}
