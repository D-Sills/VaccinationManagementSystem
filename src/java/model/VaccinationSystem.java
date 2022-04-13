package model;

import Utilities.CustomList;

/**
 * @author Darren Sills
 * Vaccination system class used to store all data in the system, containing both the list of centres and patients.
 */
public class VaccinationSystem {
    public CustomList<VaccinationCentre> vaccinationCentres;
    public CustomList<Patient> patients;

    /**
     * Constructor for objects of class Vaccination System
     */
    public VaccinationSystem() {
        vaccinationCentres = new CustomList<>();
        patients = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the list of Vaccination Centres in the System
     */
    public CustomList<VaccinationCentre> getVaccinationCentres() {
        return vaccinationCentres;
    }

    /**
     * Returns the list of Patients in the System
     */
    public CustomList<Patient> getPatients() {
        return patients;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the list of Vaccination Centres in the System to the value passed
     * @param vaccinationCentres The list of Vaccination Centres in the System
     */
    public void setVaccinationCentres(CustomList<VaccinationCentre> vaccinationCentres) {
        this.vaccinationCentres = vaccinationCentres;
    }

    /**
     * Updates the list of Patients in the System to the value passed
     * @param patients The list of Patients in the System
     */
    public void setPatients(CustomList<Patient> patients) {
        this.patients = patients;
    }
}
