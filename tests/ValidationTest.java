import Utilities.CustomList;
import Utilities.Validation;
import model.Patient;
import model.VaccinationBooth;
import model.VaccinationCentre;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {
		private Patient patient;
		private VaccinationCentre vaccinationCentre;
		private VaccinationBooth vaccinationBooth;

		@Before
		public void setUp() {
				CustomList<Patient> patientsTest = new CustomList<>();
				CustomList<VaccinationCentre> centresTest = new CustomList<>();
				CustomList<VaccinationBooth> boothTest = new CustomList<>();
				patient = new Patient("Billy", "321324243s", LocalDate.now(), "wexford","333","dsd@poop", "N/A");
				vaccinationCentre = new VaccinationCentre("Billy", "wexford", "Y35E308",2);
				vaccinationBooth = new VaccinationBooth("A1", 2, "N/A");
				patientsTest.add(patient);
				centresTest.add(vaccinationCentre);
				boothTest.add(vaccinationBooth);
		}

		@Test
		public void testValidPPSN() {
				patient.setPatientIdentifier("1234567AB");
				assertTrue(Validation.validPPS(patient.getPatientIdentifier()));
				patient.setPatientIdentifier("1234567 AB");
				assertFalse(Validation.validPPS(patient.getPatientIdentifier()));
				patient.setPatientIdentifier("12345AB");
				assertFalse(Validation.validPPS(patient.getPatientIdentifier()));
				patient.setPatientIdentifier("1234567ab");
				assertTrue(Validation.validPPS(patient.getPatientIdentifier()));
		}

		@Test
		public void testValidEmail() {
				patient.setEmail("hi@bye");
				assertTrue(Validation.validEmail(patient.getEmail()));
				patient.setEmail("hi@");
				assertFalse(Validation.validEmail(patient.getEmail()));
				patient.setEmail("hi@bye.com");
				assertTrue(Validation.validEmail(patient.getEmail()));
				patient.setEmail("h");
				assertFalse(Validation.validEmail(patient.getEmail()));
		}

		@Test
		public void testValidPhone() {
				patient.setPhone("089422 2561");
				assertTrue(Validation.validPhone(patient.getPhone()));
				patient.setPhone("089 422 2561");
				assertTrue(Validation.validPhone(patient.getPhone()));
				patient.setPhone("089-422-2561");
				assertTrue(Validation.validPhone(patient.getPhone()));
				patient.setPhone("089 422 25612");
				assertFalse(Validation.validPhone(patient.getPhone()));
				patient.setPhone("111 111 1111");
				assertTrue(Validation.validPhone(patient.getPhone()));
		}

		@Test
		public void testValidBoothID() {
				vaccinationBooth.setIdentifier("a1");
				assertFalse(Validation.validBoothIdentifier(vaccinationBooth.getIdentifier()));
				vaccinationBooth.setIdentifier("2A");
				assertFalse(Validation.validBoothIdentifier(vaccinationBooth.getIdentifier()));
				vaccinationBooth.setIdentifier("A1");
				assertTrue(Validation.validBoothIdentifier(vaccinationBooth.getIdentifier()));
				vaccinationBooth.setIdentifier("A10");
				assertFalse(Validation.validBoothIdentifier(vaccinationBooth.getIdentifier()));
				vaccinationBooth.setIdentifier("A100");
				assertFalse(Validation.validBoothIdentifier(vaccinationBooth.getIdentifier()));
		}

		@Test
		public void testValidEircode() {
				vaccinationCentre.setEircode("y35e308");
				assertFalse(Validation.validPhone(vaccinationCentre.getEircode()));
				vaccinationCentre.setEircode("Y35E308");
				assertFalse(Validation.validPhone(vaccinationCentre.getEircode()));
				vaccinationCentre.setEircode("Y35 E308");
				assertFalse(Validation.validPhone(vaccinationCentre.getEircode()));
				vaccinationCentre.setEircode("A11 A111");
				assertFalse(Validation.validPhone(vaccinationCentre.getEircode()));
		}
}
