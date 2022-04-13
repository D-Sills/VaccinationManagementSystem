package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.VaccinationRecord;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.PatientsController.getSelectedPatient;

/**
 * @author Darren Sills
 * Vaccination Records controller class which shows the Records.Fxml scene.
 * Responsible for displaying the Vaccination Records of a selected Patient.
 */
public class RecordsController implements Initializable {
    private final ObservableList<VaccinationRecord> allRecords = FXCollections.observableArrayList();

    @FXML
    private TableView<VaccinationRecord> recordsTableView;

    @FXML
    private TableColumn<VaccinationRecord, String> colVaccine;

    @FXML
    private TableColumn<VaccinationRecord, String> colVaccineID;

    @FXML
    private TableColumn<VaccinationRecord, String> colTime;

    @FXML
    private TableColumn<VaccinationRecord, String> colDate;

    @FXML
    private TableColumn<VaccinationRecord, String> colDetails;

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private Label title;

    /**
     * Gathers the Record's data from the selected Patient and populates the table fields
     * @param url used to resolve relative paths for root object
     * @param resourceBundle localizes the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!getSelectedPatient().getVaccinationRecords().isEmpty()) {
            title.setText(getSelectedPatient().getName() + " Records");
            for (VaccinationRecord vaccinationRecord : getSelectedPatient().getVaccinationRecords()) {
                allRecords.add(vaccinationRecord);
            }
        }
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colVaccine.setCellValueFactory(new PropertyValueFactory<>("vaccinationType"));
        colVaccineID.setCellValueFactory(new PropertyValueFactory<>("vaccinationIdentifier"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("vaccinatorDetails"));
        recordsTableView.setItems(allRecords);
    }

    /**
     * Closes the window on button press
     */
    @FXML
    public void onActionQuit() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.hide();
    }
}
