package com.veterinary;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {

    private SessionFactory factory;

    /**
     * Встановлює фабрику сесій.
     * @param factory фабрика сесій Hibernate.
     */
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "Cp866"));
            System.setErr(new PrintStream(System.err, true, "Cp866"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Encoding error: " + e.getMessage());
        }

        MainApp app = new MainApp();
        try {
            app.factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Owner.class)
                    .addAnnotatedClass(Veterinarian.class)
                    .addAnnotatedClass(Pet.class)
                    .addAnnotatedClass(Appointment.class)
                    .buildSessionFactory();

            app.run();

        } catch (Exception e) {
            System.err.println("Критична помилка ініціалізації: " + e.getMessage());
        } finally {
            if (app.factory != null) {
                app.factory.close();
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println(">>> Програма 'Ветеринарна клініка' запущена.");

        while (running) {
            printMenu();
            System.out.print("Ваш вибір: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    loadTestData();
                    break;
                case "2":
                    showReport();
                    break;
                case "3":
                    clearDatabase();
                    break;
                case "4":
                    System.out.println(">>> Завершення роботи...");
                    running = false;
                    break;
                default:
                    System.out.println("!!! Невідома команда, спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    void printMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println(" МЕНЮ УПРАВЛІННЯ:");
        System.out.println(" 1. Наповнити базу тестовими даними");
        System.out.println(" 2. Переглянути звіт прийомів");
        System.out.println(" 3. Видалити всі дані");
        System.out.println(" 4. Вихід");
        System.out.println("-------------------------------------------");
    }

    void loadTestData() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            System.out.println(">>> Завантаження офіційних даних...");

            Veterinarian v1 = new Veterinarian("Петренко Ірина Василівна", 10);
            Veterinarian v2 = new Veterinarian("Іваненко Андрій Миколайович", 8);
            Veterinarian v3 = new Veterinarian("Мельник Оксана Петрівна", 12);
            session.persist(v1);
            session.persist(v2);
            session.persist(v3);

            Owner o1 = new Owner("Коваленко", "Олег", "Іванович", "м. Київ", "Шевченка", "12", "5", "380501111111");
            Pet p1 = new Pet(o1, "Барсік", "кіт");
            session.persist(o1); 
            session.persist(p1);
            session.persist(new Appointment(p1, v1, LocalDate.parse("2024-09-14"), 
                LocalTime.parse("10:00:00"), "Комплексне щеплення, огляд", true));

            Owner o2 = new Owner("Сидоренко", "Марія", "Олексіївна", "м. Київ", "Лесі Українки", "5", null, 
                "380502222222");
            Pet p2 = new Pet(o2, "Рекс", "собака");
            session.persist(o2); 
            session.persist(p2);
            session.persist(new Appointment(p2, v1, LocalDate.parse("2024-09-14"), 
                LocalTime.parse("11:30:00"), "Щеплення від сказу", true));

            Owner o3 = new Owner("Бондаренко", "Дмитро", "Сергійович", "м. Київ", "просп. Перемоги", "33", 
                null, "380503333333");
            Pet p3 = new Pet(o3, "Муся", "кішка");
            session.persist(o3); 
            session.persist(p3);
            session.persist(new Appointment(p3, v2, LocalDate.parse("2024-09-14"), 
                LocalTime.parse("14:20:00"), "Огляд після операції", false));

            Owner o4 = new Owner("Ткаченко", "Оля", "Ігорівна", "м. Київ", "Хрещатик", "25", null, "380504444444");
            Pet p4 = new Pet(o4, "Джек", "собака");
            session.persist(o4); 
            session.persist(p4);
            session.persist(new Appointment(p4, v3, LocalDate.parse("2024-09-14"), 
                LocalTime.parse("15:10:00"), "Чищення зубів", false));

            Owner o5 = new Owner("Коваленко", "Сергій", "Володимирович", "м. Київ", "Саксаганського", "7", 
                null, "380505555555");
            Pet p5 = new Pet(o5, "Зоря", "кішка");
            session.persist(o5); 
            session.persist(p5);
            session.persist(new Appointment(p5, v3, LocalDate.parse("2024-09-15"), 
                LocalTime.parse("09:15:00"), "Лікування захворювання шкіри", false));

            Owner o6 = new Owner("Марченко", "Ірина", "Олегівна", "м. Київ", "Богдана Хмельницького", "10", 
                null, "380506666666");
            Pet p6 = new Pet(o6, "Буч", "собака");
            session.persist(o6); 
            session.persist(p6);
            session.persist(new Appointment(p6, v3, LocalDate.parse("2024-09-15"), 
                LocalTime.parse("11:00:00"), "Комплексне щеплення, огляд", true));

            Owner o7 = new Owner("Шевчук", "Павло", "Миколайович", "м. Київ", "Антоновича", "72", null, 
                "380507777777");
            Pet p7 = new Pet(o7, "Сімба", "кіт");
            session.persist(o7); 
            session.persist(p7);
            session.persist(new Appointment(p7, v1, LocalDate.parse("2024-09-15"), 
                LocalTime.parse("14:45:00"), "Кастрація", false));

            Owner o8 = new Owner("Гончарук", "Наталія", "Вікторівна", "м. Київ", "Велика Васильківська", 
                "7", null, "380508888888");
            Pet p8 = new Pet(o8, "Лапа", "собака");
            session.persist(o8); 
            session.persist(p8);
            session.persist(new Appointment(p8, v2, LocalDate.parse("2024-09-16"), 
                LocalTime.parse("10:30:00"), "Щеплення від сказу", true));

            Owner o9 = new Owner("Лисенко", "Артем", "Олексійович", "м. Київ", "Дегтярівська", "33", null, 
                "380509999999");
            Pet p9 = new Pet(o9, "Мурка", "кішка");
            session.persist(o9); 
            session.persist(p9);
            session.persist(new Appointment(p9, v3, LocalDate.parse("2024-09-16"), 
                LocalTime.parse("12:15:00"), "Лікування кон'юнктивіту", false));

            Owner o10 = new Owner("Павленко", "Катерина", "Сергіївна", "м. Київ", "Олени Теліги", "18", 
                null, "380501010101");
            Pet p10 = new Pet(o10, "Тедді", "собака");
            session.persist(o10); 
            session.persist(p10);
            session.persist(new Appointment(p10, v1, LocalDate.parse("2024-09-16"), 
                LocalTime.parse("15:00:00"), "Комплексне щеплення, огляд", true));

            Owner o11 = new Owner("Ковальчук", "Михайло", "Іванович", "м. Київ", "Дорогожицька", "2", 
                null, "380501112131");
            Pet p11 = new Pet(o11, "Річ", "кіт");
            session.persist(o11); 
            session.persist(p11);
            session.persist(new Appointment(p11, v2, LocalDate.parse("2024-09-17"), 
                LocalTime.parse("09:45:00"), "Огляд після лікування", false));

            session.persist(new Appointment(p3, v3, LocalDate.parse("2024-09-17"), 
                LocalTime.parse("13:30:00"), "Комплексне щеплення, огляд", true));

            tx.commit();
            System.out.println(">>> Дані про запис успішно додано!");
        } catch (Exception e) {
            System.err.println("Помилка при додаванні даних: " + e.getMessage());
        }
    }

    void showReport() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            String hql = "SELECT new com.veterinary.AppointmentInfoDTO("
                    + "a.appointmentDate, a.appointmentTime, "
                    + "o.lastName, o.firstName, o.middleName, o.phone, "
                    + "o.city, o.street, o.house, o.apartment, "
                    + "p.name, p.species, v.fullName, v.experienceYears, "
                    + "a.diagnosisTreatment, a.isVaccinated) "
                    + "FROM Appointment a "
                    + "JOIN a.pet p JOIN p.owner o JOIN a.veterinarian v "
                    + "ORDER BY a.appointmentDate, a.appointmentTime";

            List<AppointmentInfoDTO> results = session.createQuery(hql,
                    AppointmentInfoDTO.class).getResultList();

            if (results.isEmpty()) {
                System.out.println("!!! Увага: База даних порожня.");
            } else {
                String line = new String(new char[115]).replace('\0', '-');
                System.out.println("\n" + line);
                String headerFormat = "| %-25s | %-15s | %-25s | %-12s | %-5s |\n";
                System.out.printf(headerFormat, "Власник", "Тварина", "Ветеринар", "Дата", "Час");
                System.out.println(line);

                for (AppointmentInfoDTO info : results) {
                    System.out.println(info.toString());
                }
                System.out.println(line + "\n");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Помилка при формуванні звіту: " + e.getMessage());
        }
    }

    void clearDatabase() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            System.out.println(">>> Очищення бази даних...");

            session.createQuery("DELETE FROM Appointment").executeUpdate();
            session.createQuery("DELETE FROM Pet").executeUpdate();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            session.createQuery("DELETE FROM Veterinarian").executeUpdate();

            tx.commit();
            System.out.println(">>> База даних повністю очищена!");
        } catch (Exception e) {
            System.err.println("Помилка при очищенні: " + e.getMessage());
        }
    }
}