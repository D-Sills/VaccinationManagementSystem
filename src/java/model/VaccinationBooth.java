package model;

import Utilities.CustomList;

/**
 * @author Darren Sills
 * Vaccination booth class used to store all data related to each booth, as well as the list of appointments in each booth.
 */
public class VaccinationBooth {
    private String identifier;
    private int floorLevel;
    private String accessibility;
    public CustomList<VaccinationAppointment> vaccinationAppointments;

    /**
     * Constructor for objects of class Vaccination Booth
     * @param identifier Booth identifier
     * @param floorLevel Booth floor level
     * @param accessibility Booth accessibility information
     */
    public VaccinationBooth(String identifier, int floorLevel, String accessibility) {
        this.identifier = identifier;
        this.floorLevel = floorLevel;
        this.accessibility = accessibility;
        vaccinationAppointments = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the identifier of the Booth
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the floor level of the Booth
     */
    public int getFloorLevel() {
        return floorLevel;
    }

    /**
     * Returns the date of the Appointment
     */
    public String getAccessibility() {
        return accessibility;
    }

    /**
     * Returns the list of Vaccination Appointments for the Booth
     */
    public CustomList<VaccinationAppointment> getVaccinationAppointments() {
        return vaccinationAppointments;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Booth identifier to the value passed
     * @param identifier Booth identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Updates the Booth floor level to the value passed
     * @param floorLevel Booth floor level
     */
    public void setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
    }

    /**
     * Updates the Booth's accessibility information to the value passed
     * @param accessibility Booth accessibility information
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * Updates the list of Vaccination Appointments for the Booth to the value passed
     * @param vaccinationAppointments The list of Vaccination Appointments for the Booth
     */
    public void setVaccinationAppointments(CustomList<VaccinationAppointment> vaccinationAppointments) {
        this.vaccinationAppointments = vaccinationAppointments;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Booth
     */
    @Override
    public String toString() {
        return  getIdentifier();
    }
}
