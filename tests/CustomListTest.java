import Utilities.CustomList;
import model.Patient;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CustomListTest {
    private Patient patient1, patient2;
    private CustomList<Patient> patientsTest;

    @Before
    public void setUp() {
        patientsTest = new CustomList<>();
        patient1 = new Patient("Selly", "321324243s", LocalDate.now(), "wexford","333","dsd@poop", "N/A");
        patient2 = new Patient("Billy", "321324243s", LocalDate.now(), "wexford","333","dsd@poop", "N/A");
    }

   @Test
    public void testAdd() {
        assertEquals(0, patientsTest.size());
        patientsTest.add(patient1);
        assertEquals(1, patientsTest.size());
        patientsTest.add(patient2);
        assertEquals(2,patientsTest.size());
    }

    @Test
    public void testRemove() {
        patientsTest.add(patient1);
        patientsTest.add(patient2);
        patientsTest.remove(patient1);
        assertEquals(1,patientsTest.size());
        assertTrue(patientsTest.contains(patient2));
        patientsTest.remove(0);
        assertTrue(patientsTest.isEmpty());
    }

    @Test
    public void testGet() {
        patientsTest.add(patient1);
        patientsTest.add(patient2);
        assertEquals(patientsTest.get(0), patient1);
        assertEquals(patientsTest.get(1), patient2);
        patientsTest.remove(patient1);
        assertEquals(patient2,patientsTest.get(0));

        patientsTest.add(patient1);
        assertEquals(0, patientsTest.indexOf(patient2)); //quick indexof test
        assertEquals(1, patientsTest.indexOf(patient1));

        patientsTest.clear();
        assertNull(patientsTest.get(0));
    }

    @Test
    public void testToString() {
        patientsTest.add(patient1);
        patientsTest.add(patient2);
        assertEquals(patientsTest.get(0).toString(), patient1.toString());
        assertEquals(patientsTest.get(1).toString(), patient2.toString());
    }

    @Test
    public void testIterator() {
        patientsTest.add(patient1);
        patientsTest.add(patient2);
        patientsTest.add(patient2);
        patientsTest.add(patient2);
        patientsTest.add(patient2);
        patientsTest.add(patient2);
        int i = 0;
        for (Patient patient: patientsTest) {
            i++;
        }
        assertEquals(i, patientsTest.size());
    }
}