package Utilities;

/**
 * @author Darren Sills
 * Class containing a number of methods used for validation on entered data
 */
public class Validation {
    /**
     * Checks if the given string is 7 numbers followed by 2 letters
     * @param ppsn generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validPPS(String ppsn) {
        return (ppsn.matches("[0-9]{7}[a-zA-Z]{2}")); //7 0-9 numbers followed by 2 a-z characters
    }

    /**
     * Checks if the given string contains an '@' and '.'
     * @param email generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validEmail(String email) {
        return (email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"));
    }

    /**
     * Checks if the given string contains a valid phone number xxx xxx xxxx
     * @param phone generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validPhone(String phone) {
        return (phone.matches("^(\\d{3}[- .]?){2}\\d{4}$"));
    }

    /**
     * Checks if the given string contains a valid eircode
     * @param eircode generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validEircode(String eircode) {
        return (eircode.matches("(?:^[AC-FHKNPRTV-Y][0-9]{2}|D6W)[ -]?[0-9AC-FHKNPRTV-Y]{4}$")); //7 0-9 numbers followed by 2 a-z characters
    }

    /**
     * Checks if the given string contains a valid booth identifier one letter followed by a number
     * @param identifier generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validBoothIdentifier(String identifier) {
        return (identifier.matches("^[A-Z][0-9]")); //7 0-9 numbers followed by 2 a-z characters
    }

}