package model;

import java.time.LocalDate;

/**
 * @author Darren Sills
 * Vaccination appointment class used to store all data related to each appointment.
 */
public class VaccinationAppointment {
    private String centreBooth; //additional data used to increase readability of the data
    private LocalDate date;
    private String time;
    private String vaccinationType;
    private String vaccinationIdentifier;
    private String vaccinatorDetails;
    private String patientIdentifier;

    /**
     * Constructor for objects of class Vaccination Appointment
     * @param centreBooth Appointment centre and booth
     * @param date Appointment date
     * @param time Appointment time
     * @param vaccinationType Appointment vaccine
     * @param vaccinationIdentifier Appointment vaccine identifier
     * @param vaccinatorDetails Appointment vaccinator name
     * @param patientIdentifier Patient's PPSN
     */
    public VaccinationAppointment(String centreBooth, LocalDate date,  String time, String vaccinationType, String vaccinationIdentifier, String vaccinatorDetails, String patientIdentifier) {
        this.centreBooth = centreBooth;
        this.date = date;
        this.time = time;
        this.vaccinationType = vaccinationType;
        this.vaccinationIdentifier = vaccinationIdentifier;
        this.vaccinatorDetails = vaccinatorDetails;
        this.patientIdentifier = patientIdentifier;
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the Appointment centre and booth
     */
    public String getCentreBooth() {
        return centreBooth;
    }

    /**
     * Returns the date of the Appointment
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the time of the Appointment
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns the vaccine type used in the Appointment
     */
    public String getVaccinationType() {
        return vaccinationType;
    }

    /**
     * Returns the vaccination identifier of the vaccine used in the Appointment
     */
    public String getVaccinationIdentifier() {
        return vaccinationIdentifier;
    }

    /**
     * Returns the details of the Vaccinator for the Appointment
     */
    public String getVaccinatorDetails() {
        return vaccinatorDetails;
    }

    /**
     * Returns the PPSN of the Appointment's Patient
     */
    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Appointment centre and booth
     * @param centreBooth Appointment centre and booth
     */
    public void setCentreBooth(String centreBooth) {
        this.centreBooth = centreBooth;
    }

    /**
     * Updates the Appointment date to the value passed
     * @param date Appointment date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Updates the Appointment time to the value passed
     * @param time Appointment time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Updates the Appointment vaccine to the value passed
     * @param vaccinationType Appointment vaccine
     */
    public void setVaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    /**
     * Updates the Appointment vaccine id to the value passed
     * @param vaccinationIdentifier Appointment vaccine identifier
     */
    public void setVaccinationIdentifier(String vaccinationIdentifier) {
        this.vaccinationIdentifier = vaccinationIdentifier;
    }

    /**
     * Updates the Appointment vaccinator details to the value passed
     * @param vaccinatorDetails Appointment vaccinator name
     */
    public void setVaccinatorDetails(String vaccinatorDetails) {
        this.vaccinatorDetails = vaccinatorDetails;
    }

    /**
     * Updates the Appointment patient's PPSN to the value passed
     * @param patientIdentifier Patient's PPSN
     */
    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Vaccination Centre
     */
    @Override
    public String toString() {
        return  getCentreBooth() + " " + getDate();
    }
}
