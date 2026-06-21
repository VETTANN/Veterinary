package com.veterinary;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.UUID;
import org.bson.Document;

public class MainApp {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    /**
     * 
     * @param client клієнт MongoClient
     */
    public static void setMongoClient(MongoClient client) {
        mongoClient = client;
        if (client != null) {
            database = client.getDatabase("Veterinary");
        }
    }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Помилка кодування: " + e.getMessage());
        }

        String uri = "mongodb+srv://yonimonigm_db_user:hZVNgo3zuURKjM3H"
                + "@veterinary.5nopo6y.mongodb.net/Veterinary?retryWrites=true&w=majority";

        try {
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("Veterinary");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            System.out.println(">>> Програма 'Ветеринарна клініка' запущена (MongoDB Atlas).");

            while (running) {
                printMenu();
                System.out.print("Ваш вибір: ");
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
                    case "5":
                        addNewAppointment(scanner);
                        break;
                    default:
                        System.out.println("!!! Невідома команда, спробуйте ще раз.");
                }
            }

            mongoClient.close();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Критична помилка додатка: " + e.getMessage());
        }
    }
    
    private static void printMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println(" МЕНЮ УПРАВЛІННЯ (MongoDB Atlas):");
        System.out.println(" 1. Наповнити базу тестовими даними");
        System.out.println(" 2. Переглянути звіт прийомів");
        System.out.println(" 3. Видалити всі дані");
        System.out.println(" 5. Додати ВЛАСНИЙ запис прийому (+)");
        System.out.println(" 4. Вихід");
        System.out.println("-------------------------------------------");
    }

    public static void loadTestData() {
        try {
            System.out.println(">>> Завантаження даних в MongoDB...");
            MongoCollection<Document> collection = database.getCollection("appointments");
            collection.deleteMany(new Document());

            collection.insertOne(new Document()
                .append("id", UUID.randomUUID().toString())
                .append("appointmentDate", "2024-09-14").append("appointmentTime", "10:00")
                .append("ownerLastName", "Коваленко").append("ownerFirstName", "Олег")
                .append("ownerMiddleName", "Іванович").append("ownerPhone", "380501234567")
                .append("ownerCity", "м. Київ").append("ownerStreet", "вул. Шевченка")
                .append("ownerHouse", "12").append("ownerApartment", "кв.5")
                .append("petName", "Барсік").append("petSpecies", "кіт")
                .append("vetFullName", "Петренко Ірина Василівна").append("vetExperience", 5)
                .append("diagnosis", "Щеплення комплекс.").append("isVaccinated", true));

            System.out.println(">>> Тестові дані успішно імпортовано!");
        } catch (Exception e) {
            System.err.println("Помилка при додаванні даних: " + e.getMessage());
        }
    }

    private static void addNewAppointment(Scanner scanner) {
        try {
            System.out.println("\n>>> СТВОРЕННЯ НОВОГО ЗАПИСУ ПРИЙОМУ:");
            MongoCollection<Document> collection = database.getCollection("appointments");

            System.out.print("Прізвище власника: ");
            String lastName = scanner.nextLine();
            System.out.print("Ім'я власника: ");
            String firstName = scanner.nextLine();
            System.out.print("Кличка тварини: ");
            String petName = scanner.nextLine();
            System.out.print("Вид тварини (кіт/собака): ");
            String species = scanner.nextLine();
            System.out.print("ПІБ Ветеринара: ");
            String vet = scanner.nextLine();
            System.out.print("Діагноз/Лікування: ");
            String diag = scanner.nextLine();

            Document doc = new Document()
                .append("id", UUID.randomUUID().toString())
                .append("appointmentDate", "2026-06-09")
                .append("appointmentTime", "12:00")
                .append("ownerLastName", lastName)
                .append("ownerFirstName", firstName)
                .append("petName", petName)
                .append("petSpecies", species)
                .append("vetFullName", vet)
                .append("diagnosis", diag)
                .append("isVaccinated", false);

            collection.insertOne(doc);
            System.out.println(">>> Ваш персональний запис успішно додано в MongoDB Atlas!");
        } catch (Exception e) {
            System.err.println("Помилка додавання запису: " + e.getMessage());
        }
    }

    public static void showReport() {
        try {
            MongoCollection<Document> collection = database.getCollection("appointments");
            long count = collection.countDocuments();

            if (count == 0) {
                System.out.println("!!! Увага: База даних порожня.");
            } else {
                String line = new String(new char[115]).replace('\0', '-');
                System.out.println("\n" + line);
                String headerFormat = "| %-25s | %-15s | %-25s | %-12s | %-5s |\n";
                System.out.printf(headerFormat, "Власник", "Тварина", "Ветеринар", "Дата", "Час");
                System.out.println(line);

                for (Document doc : collection.find()) {
                    String lastName = doc.getString("ownerLastName");
                    String firstName = doc.getString("ownerFirstName");
                    String initials = (firstName != null && !firstName.isEmpty()) 
                        ? firstName.substring(0, 1) + "." : "";

                    String ownerInfo = lastName + " " + initials;
                    String petInfo = doc.getString("petName") + " (" + doc.getString("petSpecies") + ")";

                    System.out.printf("| %-25s | %-15s | %-25s | %-12s | %-5s |\n",
                            ownerInfo,
                            petInfo,
                            doc.getString("vetFullName"),
                            doc.getString("appointmentDate"),
                            doc.getString("appointmentTime"));
                }
                System.out.println(line + "\n");
            }
        } catch (Exception e) {
            System.err.println("Помилка при формуванні звіту: " + e.getMessage());
        }
    }

    public static void clearDatabase() {
        try {
            System.out.println(">>> Очищення бази даних...");
            MongoCollection<Document> collection = database.getCollection("appointments");
            collection.deleteMany(new Document());
            System.out.println(">>> База даних повністю очищена!");
        } catch (Exception e) {
            System.err.println("Помилка при очищенні: " + e.getMessage());
        }
    }
}