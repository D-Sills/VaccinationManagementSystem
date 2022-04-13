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
import model.VaccinationCentre;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.darrensills.Main.vacSys;

/**
 * @author Darren Sills
 * Vaccination Centre controller class which shows the Centres.Fxml scene.
 * Responsible for displaying and manipulating Vaccination Centre data.
 */
public class CentresController implements Initializable {
        private String name;
        private String address;
        private String eircode;
        private int parking;
        private static VaccinationCentre selectedCentre;
        private final ObservableList<VaccinationCentre> allCentres = FXCollections.observableArrayList();

        @FXML
        private TableView<VaccinationCentre> centresTableView;

        @FXML
        private TableColumn<VaccinationCentre, String> colName;

        @FXML
        private TableColumn<VaccinationCentre, String> colAddress;

        @FXML
        private TableColumn<VaccinationCentre, String> colEircode;

        @FXML
        private TableColumn<VaccinationCentre, Integer> colParking;

        @FXML
        private TextField nameTextField;

        @FXML
        private TextArea addressTextArea;

        @FXML
        private TextField eircodeTextField;

        @FXML
        private Spinner<Integer> parkingSpinner;

        /**
         * Gathers the Booth's data from the selected Centre and populates the table fields
         * @param url used to resolve relative paths for root object
         * @param resourceBundle localizes the root object
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                if (!vacSys.getVaccinationCentres().isEmpty()) {
                        for (VaccinationCentre vaccinationCentre : vacSys.getVaccinationCentres()) {
                                allCentres.add(vaccinationCentre);
                        }
                }
                colName.setCellValueFactory(new PropertyValueFactory<>("name"));
                colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                colEircode.setCellValueFactory(new PropertyValueFactory<>("eircode"));
                colParking.setCellValueFactory(new PropertyValueFactory<>("numParkingSpaces"));
                centresTableView.setItems(allCentres);
        }

        //---------------------------------------------------------------//
        //Methods                                                        //
        //---------------------------------------------------------------//

        /**
         * When the table is clicked checks if a Centre is selected and updates fields accordingly if so.
         */
        @FXML
        public void onTableClicked() {
                selectedCentre = centresTableView.getSelectionModel().getSelectedItem();
                if (selectedCentre != null) {
                        updateFields();
                }
        }

        /**
         * Gathers entered data from the textfields and spinner
         */
        public void enteredInfo() {
                name = nameTextField.getText();
                address = addressTextArea.getText();
                eircode = eircodeTextField.getText();
                parking = parkingSpinner.getValue();
        }

        /**
         * Gathers data from the selected Centre and updates the textfield and spinner with that data
         */
        public void updateFields() {
                nameTextField.setText(selectedCentre.getName());
                addressTextArea.setText(selectedCentre.getAddress());
                eircodeTextField.setText(selectedCentre.getEircode());
                parkingSpinner.getValueFactory().setValue(selectedCentre.getNumParkingSpaces());
        }

        /**
         * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
         * Display alerts for any incorrect information.
         * if all information is valid, returns true.
         *
         * @return true if valid, else returns false
         */
        private boolean validateInformation() {
                enteredInfo();
                if (name.isBlank() || address.isBlank() || eircode.isBlank()) {
                        Alerts.genericWarning("Please fill out all fields");
                        return false;
                } else if (!Validation.validEircode(eircode)) {
                        Alerts.genericWarning("Invalid Eircode");
                        return false;
                }
                return true;
        }

        /**
         * Adds a Centre to the system on button press, but only after successful validation.
         */
        @FXML
        public void onActionAdd() {
                if (validateInformation()) {
                        enteredInfo();
                        VaccinationCentre vaccinationCentre = new VaccinationCentre(name, address, eircode, parking);
                        vacSys.getVaccinationCentres().add(vaccinationCentre);
                        allCentres.add(vaccinationCentre);
                        centresTableView.getSelectionModel().select(null);
                }
        }

        /**
         * Updates the selected Centre's data on button press, but only after successful validation.
         */
        @FXML
        public void onActionUpdate() {
                if (selectedCentre != null) {
                        if (validateInformation()) {
                                enteredInfo();
                                selectedCentre.setName(name);
                                selectedCentre.setAddress(address);
                                selectedCentre.setEircode(eircode);
                                selectedCentre.setNumParkingSpaces(parking);
                                centresTableView.refresh();
                                selectedCentre = null;
                                centresTableView.getSelectionModel().select(null);
                        }
                } else {
                        Alerts.genericWarning("No Vaccination Centre selected!");
                }
        }

        /**
         * Removes the selected Centre from the system on button press.
         */
        @FXML
        public void onActionDelete() {
                if (selectedCentre != null) {
                        selectedCentre.getVaccinationBooths().clear();
                        vacSys.getVaccinationCentres().remove(selectedCentre);
                        allCentres.remove(selectedCentre);
                        selectedCentre = null;
                        centresTableView.getSelectionModel().select(null);
                } else {
                        Alerts.genericWarning("No Vaccination Centre selected!");
                }
        }

        /**
         * Switches scenes to MainMenu.fxml on button press
         *
         * @param actionEvent handler that reacts to the javafx root event
         */
        @FXML
        public void onActionHome(ActionEvent actionEvent) throws IOException {
                SceneSwitcher.SceneSwitch(actionEvent, "MainMenu.fxml");
        }

        /**
         * Switches scenes to Booths.fxml on button press
         *
         * @param actionEvent handler that reacts to the javafx root event
         */
        @FXML
        public void onActionBooth(ActionEvent actionEvent) throws IOException {
                if (selectedCentre != null) {
                        SceneSwitcher.SceneSwitchNewWindow(actionEvent, "Booths.fxml", "Vaccination Booths");
                        centresTableView.getSelectionModel().select(null);
                } else {
                        Alerts.genericWarning("No Vaccination Centre selected!");
                }
        }

        public static VaccinationCentre getSelectedCentre() {
                return selectedCentre;
        }
}
