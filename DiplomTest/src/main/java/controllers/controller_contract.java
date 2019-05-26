package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modeltables.tableview_contract;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class controller_contract implements Initializable {



    @FXML
    private TableView<tableview_contract> contractTable;

    @FXML
    private TableColumn<tableview_contract, String> numberContractColumnTable;

    @FXML
    private TableColumn<tableview_contract, String> typeContractColTable;

    @FXML
    private TableColumn<tableview_contract, String> dateCreationColTable;

    @FXML
    private TableColumn<tableview_contract, String> dateExecColTable;

    @FXML
    private TableColumn<tableview_contract, String> dateFinishedColTable;

    @FXML
    private TableColumn<tableview_contract, String> clientColTable;

    @FXML
    private TableColumn<tableview_contract, String> organizationColTable;

    @FXML
    private TableColumn<tableview_contract, String> employerColTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillContractTableView();
    }

    private ObservableList<tableview_contract> olist = FXCollections.observableArrayList();
    private void FillContractTableView() {
        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT contracts.dogovorid, contracts.DateOfCreationContract,contracts.DateExecution,\n" +
                    "contracts.DateExpire, contracts.Price,\n" +
                    "typecontracts.NameTypeContract,\n" +
                    "clients.FIO as clientfio,\n" +
                    "organizations.NameOrganization,\n" +
                    "Employers.FIO as employerfio\n" +
                    "FROM contracts join typecontracts \n" +
                    "on contracts.TypeContractsTypeContractID=typecontracts.typecontractid\n" +
                    "join clients on contracts.Clients = clients.Client_ID\n" +
                    "join Organizations on contracts.Organizations = Organizations.organizationid\n" +
                    "join Employers on  contracts.Employers = Employers.EmployerID");

            ResultSet rs = preparedStatement.executeQuery();
                while(rs.next())
                {
                    olist.add(new tableview_contract(rs.getString("dogovorid"),
                                                    rs.getString("NameTypeContract"),
                                                    rs.getString("DateOfCreationContract"),
                                                    rs.getString("DateExecution"),
                                                    rs.getString("DateExpire"),
                                                    rs.getString("clientfio"),
                                                    rs.getString("NameOrganization"),
                                                    rs.getString("employerfio")));
                }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        numberContractColumnTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("contractID"));
        typeContractColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("contractType"));
        dateCreationColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("contractDateOfCreation"));
        dateExecColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("contractDateExec"));
        dateFinishedColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("contractDateFinished"));
        clientColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("nameClient"));
        organizationColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("nameOrganization"));
        employerColTable.setCellValueFactory(new PropertyValueFactory<tableview_contract,String>("nameEmployer"));

        contractTable.setItems(olist);
    }
}
