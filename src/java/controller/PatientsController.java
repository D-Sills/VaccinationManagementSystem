package controller;

import Utilities.Alerts;
import Utilities.SceneSwitcher;
import Utilities.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.darrensills.Main.vacSys;

/**
 * @author Darren Sills
 * Patient controller class which shows the Patients.Fxml scene.
 * Responsible for displaying and manipulating Patient data.
 */
public class PatientsController implements Initializable {
    private static Patient selectedPatient;
    private String name;
    private String address;
    private String PPSN;
    private LocalDate DOB;
    private String email;
    private String phone;

    private final ObservableList<Patient> allPatients = FXCollections.observableArrayList();

    @FXML
    private TableView<Patient> patientsTableView;

    @FXML
    private TableColumn<Patient, String> colName;

    @FXML
    private TableColumn<Patient, String> colAddress;

    @FXML
    private TableColumn<Patient, String> colPPSN;

    @FXML
    private TableColumn<Patient, LocalDate> colDOB;

    @FXML
    private TableColumn<Patient, String> colPhone;

    @FXML
    private TableColumn<Patient, String> colEmail;

    @FXML
    private TableColumn<Patient, String> colWheelchair;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea addressTextArea;

    @FXML
    private TextField ppsnTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private CheckBox wheelchairCheckBox;

    /**
     * Gathers the Patient's data from the system and populates the table fields
     * @param url used to resolve relative paths for root object
     * @param resourceBundle localizes the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(vacSys.getPatients().isEmpty())) {
            for (Patient patient : vacSys.getPatients()) {
                allPatients.add(patient);
            }
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPPSN.setCellValueFactory(new PropertyValueFactory<>("patientIdentifier"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colWheelchair.setCellValueFactory(new PropertyValueFactory<>("accessibility"));
        patientsTableView.setItems(allPatients);
    }

    //---------------------------------------------------------------//
    //Methods                                                        //
    //---------------------------------------------------------------//
    /**
     * When the table is clicked checks if a Patient is selected and updates fields accordingly if so.
     */
    @FXML
    public void onTableClicked() {
        selectedPatient = patientsTableView.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            updateFields();
        }
    }

    /**
     * Gathers entered data from the textfields and datepicker
     */
    public void enteredInfo() {
        name = nameTextField.getText();
        address = addressTextArea.getText().strip();
        PPSN = ppsnTextField.getText();
        email = emailTextField.getText();
        phone = phoneTextField.getText().trim();
        DOB = datePicker.getValue();
    }

    /**
     * Gathers data from the selected Patient and updates the textfield and datepicker with that data
     */
    public void updateFields() {
        nameTextField.setText(selectedPatient.getName());
        addressTextArea.setText(selectedPatient.getAddress());
        ppsnTextField.setText(selectedPatient.getPatientIdentifier());
        emailTextField.setText(selectedPatient.getEmail());
        phoneTextField.setText(selectedPatient.getPhone());
        datePicker.setValue(selectedPatient.getDateOfBirth());
        wheelchairCheckBox.setSelected(selectedPatient.getAccessibility().equals("Wheelchair-Bound")); //checkbox is set depending on the accessibility string
    }

    /**
     * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
     * Display alerts for any incorrect information.
     * if all information is valid, returns true.
     * @return true if valid, else returns false
     */
    private boolean validateInformation() {
        enteredInfo();
        if (name.isBlank() || phone.isBlank() || address.isBlank() || PPSN.isBlank() || email.isBlank() || DOB == null) {
            Alerts.genericWarning("Please fill out all fields");
            return false;
        } else if (!Validation.validPPS(PPSN)) {
            Alerts.genericWarning("Invalid PPSN, must be 7 numbers followed by 2 letters");
            return false;
        } else if (!Validation.validPhone(phone)) {
            Alerts.genericWarning("Invalid phone number, must be 10 numbers long");
            return false;
        } else if (!Validation.validEmail(email)) {
            Alerts.genericWarning("Invalid email, must contain a @ and .");
            return false;
        }
        return true;
    }

    /**
     * Adds a Patient to the system on button press, but only after successful validation.
     */
    @FXML
    public void onActionAdd() {
        if (validateInformation()) {
            enteredInfo();
            String access = wheelchairCheckBox.isSelected() ? "Wheelchair-Bound" : "N/A"; //conditional operator to check accessibility
            for (Patient patient : vacSys.getPatients()) { //check if two patients have the same identifier
                if (PPSN.equalsIgnoreCase(patient.getPatientIdentifier())) {
                    Alerts.genericWarning("No two Patients can have the same identifier!");
                    return;
                }
            }
            Patient patient = new Patient(name, PPSN, DOB, address, phone, email, access);
            vacSys.getPatients().add(patient);
            allPatients.add(patient);
            selectedPatient = null;
            patientsTableView.getSelectionModel().select(null);
        }
    }

    /**
     * Updates the selected Patient's data on button press, but only after successful validation.
     */
    @FXML
    public void onActionUpdate() {
        if(selectedPatient != null) {
            if (validateInformation()) {
                enteredInfo();
                if (!PPSN.equalsIgnoreCase(selectedPatient.getPatientIdentifier())) {
                    for (Patient patient : vacSys.getPatients()) {
                        if (PPSN.equalsIgnoreCase(patient.getPatientIdentifier())) {
                            Alerts.genericWarning("No two Patients can have the same identifier!");
                            return;
                        }
                    }
                }
                selectedPatient.setName(name);
                selectedPatient.setAddress(address);
                selectedPatient.setPatientIdentifier(PPSN);
                selectedPatient.setEmail(email);
                selectedPatient.setPhone(phone);
                selectedPatient.setAccessibility(wheelchairCheckBox.isSelected() ? "Wheelchair-Bound" : "N/A");
                selectedPatient.setDateOfBirth(DOB);
                patientsTableView.refresh();
                selectedPatient = null;
                patientsTableView.getSelectionModel().select(null);
            }
        } else {
            Alerts.genericWarning("No Patient selected!");
        }
    }

    /**
     * Removes the selected Patient from the system on button press.
     */
    @FXML
    public void onActionDelete() {
        if(selectedPatient != null) {
            selectedPatient.getVaccinationRecords().clear();
            vacSys.getPatients().remove(selectedPatient);
            allPatients.remove(selectedPatient);
            selectedPatient = null;
            patientsTableView.getSelectionModel().select(null);
        } else {
            Alerts.genericWarning("No Patient selected!");
        }
    }

    /**
     * Searches for the Patient with the entered PPSN and displays their vaccination records
     */
    public void onActionSearch(ActionEvent actionEvent) throws IOException {
        TextInputDialog search = new TextInputDialog();
        TextField inputField = search.getEditor();
        search.showAndWait();
        String searchppsn = inputField.getText();
        if (!vacSys.getPatients().isEmpty()) {
            for (Patient patient : vacSys.getPatients()) {
                if (patient.getPatientIdentifier().equalsIgnoreCase(searchppsn)) {
                    selectedPatient = patient;
                    SceneSwitcher.SceneSwitchNewWindow(actionEvent, "PatientRecords.fxml", "Patient Records");
                    selectedPatient = null;
                    patientsTableView.getSelectionModel().select(null);
                    return;
                }
            }
        }
        Alerts.genericWarning("No Patient with PPSN " + searchppsn + " found!");
    }

    /**
     * Switches scenes to MainMenu.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionHome(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitch(actionEvent, "MainMenu.fxml");
        patientsTableView.getSelectionModel().select(null);
    }

    /**
     * Switches scenes to Records.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionRecords(ActionEvent actionEvent) throws IOException {
        if (selectedPatient != null) {
            SceneSwitcher.SceneSwitchNewWindow(actionEvent, "PatientRecords.fxml", "Patient Records");
            selectedPatient = null;
            patientsTableView.getSelectionModel().select(null);
        } else {
            Alerts.genericWarning("No Patient selected!");
        }
    }

    public static Patient getSelectedPatient() {
        return selectedPatient;
    }
}
