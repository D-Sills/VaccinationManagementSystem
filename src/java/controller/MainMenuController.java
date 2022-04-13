package controller;

import Utilities.Alerts;
import Utilities.CustomList;
import Utilities.SceneSwitcher;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Patient;
import model.VaccinationCentre;

import java.io.*;
import java.util.Optional;

import static com.darrensills.Main.vacSys;

/**
 * @author Darren Sills
 * MainMenu class which shows the MainMenu.Fxml screen.
 * Responsible for swapping between the main scenes and also for saving and loading data via xstream.
 */
public class MainMenuController {
    /**
     * Switches scenes to Appointments.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionAppointments(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitch(actionEvent, "Appointments.fxml");
    }

    /**
     * Switches scenes to Centres.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionCentres(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitch(actionEvent, "Centres.fxml");
    }

    /**
     * Switches scenes to Patients.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionPatients(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitch(actionEvent, "Patients.fxml");
    }

    //---------------------------------------------------------------//
    //Persistence                                                    //
    //---------------------------------------------------------------//
    /**
     * Uses the XStream library to save the system data to a .xml file in the project
     */
    @FXML
    public void onActionSave() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation popup
        alert.setHeaderText("Are you sure you want to save all data to VaccinationSystem.xml?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.orElse(null ) == ButtonType.OK) { //if ok is selected continue, else do nothing
            XStream xstream = new XStream(new DomDriver()); //initialise the xstream object
            ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("VaccinationSystem.xml")); //initialise an objectoutputsteam to a specific file
            out.writeObject(vacSys.getVaccinationCentres()); //write out the objects you want saved
            out.writeObject(vacSys.getPatients());
            out.close();

            Alerts.genericInfo("Saved to VaccinationSystem.xml successful!");
        }
    }

    /**
     * Uses the XStream library to load the system data from a .xml file in the project
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void onActionLoad() throws Exception {
        File xml = new File("VaccinationSystem.xml");
        if(xml.isFile()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to load all data from VaccinationSystem.xml?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(null ) == ButtonType.OK) {
                XStream xstream = new XStream(new DomDriver());
                ObjectInputStream is = xstream.createObjectInputStream(new FileReader("VaccinationSystem.xml"));  //initialise an objectoutputsteam from a specific file
                vacSys.setVaccinationCentres((CustomList<VaccinationCentre>) is.readObject()); //tell it what object to assign values to
                vacSys.setPatients((CustomList<Patient>) is.readObject());
                is.close();

                Alerts.genericInfo("Loaded from VaccinationSystem.xml successful!");
            }
        } else {
            Alerts.genericInfo("Please save some data first!");
        }

    }

    /**
     * Completely resets all data in the System
     */
    @FXML
    public void onActionReset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to reset all data in the system?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.orElse(null ) == ButtonType.OK) {
            vacSys.getPatients().clear(); //clear the two main lists to wipe all data
            vacSys.getVaccinationCentres().clear();

            Alerts.genericInfo("System reset successful!");
        }
    }
}
