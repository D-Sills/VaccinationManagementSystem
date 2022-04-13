package controller;

import Utilities.Alerts;
import Utilities.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.VaccinationAppointment;
import model.VaccinationBooth;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.CentresController.*;

/**
 * @author Darren Sills
 * Vaccination Booths controller class which shows the Booths.Fxml scene.
 * Responsible for displaying and manipulating Vaccination Booths data in the selected Centre.
 */
public class BoothsController implements Initializable {
    private VaccinationBooth selectedBooth;
    private String identifier;
    private int floor;
    private final ObservableList<VaccinationBooth> allBooths = FXCollections.observableArrayList();

    @FXML
    private TableView<VaccinationBooth> boothsTableView;

    @FXML
    private TableColumn<VaccinationBooth, String> colIdentifier;

    @FXML
    private TableColumn<VaccinationBooth, Integer> colFloor;

    @FXML
    private TableColumn<VaccinationBooth, String> colWheelchair;

    @FXML
    private Label title;

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private TextField identifierTextfield;

    @FXML
    private Spinner<Integer> floorSpinner;

    @FXML
    private CheckBox wheelchairCheckBox;

    /**
     * Gathers the Booth's data from the selected Centre and populates the table fields
     * @param url used to resolve relative paths for root object
     * @param resourceBundle localizes the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!getSelectedCentre().getVaccinationBooths().isEmpty()) {
            title.setText(getSelectedCentre().getName() + " Booths");
            for (VaccinationBooth vaccinationBooth : getSelectedCentre().getVaccinationBooths()) {
                allBooths.add(vaccinationBooth);
            }
        }
        colIdentifier.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        colFloor.setCellValueFactory(new PropertyValueFactory<>("floorLevel"));
        colWheelchair.setCellValueFactory(new PropertyValueFactory<>("accessibility"));
        boothsTableView.setItems(allBooths);
    }

    //---------------------------------------------------------------//
    //Methods                                                        //
    //---------------------------------------------------------------//
    /**
     * When the table is clicked checks if a booth is selected and updates fields accordingly if so.
     */
    @FXML
    public void onTableClicked() {
        selectedBooth = boothsTableView.getSelectionModel().getSelectedItem();
        if (selectedBooth != null) {
            updateFields();
        }
    }

    /**
     * Gathers entered data from the textfield, spinner and checkbox
     */
    @FXML
    public void enteredInfo() {
        identifier = identifierTextfield.getText();
        floor = floorSpinner.getValue();
    }

    /**
     * Gathers data from the selected booth and updates the textfield, spinner and checkbox with that data
     */
    @FXML
    public void updateFields() {
        identifierTextfield.setText(selectedBooth.getIdentifier());
        floorSpinner.getValueFactory().setValue(selectedBooth.getFloorLevel());
        wheelchairCheckBox.setSelected(selectedBooth.getAccessibility().equals("Wheelchair Friendly"));
    }

    /**
     * Validates all entered information. First checks to see if any text-fields or combo boxes are blank,then checks the entered values.
     * Display alerts for any incorrect information.
     * if all information is valid, returns true.
     * @return true if valid, else returns false
     */
    private boolean validateInformation() {
        enteredInfo();
        if (identifier.isBlank()) {
            Alerts.genericWarning("Please fill out all fields");
            return false;
        }
        if (!Validation.validBoothIdentifier(identifier)) {
            Alerts.genericWarning("Invalid ID, must be one letter followed by a number");
            return false;
        }
        return true;
    }

    /**
     * Adds a Booth to the selected centre on button press, but only after successful validation and checking that no other booth has the same ID.
     */
    @FXML
    public void onActionAdd() {
        if (validateInformation()) {
            enteredInfo();
            String access = wheelchairCheckBox.isSelected() ? "Wheelchair Friendly" : "N/A"; //check the checkbox to see what string to assign the new object
            VaccinationBooth vaccinationBooth = new VaccinationBooth(identifier, floor, access);
            for (VaccinationBooth vaccinationBooth1 : getSelectedCentre().getVaccinationBooths()) { //check if another booth has the same id
                if (identifier.equalsIgnoreCase(vaccinationBooth1.getIdentifier())) {
                    Alerts.genericWarning("No two booths can have the same identifier!");
                    return;
                }
            }
            getSelectedCentre().getVaccinationBooths().add(vaccinationBooth);
            allBooths.add(vaccinationBooth);
            selectedBooth = null;
            boothsTableView.getSelectionModel().select(null);
        }
    }

    /**
     * Updates the selected Booth's data on button press, but only after successful validation.
     */
    @FXML
    public void onActionUpdate() {
        if(selectedBooth != null) {
            if (validateInformation()) {
                enteredInfo();
                String originalIdentifier = selectedBooth.getIdentifier();
                if (!(identifier.equalsIgnoreCase(originalIdentifier))) { //check if the id is being changed
                    for (VaccinationBooth vaccinationBooth : getSelectedCentre().getVaccinationBooths()) { //if it is make sure it's not already taken
                        if (identifier.equalsIgnoreCase(vaccinationBooth.getIdentifier())) {
                            Alerts.genericWarning("No two booths can have the same identifier!");
                            return;
                        }
                    }
                }
                selectedBooth.setFloorLevel(floor);
                selectedBooth.setAccessibility(wheelchairCheckBox.isSelected() ? "Wheelchair Friendly" : "N/A");
                selectedBooth.setIdentifier(identifier);
                boothsTableView.refresh();
                selectedBooth = null;
                boothsTableView.getSelectionModel().select(null);
                }
            } else {
            Alerts.genericWarning("No Booth selected!");
        }
    }

    /**
     * Removes the selected Booth from the system on button press.
     * If the Booth contains Appointments, those Appointments are moved to another Booth if possible.
     */
    @FXML
    public void onActionDelete() {
        if (selectedBooth != null) {
            if (!selectedBooth.getVaccinationAppointments().isEmpty()) {
                VaccinationBooth moveBooth;
                if (getSelectedCentre().getVaccinationBooths().size() > 1 && getSelectedCentre().getVaccinationBooths().indexOf(selectedBooth) != 0) { //make sure size is more than 1
                    moveBooth = getSelectedCentre().getVaccinationBooths().get(0); //move to first in the list if selected booth isn't first
                    for (VaccinationAppointment vaccinationAppointment : selectedBooth.getVaccinationAppointments()) {
                        vaccinationAppointment.setCentreBooth(getSelectedCentre().getName() + " - " + moveBooth.getIdentifier()); //update the name booth of the appointment for use in the appointment tables
                        moveBooth.getVaccinationAppointments().add(vaccinationAppointment);
                    }
                } else if (getSelectedCentre().getVaccinationBooths().size() > 1 && getSelectedCentre().getVaccinationBooths().indexOf(selectedBooth) == 0) {
                    moveBooth = getSelectedCentre().getVaccinationBooths().get(1); //move to second in the list if selected booth is first
                    for (VaccinationAppointment vaccinationAppointment : selectedBooth.getVaccinationAppointments()) {
                        vaccinationAppointment.setCentreBooth(getSelectedCentre().getName() + " - " + moveBooth.getIdentifier());
                        moveBooth.getVaccinationAppointments().add(vaccinationAppointment);
                    }
                }
            }
            getSelectedCentre().getVaccinationBooths().remove(selectedBooth);
            allBooths.remove(selectedBooth);
            selectedBooth = null;
            boothsTableView.getSelectionModel().select(null);
        } else {
            Alerts.genericWarning("No Booth selected!");
        }
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