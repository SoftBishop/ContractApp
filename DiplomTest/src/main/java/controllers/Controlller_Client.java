package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modeltables.Tableview_Client;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controlller_Client implements Initializable {

    @FXML
    private TableView<Tableview_Client> clientTableView;

    @FXML
    private TableColumn<Tableview_Client, String> fioCol;

    @FXML
    private TableColumn<Tableview_Client, String> nameOrgCol;

    @FXML
    private TextField fioTextField;

    @FXML
    private ComboBox<?> nameOrgComboBox;

    @FXML
    private Button deleteClientButton;

    @FXML
    private Button editClientButton;

    @FXML
    private Button addClientButton;

    @FXML
    void AddClient(ActionEvent event) {

    }

    @FXML
    void DeleteClient(ActionEvent event) {

    }

    @FXML
    void EditClient(ActionEvent event) {

    }

    private ObservableList<Tableview_Client> clientList = FXCollections.observableArrayList();
    private void FillClientTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT clients.fio AS FIO, organizations.nameorganization as NAMEORG  " +
                    "FROM clients \n" +
                    "JOIN organizations ON organizations.organizationid = clients.organizations");


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                clientList.add(new Tableview_Client(rs.getString("FIO"),
                        rs.getString("NAMEORG")
                        ));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        fioCol.setCellValueFactory(new PropertyValueFactory<Tableview_Client,String>("fio"));
        nameOrgCol.setCellValueFactory(new PropertyValueFactory<Tableview_Client,String>("nameOrg"));


        clientTableView.setItems(clientList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillClientTableView();
    }
}
