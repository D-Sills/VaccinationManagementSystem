package model;

import Utilities.CustomList;

/**
 * @author Darren Sills
 * Vaccination centre class used to store all data related to each centre, as well as the list of booths in each centre.
 */
public class VaccinationCentre {
    private String name;
    private String address;
    private String eircode;
    private int numParkingSpaces;
    public CustomList<VaccinationBooth> vaccinationBooths;

    /**
     * Constructor for objects of class Vaccination Centre
     * @param name Vaccination Centre name
     * @param address Vaccination Centre address
     * @param eircode Vaccination Centre eircode
     * @param numParkingSpaces number of parking spaces in the Vaccination Centre
     */
    public VaccinationCentre(String name, String address, String eircode, int numParkingSpaces) {
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        this.numParkingSpaces = numParkingSpaces;
        vaccinationBooths = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the name of the Centre
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the address of the Centre
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the eircode of the Centre
     */
    public String getEircode() {
        return eircode;
    }

    /**
     * Returns the number of parking spaces at the Centre
     */
    public int getNumParkingSpaces() {
        return numParkingSpaces;
    }

    /**
     * Returns the list of Vaccination Booths in the Centre
     */
    public CustomList<VaccinationBooth> getVaccinationBooths() {
        return vaccinationBooths;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Vaccination Centre name to the value passed
     * @param name Vaccination Centre name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the Vaccination Centre address to the value passed
     * @param address Vaccination Centre address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Updates the Vaccination Centre eircode to the value passed
     * @param eircode Vaccination Centre eircode
     */
    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    /**
     * Updates the number of parking spaces in tge Centre to the value passed
     * @param numParkingSpaces number of parking spaces in the Vaccination Centre
     */
    public void setNumParkingSpaces(int numParkingSpaces) {
        this.numParkingSpaces = numParkingSpaces;
    }

    /**
     * Updates the list of Vaccination Booths in the Centre to the value passed
     * @param vaccinationBooths The list of Vaccination Booths in the Centre
     */
    public void setVaccinationBooths(CustomList<VaccinationBooth> vaccinationBooths) {
        this.vaccinationBooths = vaccinationBooths;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Vaccination Centre
     */
    @Override
    public String toString() {
        return  getName();
    }
}
