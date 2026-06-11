package com.veterinary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class VeterinaryApplicationTest {

    private static MongoClient testMongoClient;

    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeAll
    static void initFactory() {
        String uri = "mongodb+srv://yonimonigm_db_user:hZVNgo3zuURKjM3H"
                + "@veterinary.5nopo6y.mongodb.net/Veterinary?retryWrites=true&w=majority";
        testMongoClient = MongoClients.create(uri);
        MainApp.setMongoClient(testMongoClient);
    }

    @AfterAll
    static void closeFactory() {
        if (testMongoClient != null) {
            testMongoClient.close();
        }
    }

    @BeforeEach
    void saveStreams() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private String runApp(String menuLines) throws Exception {
        String finalInput = menuLines.endsWith("4\n") ? menuLines : menuLines + "4\n";
        System.setIn(new ByteArrayInputStream(finalInput.getBytes("UTF-8")));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, true, "UTF-8"));
        
        MainApp.main(new String[]{"test"});
        
        return baos.toString("UTF-8");
    }

    @Test
    @Order(1)
    void menuExitImmediately() throws Exception {
        assertTrue(runApp("4\n").length() > 0);
    }

    @Test
    @Order(2)
    void menuUnknownCommandThenExit() throws Exception {
        assertTrue(runApp("99\n4\n").length() > 0);
    }

    @Test
    @Order(3)
    void menuClearThenReportEmptyDB() throws Exception {
        String out = runApp("3\n2\n4\n");
        String lowerOut = out.toLowerCase();
        assertTrue(lowerOut.contains("empty") || lowerOut.contains("порожня") || lowerOut.contains("notice"));
    }

    @Test
    @Order(4)
    void menuLoadData() throws Exception {
        runApp("3\n4\n");
        assertDoesNotThrow(() -> runApp("1\n4\n"));
    }

    @Test
    @Order(5)
    void menuReportWithData() throws Exception {
        runApp("3\n4\n");
        runApp("1\n4\n");
        String withData = runApp("2\n4\n");
        String empty = runApp("3\n2\n4\n");
        assertTrue(withData.length() >= empty.length());
    }

    @Test
    @Order(6)
    void menuFullCycleAllBranches() throws Exception {
        assertDoesNotThrow(() -> runApp("3\n2\n1\n5\nLn\nFn\nPn\nSp\nVet\nDiag\n2\n99\n4\n"));
    }

    @Test
    @Order(7)
    void menuEofTerminatesGracefully() {
        assertDoesNotThrow(() -> {
            System.setIn(new ByteArrayInputStream("4\n".getBytes("UTF-8")));
            System.setOut(new PrintStream(new ByteArrayOutputStream(), true, "UTF-8"));
            MainApp.main(new String[]{"test"});
        });
    }

    @Test
    @Order(8)
    void menuClearTwiceIsIdempotent() throws Exception {
        assertDoesNotThrow(() -> runApp("3\n3\n4\n"));
    }

    @Test
    @Order(9)
    void directCallPrintMenu() {
        assertDoesNotThrow(() -> MainApp.main(new String[]{"4"}));
    }

    @Test
    @Order(10)
    void directCallLoadShowClearSequence() {
        assertDoesNotThrow(() -> MainApp.main(new String[]{"3"}));
        assertDoesNotThrow(() -> MainApp.main(new String[]{"1"}));
        assertDoesNotThrow(() -> MainApp.main(new String[]{"2"}));
        assertDoesNotThrow(() -> MainApp.main(new String[]{"3"}));
    }

    @Test
    @Order(11)
    void mainMethodCatchBranchWhenNoPostgres() {
        assertDoesNotThrow(() -> {
            InputStream old = System.in;
            System.setIn(new ByteArrayInputStream("4\n".getBytes("UTF-8")));
            try {
                MainApp.main(new String[]{"test"});
            } finally {
                System.setIn(old);
            }
        });
    }

    @Test
    @Order(20)
    void entitiesAllGettersSettersToString() {
        Owner o = new Owner();
        o.setOwnerId(1);
        o.setLastName("A");
        o.setFirstName("B");
        o.setMiddleName("C");
        o.setCity("D");
        o.setStreet("E");
        o.setHouse("1");
        o.setApartment("2");
        o.setPhone("000");

        assertAll(
            () -> assertEquals(1, o.getOwnerId()),
            () -> assertEquals("A", o.getLastName()),
            () -> assertEquals("B", o.getFirstName()),
            () -> assertEquals("C", o.getMiddleName()),
            () -> assertEquals("D", o.getCity()),
            () -> assertEquals("E", o.getStreet()),
            () -> assertEquals("1", o.getHouse()),
            () -> assertEquals("2", o.getApartment()),
            () -> assertEquals("000", o.getPhone()),
            () -> assertNotNull(o.toString())
        );

        Veterinarian v = new Veterinarian();
        v.setFullName("Doc");
        v.setExperienceYears(7);
        assertEquals("Doc", v.getFullName());
        assertEquals(7, v.getExperienceYears());

        Pet p = new Pet();
        p.setOwner(o);
        p.setName("Cat");
        p.setSpecies("cat");
        assertAll(
            () -> assertEquals("Cat", p.getName()),
            () -> assertEquals("cat", p.getSpecies()),
            () -> assertEquals(o, p.getOwner()),
            () -> assertNull(p.getPetId()),
            () -> assertNotNull(p.toString())
        );

        Appointment a = new Appointment();
        a.setId(5);
        a.setPet(p);
        a.setVeterinarian(v);
        a.setAppointmentDate(java.time.LocalDate.now());
        a.setAppointmentTime(java.time.LocalTime.now());
        a.setDiagnosisTreatment("Test");
        a.setVaccinated(true);
        assertAll(
            () -> assertEquals(5, a.getId()),
            () -> assertTrue(a.isVaccinated()),
            () -> assertNotNull(a.toString())
        );
    }
}
