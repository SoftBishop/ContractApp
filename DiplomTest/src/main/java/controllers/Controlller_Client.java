package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modeltables.Tableview_Client;
import modeltables.Tableview_Contract;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controlller_Client implements Initializable {

    ErrorFormClass errorFormClass;
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
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            CallableStatement callableStatement = connection.prepareCall("BEGIN;" +
                    "INSERT INTO clients(client_id,fio,organizations)\n" +
                    "VALUES (DEFAULT,?,\n" +
                    "(select OrganizationId from organizations \n" +
                    "where NameOrganization = ?));" +
                    "COMMIT;");

            callableStatement.setString(1,fioTextField.getText());
            callableStatement.setString(2,nameOrgComboBox.getEditor().getText());

            callableStatement.executeQuery();

            callableStatement.close();

        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        FillallElements();
    }

    @FXML
    void DeleteClient(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            CallableStatement callableStatement = connection.prepareCall("begin;" +
                    "DELETE FROM clients\n" +
                    "WHERE client_id = ?;" +
                    "COMMIT;");

            callableStatement.setInt(1,idClient);

            callableStatement.executeQuery();

            callableStatement.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);

        }
        FillallElements();
    }

    @FXML
    void EditClient(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("begin;\n" +
                    "UPDATE\n" +
                    "    clients\n" +
                    "SET\n" +
                    "    fio = ?,\n" +
                    "    organizations = orgTable.organizationid\n" +
                    "FROM\n" +
                    "    clients AS ClientTable\n" +
                    "    JOIN organizations AS orgTable\n" +
                    "        ON ClientTable.organizations = orgTable.organizationId\n" +
                    "WHERE\n" +
                    "    orgTable.nameorganization = ? and clients.client_id = ?;\n" +
                    "commit;");
            preparedStatement.setString(1,fioTextField.getText());
            preparedStatement.setString(2,nameOrgComboBox.getEditor().getText());
            preparedStatement.setInt(3,idClient);
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }

        FillallElements();

    }

    private ObservableList<Tableview_Client> clientList = FXCollections.observableArrayList();
    private void FillClientTableView() {
        clientList.clear();
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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        fioCol.setCellValueFactory(new PropertyValueFactory<Tableview_Client,String>("fio"));
        nameOrgCol.setCellValueFactory(new PropertyValueFactory<Tableview_Client,String>("nameOrg"));


        clientTableView.setItems(clientList);
    }

    private ObservableList nameOrgList = FXCollections.observableArrayList();

    public void FillNameOrgComboBox()
    {
        nameOrgList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT nameorganization FROM organizationS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                nameOrgList.add(rs.getString("nameorganization"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        nameOrgComboBox.setItems(nameOrgList);
    }

    public void FillallElements()
    {
        FillClientTableView();
        FillNameOrgComboBox();
    }

    private int idClient;
    private void GetDataByClick()
    {
        clientTableView.setRowFactory( tv -> {
            TableRow<Tableview_Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Tableview_Client rowData = row.getItem();
                    try {

                        Connection connection;
                        connection = ConnectionPool.getDataSource().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("" +
                                "SELECT client_id FROM clients where fio = ?");
                        preparedStatement.setString(1,rowData.getFio());
                        ResultSet rs = preparedStatement.executeQuery();
                        while(rs.next())
                        {
                            idClient = Integer.parseInt(rs.getString("client_id"));
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillallElements();
        GetDataByClick();
    }
}
