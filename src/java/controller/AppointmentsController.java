package controller;

import Utilities.Alerts;
import Utilities.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.darrensills.Main.vacSys;

/**
 * @author Darren Sills
 * Appointment controller class which shows the Appointments.Fxml scene.
 * Responsible for displaying and manipulating Appointment data.
 */
public class AppointmentsController implements Initializable {
    private VaccinationAppointment selectedAppointment;
    private VaccinationBooth chosenBooth;
    private String booth;
    private LocalDate date;
    private String time;
    private String vaccineID;
    private String vaccineType;
    private String vaccinator;
    private String ppsn;
    private final ObservableList<VaccinationAppointment> allAppointments = FXCollections.observableArrayList();
    private final ObservableList<String> allCentres = FXCollections.observableArrayList();
    private final ObservableList<String> allBooths = FXCollections.observableArrayList();
    private final ObservableList<String> allVaccinators = FXCollections.observableArrayList();
    private final ObservableList<String> allVaccines = FXCollections.observableArrayList();

    @FXML
    private TableView<VaccinationAppointment> appointmentsTableView;

    @FXML
    private TableColumn<VaccinationAppointment, String> colName;

    @FXML
    private TableColumn<VaccinationAppointment, String> colVaccineType;

    @FXML
    private TableColumn<VaccinationAppointment, String> colVaccineID;

    @FXML
    private TableColumn<VaccinationAppointment, String> colTime;

    @FXML
    private TableColumn<VaccinationAppointment, String> colDate;

    @FXML
    private TableColumn<VaccinationAppointment, String> colVaccinator;

    @FXML
    private TableColumn<VaccinationAppointment, String> colPatientPPSN;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private ComboBox<String> vaccineTypeComboBox;

    @FXML
    private TextField vaccineIDTextField;

    @FXML
    private ComboBox<String> vaccinatorComboBox;

    @FXML
    private TextField ppsnTextField;

    @FXML
    private TextField smartAddTextField;

    @FXML
    private TextField viewPatientTextField;

    @FXML
    private ComboBox<String> viewVaccineTypeComboBox;

    @FXML
    private TextField viewVaccineIDTextField;

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private ComboBox<String> centreComboBox;

    @FXML
    private ComboBox<String> boothComboBox;

    /**
     * Constructor that initializes various observable lists and comboboxes, as well as adding some simple data to those boxes
     */
    public AppointmentsController() {
        allVaccinators.addAll("Bill", "Bob", "Bud", "Sol Badguy");
        allVaccines.addAll("AstraZeneca", "Moderna", "Pfizer", "Johnson & Johnson");
        centreComboBox = new ComboBox<>();
        boothComboBox = new ComboBox<>();
        vaccineTypeComboBox = new ComboBox<>();
        viewVaccineTypeComboBox = new ComboBox<>();
        vaccinatorComboBox = new ComboBox<>();
    }

    /**
     * Gathers the Appointment's data from the system and populates the table fields
     * @param url used to resolve relative paths for root object
     * @param resourceBundle localizes the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!vacSys.getVaccinationCentres().isEmpty()) {
            for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                allCentres.add(vaccinationCentre.getName());
                if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                    for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                        allBooths.add(vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier()); //use strings instead of a booth object for user readability, can't use centre name in the booth toString as far as i know
                    }
                }
            }
        }
        appointmentsTableView.setRowFactory(new Callback<>() { //code to change the row appearance
            @Override
            public TableRow<VaccinationAppointment> call(TableView<VaccinationAppointment> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(VaccinationAppointment vaccinationAppointment, boolean empty) {
                        super.updateItem(vaccinationAppointment, empty);

                        if (vaccinationAppointment instanceof VaccinationRecord) { //if the row is a record, change the colour
                            setStyle("-fx-background-color: #ff6961");
                        } else { //reset if not
                            setStyle(null);
                        }
                    }
                };
            }
        });
        colName.setCellValueFactory(new PropertyValueFactory<>("centreBooth"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colVaccineType.setCellValueFactory(new PropertyValueFactory<>("vaccinationType"));
        colVaccineID.setCellValueFactory(new PropertyValueFactory<>("vaccinationIdentifier"));
        colVaccinator.setCellValueFactory(new PropertyValueFactory<>("vaccinatorDetails"));
        colPatientPPSN.setCellValueFactory(new PropertyValueFactory<>("patientIdentifier"));

        centreComboBox.setItems(allCentres);
        boothComboBox.setItems(allBooths);
        vaccinatorComboBox.setItems(allVaccinators);
        viewVaccineTypeComboBox.setItems(allVaccines);
        vaccineTypeComboBox.setItems(allVaccines);
        onActionViewAll(); //populate with all appointments by default
    }

    //---------------------------------------------------------------//
    //Tab 1                                                          //
    //---------------------------------------------------------------//
    /**
     * Populates the table with all appointments in the system on button press
     */
    @FXML
    public void onActionViewAll() {
        allAppointments.clear(); //clear the list to avoid duplicates
        if (!vacSys.getVaccinationCentres().isEmpty()) {
            for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                    for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                        if (!vaccinationBooth.getVaccinationAppointments().isEmpty()) {
                            for (VaccinationAppointment vaccinationAppointment : vaccinationBooth.getVaccinationAppointments()) {
                                allAppointments.add(vaccinationAppointment);
                            }
                        }
                    }
                }
            }
        }
        appointmentsTableView.setItems(allAppointments);
    }

    /**
     * Populates the table with only appointments from the chosen centre on button press
     */
    @FXML
    public void onActionViewChosen() {
        String centre = centreComboBox.getValue();
        VaccinationCentre chosenCentre = null;
        if (!vacSys.getVaccinationCentres().isEmpty()) {
            for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                if (vaccinationCentre.getName().equalsIgnoreCase(centre)) {
                    chosenCentre = vaccinationCentre;
                }
            }
        }
        if (chosenCentre == null) {
            Alerts.genericWarning("No Vaccination centre chosen!");
        } else {
            allAppointments.clear();
            if (!chosenCentre.getVaccinationBooths().isEmpty()) {
                for (VaccinationBooth vaccinationBooth : chosenCentre.getVaccinationBooths()) {
                    if (!vaccinationBooth.getVaccinationAppointments().isEmpty()) {
                        for (VaccinationAppointment vaccinationAppointment : vaccinationBooth.getVaccinationAppointments()) {
                            allAppointments.add(vaccinationAppointment);
                        }
                    }
                }
            }
            appointmentsTableView.setItems(allAppointments);
        }
    }

    /**
     * Populates the table with all appointments and records using the chosen vaccine/vaccineID on button press
     */
    @FXML
    public void onActionViewVaccine() {
        if (viewVaccineTypeComboBox.getValue() == null || viewVaccineIDTextField.getText().isBlank()) {
            Alerts.genericWarning("Please fill out all fields");
            return;
        }
        allAppointments.clear();
        if (!vacSys.getPatients().isEmpty()) {
            for (Patient patient : vacSys.getPatients()) {
                for (VaccinationRecord vaccinationRecord : patient.getVaccinationRecords()) {
                    if (vaccinationRecord.getVaccinationType().equalsIgnoreCase(viewVaccineTypeComboBox.getValue()) && vaccinationRecord.getVaccinationIdentifier().equals(viewVaccineIDTextField.getText())) {
                        allAppointments.add(vaccinationRecord);
                    }
                }
            }
        }

        String chosenVaccine = viewVaccineTypeComboBox.getValue().concat(viewVaccineIDTextField.getText());
        for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
            if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                    if (!vaccinationBooth.getVaccinationAppointments().isEmpty()) {
                        for (VaccinationAppointment vaccinationAppointment : vaccinationBooth.getVaccinationAppointments()) {
                            if (vaccinationAppointment.getVaccinationType().concat(vaccinationAppointment.getVaccinationIdentifier()).equalsIgnoreCase(chosenVaccine)) {
                                allAppointments.add(vaccinationAppointment);
                            }
                        }
                    }
                }
            }
        }
        appointmentsTableView.setItems(allAppointments);
        if (allAppointments.isEmpty()) {
            Alerts.genericWarning("Nothing found. Vaccination details incorrect!");
        }
    }

    /**
     * Populates the table with all appointments and records associated with the entered PPSN on button press
     */
    @FXML
    public void onActionViewPatient() {
        String searchppsn = viewPatientTextField.getText();
        if (!vacSys.getPatients().isEmpty()) {
            allAppointments.clear();
            for (Patient patient : vacSys.getPatients()) {
                if (patient.getPatientIdentifier().equalsIgnoreCase(searchppsn)) {
                    for (VaccinationRecord vaccinationRecord : patient.getVaccinationRecords()) {
                        allAppointments.add(vaccinationRecord);
                    }
                } else {
                    Alerts.genericWarning("No Patient with PPSN " + searchppsn + " found!");
                    return;
                }
            }
            if (!vacSys.getVaccinationCentres().isEmpty()) {
                for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                    if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                        for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                            if (!vaccinationBooth.getVaccinationAppointments().isEmpty()) {
                                for (VaccinationAppointment vaccinationAppointment : vaccinationBooth.getVaccinationAppointments()) {
                                    if (vaccinationAppointment.getPatientIdentifier().equalsIgnoreCase(searchppsn)) {
                                        allAppointments.add(vaccinationAppointment);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Alerts.genericWarning("No Patients are even in the system!");
            return;
        }
        if (allAppointments.isEmpty()) {
            Alerts.genericWarning("Nothing found!");
        }
    }

    /**
     * Removes the selected Appointment from the system on button press.
     */
    @FXML
    private void removeAppointment() {
        allAppointments.remove(selectedAppointment);
        for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
            if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                    if (vaccinationBooth.getVaccinationAppointments().contains(selectedAppointment)) {
                        vaccinationBooth.getVaccinationAppointments().remove(selectedAppointment);
                    }
                }
            }
        }
        selectedAppointment = null;
        appointmentsTableView.getSelectionModel().select(null);
    }

    /**
     * Completes an appointment by removing the selected Appointment from the system and assigning a
     * duplicate vaccination record to the appropriate patient on button press
     */
    @FXML
    public void onActionComplete() {
        if (selectedAppointment != null) {
            if (!(selectedAppointment instanceof VaccinationRecord)) { //check the user isn't trying to complete a record
                VaccinationRecord vaccinationRecord = new VaccinationRecord(selectedAppointment.getCentreBooth(),selectedAppointment.getDate(), selectedAppointment.getTime(), selectedAppointment.getVaccinationType(), selectedAppointment.getVaccinationIdentifier(), selectedAppointment.getVaccinatorDetails(), selectedAppointment.getPatientIdentifier());
                for (Patient patient : vacSys.getPatients()) {
                    if (patient.getPatientIdentifier().equalsIgnoreCase(selectedAppointment.getPatientIdentifier())) {
                        patient.getVaccinationRecords().add(vaccinationRecord);
                    }
                }
                removeAppointment();
            } else {
                Alerts.genericWarning("Can't complete what is already completed...");
            }
        } else {
            Alerts.genericWarning("No Appointment selected!");
        }
    }

    /**
     * Cancels an appointment by removing the selected Appointment from the system on button press
     */
    @FXML
    public void onActionCancel() {
        if(selectedAppointment != null) {
            if (!(selectedAppointment instanceof VaccinationRecord)) {
                removeAppointment();
            } else {
                Alerts.genericWarning("Can't cancel what is already completed...");
            }
        } else {
            Alerts.genericWarning("No Appointment selected!");
        }
    }

    //---------------------------------------------------------------//
    //Tab 2                                                          //
    //---------------------------------------------------------------//
    /**
     * Gathers entered data from the textfields, datepicker and comboboxes
     */
    public void enteredInfo() {
        booth = boothComboBox.getValue();
        if (!vacSys.getVaccinationCentres().isEmpty()) {
            for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                    for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                        if ((vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier()).equalsIgnoreCase(booth)) {
                            chosenBooth = vaccinationBooth; //get the booth object from the chosen string in the combobox
                        }
                    }
                }
            }
        }
        date = datePicker.getValue();
        time = timeField.getText();
        vaccineType = vaccineTypeComboBox.getValue();
        vaccineID = vaccineIDTextField.getText();
        vaccinator = vaccinatorComboBox.getValue();
        ppsn = ppsnTextField.getText();
    }

    /**
     * Gathers data from the selected Appointment and updates the textfield, datepicker and comboboxes with that data
     */
    public void updateFields() {
        boothComboBox.setValue(selectedAppointment.getCentreBooth());
        datePicker.setValue(selectedAppointment.getDate());
        timeField.setText(selectedAppointment.getTime());
        vaccineTypeComboBox.setValue(selectedAppointment.getVaccinationType());
        vaccineIDTextField.setText(selectedAppointment.getVaccinationIdentifier());
        vaccinatorComboBox.setValue(selectedAppointment.getVaccinatorDetails());
        ppsnTextField.setText(selectedAppointment.getPatientIdentifier());
    }

    /**
     * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
     * Display alerts for any incorrect information.
     * if all information is valid, returns true.
     * @return true if valid, else returns false
     */
    private boolean validateInformation() {
        enteredInfo();
        if (boothComboBox.getValue() == null || date == null ||
                time.isBlank() || vaccineType.isBlank() || vaccineID.isBlank() || vaccinator.isBlank() || ppsn.isBlank()) {
            Alerts.genericWarning( "Please fill out all fields");
            return false;
        } else if (!vacSys.getPatients().isEmpty()) { //check the ppsn has an associated patient
            for (Patient patient : vacSys.getPatients()) {
                if (patient.getPatientIdentifier().equalsIgnoreCase(ppsn)) {
                    return true;
                }
            }
            Alerts.genericWarning("No Patient with PPSN "+ ppsn +" found!");
            return false;
        }
        return true;
    }

    /**
     * Adds an Appointment to the system on button press, but only after successful validation.
     */
    @FXML
    public void onActionAdd() {
        if (validateInformation()) {
            enteredInfo();
            VaccinationAppointment vaccinationAppointment = new VaccinationAppointment(booth, date, time, vaccineType, vaccineID, vaccinator, ppsn);
            chosenBooth.getVaccinationAppointments().add(vaccinationAppointment);
            allAppointments.add(vaccinationAppointment);
            onActionViewAll();
        }
    }

    /**
     * Updates the selected Appointment's data on button press, but only after successful validation.
     * If a different booth is chosen it will update the system accordingly.
     */
    @FXML
    public void onActionUpdate() {
        if(selectedAppointment != null) {
            if (selectedAppointment instanceof VaccinationRecord) {
                Alerts.genericWarning("Can't update what is already completed...");
                return;
            }
            if (validateInformation()) {
                enteredInfo();
                if (!vacSys.getVaccinationCentres().isEmpty()) {
                    for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                        if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                            for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                                if ((vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier()).equalsIgnoreCase(selectedAppointment.getCentreBooth())) {
                                    chosenBooth = vaccinationBooth; //chosen booth = the existing booth as opposed to the entered booth
                                }
                            }
                        }
                    }
                }
                selectedAppointment.setDate(date);
                selectedAppointment.setTime(time);
                selectedAppointment.setVaccinationType(vaccineType);
                selectedAppointment.setVaccinationIdentifier(vaccineID);
                selectedAppointment.setVaccinatorDetails(vaccinator);
                selectedAppointment.setPatientIdentifier(ppsn);
                if (!selectedAppointment.getCentreBooth().equalsIgnoreCase(booth)) {
                    for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                        if (!vaccinationCentre.getVaccinationBooths().isEmpty()) {
                            for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
                                if ((vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier()).equalsIgnoreCase(booth)) {
                                    selectedAppointment.setCentreBooth(booth);
                                    vaccinationBooth.getVaccinationAppointments().add(selectedAppointment); //add to new booth
                                    chosenBooth.getVaccinationAppointments().remove(selectedAppointment); //take away from old booth
                                }
                            }
                        }
                    }
                }
                onActionViewAll(); //reset the table
                selectedAppointment = null;
                appointmentsTableView.getSelectionModel().select(null);
            }
        }  else {
            Alerts.genericWarning("No Appointment selected!");
        }
    }

    /**
     * Adds an Appointment to the system using only the given PPSN on button press.
     * Which booth the appointment is added to depends on address and accessibility
     */
    @FXML
    public void onActionSmartAdd() {
        LocalDate vaxtime = LocalDate.now().plusDays(28);
        String smartAddPPSN = smartAddTextField.getText();
        if (!vacSys.getPatients().isEmpty()) {
            for (Patient patient : vacSys.getPatients()) {
                if (patient.getPatientIdentifier().equalsIgnoreCase(smartAddPPSN)) {
                    if (!vacSys.getVaccinationCentres().isEmpty()) {
                        for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                            if (vaccinationCentre.getAddress().toLowerCase().contains(patient.getAddress().toLowerCase())) { //check if any vaccination centre contains the patient address, very simple logic
                                if (!vaccinationCentre.getVaccinationBooths().isEmpty() && smartAddHelper(vaxtime, smartAddPPSN, patient, vaccinationCentre))
                                    return;
                            } else {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("No Vaccination Centre with address " + patient.getAddress() + " found. Book appointment anyway? "); //if no address similarities check if they want to proceed anyway
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.orElse(null) == ButtonType.OK && smartAddHelper(vaxtime, smartAddPPSN, patient, vaccinationCentre))
                                    return;
                            }
                        }
                    }
                } else
                Alerts.genericWarning("No Patient with PPSN "+ smartAddPPSN +" found!");
            }
        } else {
            Alerts.genericWarning("No Patients are even in the system!");
        }
    }

    /**
     * Method used in smart add to add appointments to the system.
     */
    @FXML
    private boolean smartAddHelper(LocalDate vaxtime, String smartAddPPSN, Patient patient, VaccinationCentre vaccinationCentre) {
        for (VaccinationBooth vaccinationBooth : vaccinationCentre.getVaccinationBooths()) {
            if (vaccinationBooth.getAccessibility().equalsIgnoreCase("Wheelchair Friendly") && patient.getAccessibility().equalsIgnoreCase("Wheelchair-Bound")) {
                chosenBooth = vaccinationBooth;
                booth = vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier();
                VaccinationAppointment vaccinationAppointment = new VaccinationAppointment(booth, vaxtime, "14:00", "AstraZeneca", "123A", "Sol Badguy", smartAddPPSN);
                chosenBooth.getVaccinationAppointments().add(vaccinationAppointment);
                allAppointments.add(vaccinationAppointment);
                Alerts.genericInfo(patient.getName() + " successfully assigned a Wheelchair friendly appointment");
                onActionViewAll();
                return true;
            }
            if (patient.getAccessibility().contains("N/A")) {
                chosenBooth = vaccinationBooth;
                booth = vaccinationCentre.getName() + " - " + vaccinationBooth.getIdentifier();
                VaccinationAppointment vaccinationAppointment = new VaccinationAppointment(booth, vaxtime, "15:00", "AstraZeneca", "123A", "Sol Badguy", smartAddPPSN);
                chosenBooth.getVaccinationAppointments().add(vaccinationAppointment);
                allAppointments.add(vaccinationAppointment);
                Alerts.genericInfo(patient.getName() + " successfully assigned an appointment");
                onActionViewAll();
                return true;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("No wheelchair friendly booth exists. Book appointment anyway?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            chosenBooth = vaccinationCentre.getVaccinationBooths().get(0);
            booth = vaccinationCentre.getName() + " - " + chosenBooth.getIdentifier();
            VaccinationAppointment vaccinationAppointment = new VaccinationAppointment(booth, vaxtime, "16:00", "AstraZeneca", "123A", "Sol Badguy", smartAddPPSN);
            chosenBooth.getVaccinationAppointments().add(vaccinationAppointment);
            allAppointments.add(vaccinationAppointment);
            Alerts.genericInfo(patient.getName() + " successfully assigned an appointment");
            onActionViewAll();
            return true;
    }
        return false;
    }

    //---------------------------------------------------------------//
    //Random                                                         //
    //---------------------------------------------------------------//
    /**
     * When the table is clicked checks if a Centre is selected and updates fields accordingly if so.
     */
    @FXML
    public void onTableClicked() {
        selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            updateFields();
        }
    }

    /**
     * Switches scenes to MainMenu.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionHome(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitch(actionEvent, "MainMenu.fxml");
    }
}