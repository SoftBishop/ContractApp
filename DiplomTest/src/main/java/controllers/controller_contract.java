package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modeltables.Tableview_Contract;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Contract implements Initializable {

    @FXML
    private Controller_Auth controller_auth;

    public void setController_auth(Controller_Auth controller_auth) {
        this.controller_auth = controller_auth;
    }

    @FXML
    private Controller_Contract_Editor controller_contract_editor;

    @FXML
    private TableView<Tableview_Contract> contractTable;

    @FXML
    private TableColumn<Tableview_Contract, String> numberContractColumnTable;

    @FXML
    private TableColumn<Tableview_Contract, String> typeContractColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> dateCreationColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> dateExecColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> dateFinishedColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> clientColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> organizationColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> employerColTable;

    @FXML
    private TableColumn<Tableview_Contract, String> placementIDCol;

    @FXML
    private TableColumn<Tableview_Contract, String> priceContractCol;

    @FXML
    private Button estimateButton;

    @FXML
    private Button ClientFormButton;

    @FXML
    private Button generatePlanButton;

    @FXML
    private Button organizationFormButton;

    @FXML
    private Button CreateContractButton;

    @FXML
    private Button placementFormButton;

    @FXML
    private Button importFormButton;

    @FXML
    private Button exportContractButton;

    @FXML
    void GeneratePlanFormButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_generate_plan.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenClientForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_client.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenEstimate(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_estimate.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenExportFormContract(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_export_contract.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenFormCreateContract(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_contract_editor.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception ex)
        {
            System.out.println(ex);
        }

    }



    @FXML
    void OpenOrganizationContract(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_oraganization.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenPlacementForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_placement.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }


    private ObservableList<Tableview_Contract> olist = FXCollections.observableArrayList();
    private void FillContractTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT contracts.dogovorid AS NUMCONTRACTID, \n" +
                    "TO_CHAR(contracts.DateOfCreationContract :: DATE, 'dd.mm.yyyy') AS DATECREATECONTRACT,\n" +
                    "TO_CHAR(contracts.DateExecution :: DATE, 'dd.mm.yyyy') AS DATEEXECUITONCONTRACT, \n" +
                    "TO_CHAR(contracts.DateExpire :: DATE, 'dd.mm.yyyy') AS DATEEXPIRECONTRACT,  \n" +
                    "contracts.Price AS CONTRACTPRICE, typecontracts.NameTypeContract AS NAMETYPECONTRACT, \n" +
                    "clients.FIO as CLIENTFIO, organizations.NameOrganization AS NAMEORGANIZATION,\n" +
                    "Employers.FIO as EMPLOYERFIO , placements.placementid as NUMPLACE\n" +
                    "FROM contracts join typecontracts \n" +
                    "on contracts.typecontract = typecontracts.typecontractid \n" +
                    "join clients on contracts.Clients = clients.Client_ID\n" +
                    "join Organizations on contracts.Organizations = Organizations.organizationid\n" +
                    "join Employers on contracts.Employers = Employers.EmployerID\n" +
                    "join placements on placements.placementid = contracts.placement");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                olist.add(new Tableview_Contract(rs.getString("NUMCONTRACTID"),
                        rs.getString("NAMETYPECONTRACT"),
                        rs.getString("DATECREATECONTRACT"),
                        rs.getString("DATEEXECUITONCONTRACT"),
                        rs.getString("DATEEXPIRECONTRACT"),
                        rs.getString("CLIENTFIO"),
                        rs.getString("NAMEORGANIZATION"),
                        rs.getString("EMPLOYERFIO"),
                        rs.getString("NUMPLACE"),
                        rs.getString("CONTRACTPRICE")));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        numberContractColumnTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractID"));
        typeContractColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractType"));
        dateCreationColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractDateOfCreation"));
        dateExecColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractDateExec"));
        dateFinishedColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractDateFinished"));
        clientColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("nameClient"));
        organizationColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("nameOrganization"));
        employerColTable.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("nameEmployer"));
        placementIDCol.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("placementID"));
        priceContractCol.setCellValueFactory(new PropertyValueFactory<Tableview_Contract,String>("contractPrice"));

        contractTable.setItems(olist);

    }

    private void DoubleClick()
    {
        contractTable.setRowFactory( tv -> {
            TableRow<Tableview_Contract> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Tableview_Contract rowData = row.getItem();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_contract_editor.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        controller_contract_editor = fxmlLoader.getController();
                        controller_contract_editor.setController_contract(this);
                        controller_contract_editor.SetElements(
                                rowData.getContractID(),
                                rowData.getContractDateOfCreation(),
                                rowData.getContractDateExec(),
                                rowData.getContractDateFinished(),
                                rowData.getContractType(),
                                rowData.getNameClient(),
                                rowData.getNameOrganization(),
                                rowData.getNameEmployer(),
                                rowData.getPlacementID(),
                                rowData.getContractPrice()
                        );
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
        FillContractTableView();
        DoubleClick();

    }
}
