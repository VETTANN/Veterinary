package com.veterinary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class VeterinaryApplicationTest {

    private static MongoClient testMongoClient;

    @BeforeAll
    static void initFactory() {
        String uri = "mongodb+srv://yonimonigm_db_user:hZVNgo3zuURKjM3H@veterinary.5nopo6y.mongodb.net/Veterinary?retryWrites=true&w=majority";
        testMongoClient = MongoClients.create(uri);
        MainApp.setMongoClient(testMongoClient);
    }
    @AfterAll
    static void closeFactory() {
        if (testMongoClient != null) {
            testMongoClient.close();
        }
    }

    @Test
    @Order(1)
    void testMongoConnectionIsNotNull() {
        assertNotNull(testMongoClient, "Клієнт MongoDB не повинен бути null");
    }

    @Test
    @Order(2)
    void testMainAppCompilesAndRuns() {
        MainApp app = new MainApp();
        assertNotNull(app);
    }

    @Test
    @Order(3)
    void testLoadTestDataDoesNotThrow() {
        assertDoesNotThrow(() -> MainApp.loadTestData());
    }

    @Test
    @Order(4)
    void testShowReportDoesNotThrow() {
        assertDoesNotThrow(() -> MainApp.showReport());
    }

    @Test
    @Order(5)
    void testClearDatabaseDoesNotThrow() {
        // Тестуємо очищення бази даних
        assertDoesNotThrow(() -> MainApp.clearDatabase());
    }

    @Test
    @Order(6)
    void testShowReportWhenClientIsNull() {
        MainApp.setMongoClient(null);
        assertDoesNotThrow(() -> MainApp.showReport());
        // Повертаємо назад клієнт для стабільності
        MainApp.setMongoClient(testMongoClient);
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