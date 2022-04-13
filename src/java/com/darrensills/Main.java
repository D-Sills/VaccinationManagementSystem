package com.darrensills;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.VaccinationSystem;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Darren Sills
 * Main class is the launcher for the JavaFX application and also initializes the Vaccination System.
 */
public class Main extends Application {
    public final static VaccinationSystem vacSys = new VaccinationSystem(); //Creates the singular vacation system, static so that the list of centres and patient can be called from anywhere

    /**
     * Starts the JavaFX scene; Main Menu.fxml
     * @param stage stage for the JavaFX application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1152, 700);
        stage.setTitle("Vaccination Booking Centre");
        stage.setScene(scene);
        stage.setResizable(false); //application isn't responsive so non-resizable
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/healthcare.png")))); //set the icon
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
