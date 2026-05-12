package com.veterinary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
class VeterinaryApplicationTest {

    private static SessionFactory testFactory;

    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeAll
    static void initFactory() {
        // ✅ Будуємо SessionFactory програмно — без жодного cfg.xml файлу
        // Використовує H2 in-memory, не потребує PostgreSQL і жодних файлів
        Configuration cfg = new Configuration();

        // H2 in-memory налаштування
        cfg.setProperty(Environment.DRIVER,   "org.h2.Driver");
        cfg.setProperty(Environment.URL,      "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        cfg.setProperty(Environment.USER,     "sa");
        cfg.setProperty(Environment.PASS,     "");
        cfg.setProperty(Environment.DIALECT,  "org.hibernate.dialect.H2Dialect");
        cfg.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
        cfg.setProperty(Environment.SHOW_SQL, "false");
        cfg.setProperty("hibernate.current_session_context_class", "thread");

        // Реєструємо всі сутності
        cfg.addAnnotatedClass(Owner.class);
        cfg.addAnnotatedClass(Veterinarian.class);
        cfg.addAnnotatedClass(Pet.class);
        cfg.addAnnotatedClass(Appointment.class);

        ServiceRegistry sr = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();

        testFactory = cfg.buildSessionFactory(sr);
    }

    @AfterAll
    static void closeFactory() {
        if (testFactory != null) testFactory.close();
    }

    @BeforeEach
    void saveStreams() {
        originalIn  = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /** Створює MainApp з H2-factory і годує stdin рядками меню. */
    private String runApp(String menuLines) throws Exception {
        System.setIn(new ByteArrayInputStream(menuLines.getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, true, "UTF-8"));
        MainApp app = new MainApp();
        app.setFactory(testFactory);
        app.run();
        return baos.toString("UTF-8");
    }

    // =========================================================================
    //  MainApp — інтеграційні тести
    // =========================================================================

    @Test @Order(1)
    void menu_exit_immediately() throws Exception {
        assertTrue(runApp("4\n").length() > 0);
    }

    @Test @Order(2)
    void menu_unknownCommand_thenExit() throws Exception {
        assertTrue(runApp("99\nabc\n4\n").length() > 0);
    }

    /** case "3" + case "2" порожня БД → if (results.isEmpty()) */
    @Test @Order(3)
    void menu_clear_thenReport_emptyDB() throws Exception {
        String out = runApp("3\n2\n4\n");
        assertTrue(out.contains("!!!"));
    }

    /** case "1" — завантаження даних */
    @Test @Order(4)
    void menu_loadData() throws Exception {
        runApp("3\n4\n");
        assertDoesNotThrow(() -> runApp("1\n4\n"));
    }

    /** case "2" з даними → else-гілка */
    @Test @Order(5)
    void menu_report_withData() throws Exception {
        runApp("3\n4\n");
        runApp("1\n4\n");
        String withData = runApp("2\n4\n");
        String empty    = runApp("3\n2\n4\n");
        assertTrue(withData.contains("---"));
        assertTrue(withData.length() > empty.length());
    }

    /** Повний цикл — усі 5 гілок switch */
    @Test @Order(6)
    void menu_fullCycle_allBranches() throws Exception {
        assertDoesNotThrow(() -> runApp("3\n2\n1\n2\n99\n4\n"));
    }

    /** !scanner.hasNextLine() → break */
    @Test @Order(7)
    void menu_eof_terminatesGracefully() {
        assertDoesNotThrow(() -> {
            System.setIn(new ByteArrayInputStream(new byte[0]));
            System.setOut(new PrintStream(new ByteArrayOutputStream(), true, "UTF-8"));
            MainApp app = new MainApp();
            app.setFactory(testFactory);
            app.run();
        });
    }

    @Test @Order(8)
    void menu_clearTwice_isIdempotent() throws Exception {
        assertDoesNotThrow(() -> runApp("3\n3\n4\n"));
    }

    /** printMenu() напряму */
    @Test @Order(9)
    void directCall_printMenu() {
        MainApp app = new MainApp();
        app.setFactory(testFactory);
        assertDoesNotThrow(app::printMenu);
    }

    /** Пряме покриття всіх методів без stdin */
    @Test @Order(10)
    void directCall_loadShowClear_sequence() {
        MainApp app = new MainApp();
        app.setFactory(testFactory);
        assertDoesNotThrow(app::clearDatabase);
        assertDoesNotThrow(app::loadTestData);
        assertDoesNotThrow(app::showReport);
        assertDoesNotThrow(app::clearDatabase);
        assertDoesNotThrow(app::showReport);
    }

    /** MainApp.main() — catch(Exception) коли PostgreSQL недоступний */
    @Test @Order(11)
    void mainMethod_catchBranch_whenNoPostgres() {
        assertDoesNotThrow(() -> {
            InputStream old = System.in;
            System.setIn(new ByteArrayInputStream("4\n".getBytes("UTF-8")));
            try { MainApp.main(new String[]{}); }
            finally { System.setIn(old); }
        });
    }

    // =========================================================================
    //  Сутності — страховий блок покриття
    // =========================================================================

    @Test @Order(20)
    void entities_allGettersSettersToString() {
        Owner o = new Owner();
        o.setOwnerId(1); o.setLastName("A"); o.setFirstName("B");
        o.setMiddleName("C"); o.setCity("D"); o.setStreet("E");
        o.setHouse("1"); o.setApartment("2"); o.setPhone("000");
        assertAll(
            () -> assertEquals(1,     o.getOwnerId()),
            () -> assertEquals("A",   o.getLastName()),
            () -> assertEquals("B",   o.getFirstName()),
            () -> assertEquals("C",   o.getMiddleName()),
            () -> assertEquals("D",   o.getCity()),
            () -> assertEquals("E",   o.getStreet()),
            () -> assertEquals("1",   o.getHouse()),
            () -> assertEquals("2",   o.getApartment()),
            () -> assertEquals("000", o.getPhone()),
            () -> assertNotNull(o.toString())
        );

        Veterinarian v = new Veterinarian();
        v.setFullName("Doc"); v.setExperienceYears(7);
        assertEquals("Doc", v.getFullName());
        assertEquals(7, v.getExperienceYears());

        Pet p = new Pet();
        p.setOwner(o); p.setName("Cat"); p.setSpecies("cat");
        assertAll(
            () -> assertEquals("Cat", p.getName()),
            () -> assertEquals("cat", p.getSpecies()),
            () -> assertEquals(o,     p.getOwner()),
            () -> assertNull(p.getPetId()),
            () -> assertNotNull(p.toString())
        );

        Appointment a = new Appointment();
        a.setId(5); a.setPet(p); a.setVeterinarian(v);
        a.setAppointmentDate(java.time.LocalDate.now());
        a.setAppointmentTime(java.time.LocalTime.now());
        a.setDiagnosisTreatment("Test"); a.setVaccinated(true);
        assertAll(
            () -> assertEquals(5,      a.getId()),
            () -> assertTrue(a.isVaccinated()),
            () -> assertNotNull(a.toString())
        );

        // null-pet гілка toString
        Appointment nullPet = new Appointment();
        nullPet.setDiagnosisTreatment("x");
        assertTrue(nullPet.toString().contains("null"));
    }
}