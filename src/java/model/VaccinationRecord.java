package model;

import java.time.LocalDate;

/**
 * @author Darren Sills
 * Vaccination record class that is a subclass of appointment with no new methods as both store the same data.
 */
public class VaccinationRecord extends VaccinationAppointment{
    /**
     * Constructor for objects of class Vaccination Record
     * @param date Appointment date
     * @param time Appointment time
     * @param vaccinationType Appointment vaccine
     * @param vaccinationIdentifier Appointment vaccine identifier
     * @param vaccinatorDetails Appointment vaccinator name
     * @param patientIdentifier Patient's PPSN
     */
    public VaccinationRecord(String centreBooth,LocalDate date, String time, String vaccinationType, String vaccinationIdentifier, String vaccinatorDetails, String patientIdentifier) {
        super(centreBooth, date, time, vaccinationType, vaccinationIdentifier, vaccinatorDetails, patientIdentifier);
    }
}
