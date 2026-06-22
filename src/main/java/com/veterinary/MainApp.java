package com.veterinary;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import java.util.UUID;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainApp {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

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
    java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);
    
    System.setProperty("org.mongodb.driver.logging.level", "SEVERE");
    
    try {
        ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.mongodb.driver").setLevel(ch.qos.logback.classic.Level.ERROR);
    } catch (Throwable t) {
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
            System.out.println(">>> Завантаження даних в MongoDB Atlas...");
            MongoCollection<Document> collection = database.getCollection("appointments");
            
            //collection.deleteMany(new Document());//

            collection.insertOne(new Document()
                .append("id", UUID.randomUUID().toString())
                .append("appointmentDate", "2026-06-21")
                .append("appointmentTime", "15:30")
                .append("ownerLastName", "Коваленко")
                .append("ownerFirstName", "Олег")
                .append("ownerMiddleName", "Іванович")
                .append("ownerPhone", "380501234567")
                .append("ownerCity", "м. Черкаси")
                .append("ownerStreet", "вул. Шевченка")
                .append("ownerHouse", "12")
                .append("ownerApartment", "кв. 5")
                .append("petName", "Мурчик")
                .append("petSpecies", "кіт")
                .append("vetFullName", "Коновалов Петро Іванович")
                .append("vetExperience", 10)
                .append("diagnosis", "Огляд та профілактика")
                .append("isVaccinated", true));

            System.out.println(">>> Дані успішно імпортовано в хмару!");
            showReport();
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
            System.out.print("По батькові власника: ");
            String middleName = scanner.nextLine();
            System.out.print("Телефон власника: ");
            String phone = scanner.nextLine();
            System.out.print("Місто власника: ");
            String city = scanner.nextLine();
            System.out.print("Вулиця власника: ");
            String street = scanner.nextLine();
            System.out.print("Будинок власника: ");
            String house = scanner.nextLine();
            System.out.print("Квартира власника: ");
            String apartment = scanner.nextLine();

            Document ownerObj = new Document()
                .append("lastName", lastName)
                .append("firstName", firstName)
                .append("middleName", middleName)
                .append("city", city)
                .append("street", street)
                .append("house", house)
                .append("apartment", apartment)
                .append("phone", phone);

            System.out.print("Кличка тварини: ");
            String name = scanner.nextLine();
            System.out.print("Вид тварини (кіт/собака/кішка): ");
            String species = scanner.nextLine();

            Document petObj = new Document()
                .append("name", name)
                .append("species", species);

            System.out.print("ПІБ Ветеринара: ");
            String fullName = scanner.nextLine();
            System.out.print("Стаж ветеринара (років): ");
            int experienceYears = Integer.parseInt(scanner.nextLine());
            System.out.print("Дата прийому (РРРР-ММ-ДД): ");
            String appointmentDate = scanner.nextLine();
            System.out.print("Час прийому (ГГ:ХХ): ");
            String appointmentTime = scanner.nextLine();
            System.out.print("Діагноз та лікування: ");
            String diagnosisTreatment = scanner.nextLine();
            System.out.print("Чи вакцинована тварина? (true/false): ");
            boolean isVaccinated = Boolean.parseBoolean(scanner.nextLine());

            Document vetObj = new Document()
                .append("fullName", fullName)
                .append("experienceYears", experienceYears)
                .append("appointmentDate", appointmentDate)
                .append("appointmentTime", appointmentTime)
                .append("diagnosisTreatment", diagnosisTreatment)
                .append("isVaccinated", isVaccinated);

            Document doc = new Document()
                .append("owner", ownerObj)
                .append("pet", petObj)
                .append("veterinarian", vetObj);

            collection.insertOne(doc);
            System.out.println(">>> Новий запис успішно додано.");
            
            showReport();
        } catch (Exception e) {
            System.err.println("Помилка додавання запису: " + e.getMessage());
        }
    }

        public static void showReport() {
            try {
                MongoCollection<Document> collection = database.getCollection("appointments");
                long count = collection.countDocuments();

                if (count == 0) {
                    System.out.println("!!! Увага: База даних в MongoDB Atlas порожня.");
                } else {
                    String line = new String(new char[210]).replace('\0', '-');
                    System.out.println("\n" + line);
                    
                    String headerFormat = "| %-28s | %-12s | %-28s | %-12s | %-6s | %-30s | %-35s | %-11s | %-6s | %-12s |\n";
                    System.out.printf(headerFormat, "Власник тварини", "Тварина", "Ветеринар", "Дата прий.", "Час", "Діагноз та лікування", "Адреса власника", "Вакцинація", "Стаж", "Телефон");
                    System.out.println(line);

                    for (Document doc : collection.find()) {
                        Document ownerDoc = (Document) doc.get("owner");
                        String ownerFullName = "---";
                        String address = "---";
                        String phone = "---";
                        if (ownerDoc != null) {
                            String ln = ownerDoc.getString("lastName");
                            String fn = ownerDoc.getString("firstName");
                            String mn = ownerDoc.getString("middleName");
                            ownerFullName = (ln != null ? ln : "") + " " + (fn != null ? fn : "") + " " + (mn != null ? mn : "");
                            
                            String city = ownerDoc.getString("city");
                            String street = ownerDoc.getString("street");
                            String house = ownerDoc.getString("house");
                            String apt = ownerDoc.getString("apartment");
                            address = (city != null ? city : "") + ", " + (street != null ? street : "") + " " + (house != null ? house : "") + (apt != null && !apt.isEmpty() ? ", " + apt : "");
                            
                            phone = ownerDoc.get("phone") != null ? String.valueOf(ownerDoc.get("phone")) : "---";
                        }

                        Document petDoc = (Document) doc.get("pet");
                        String petInfo = "---";
                        if (petDoc != null) {
                            String petName = petDoc.getString("name");
                            String petSpecies = petDoc.getString("species");
                            petInfo = (petName != null ? petName : "---") + " (" + (petSpecies != null ? petSpecies : "вид") + ")";
                        }

                        Document vetDoc = (Document) doc.get("veterinarian");
                        String vetFullName = "---";
                        String experience = "0";
                        if (vetDoc != null) {
                            vetFullName = vetDoc.getString("fullName");
                            if (vetDoc.get("experienceYears") != null) {
                                experience = String.valueOf(vetDoc.get("experienceYears"));
                            }
                        }

                        String appDate = doc.getString("appointmentDate");
                        if (appDate == null && vetDoc != null) appDate = vetDoc.getString("appointmentDate");

                        String appTime = doc.getString("appointmentTime");
                        if (appTime == null && vetDoc != null) appTime = vetDoc.getString("appointmentTime");

                        String diagnosis = doc.getString("diagnosisTreatment");
                        if (diagnosis == null) diagnosis = doc.getString("diagnosis");
                        if (diagnosis == null && vetDoc != null) {
                            diagnosis = vetDoc.getString("diagnosisTreatment");
                            if (diagnosis == null) diagnosis = vetDoc.getString("diagnosis");
                        }

                        String vaccination = "Ні";
                        if (doc.get("isVaccinated") != null) {
                            vaccination = doc.getBoolean("isVaccinated") ? "Так" : "Ні";
                        } else if (vetDoc != null && vetDoc.get("isVaccinated") != null) {
                            vaccination = vetDoc.getBoolean("isVaccinated") ? "Так" : "Ні";
                        }

                        if (ownerFullName != null) ownerFullName = ownerFullName.replace("?", "і").replace("Мьр", "Мур").replace("Гончарьк", "Гончарук").replace("Сергiйович", "Сергійович").replace("Юл?я", "Юлія").replace("Серг?йович", "Сергійович").replace("Дмитр?й", "Дмитрій");
                        if (petInfo != null) petInfo = petInfo.replace("?", "і").replace("к?т", "кіт").replace("к?шка", "кішка").replace("Барс?к", "Барсік");
                        if (vetFullName != null) vetFullName = vetFullName.replace("?", "і").replace("Андрiй", "Андрій").replace("?рина", "Ірина").replace("Васил?вна", "Василівна");
                        if (diagnosis != null) diagnosis = diagnosis.replace("?", "і").replace("вiд", "від").replace("операцiї", "операції").replace("л?кування", "лікування").replace("кон'?нктив?ту", "кон'юнктивіту").replace("шкір?", "шкіри");
                        if (address != null) address = address.replace("?", "і").replace("Лесi", "Лесі").replace("Українкi", "Українки").replace("Хрещатiк", "Хрещатик").replace("Василькiвська", "Васильківська");
                        if (vaccination != null) vaccination = vaccination.replace("?", "і");

                        System.out.printf(headerFormat,
                                ownerFullName.trim().isEmpty() ? "---" : ownerFullName.trim(),
                                petInfo,
                                vetFullName != null ? vetFullName : "---",
                                appDate != null ? appDate : "---",
                                appTime != null ? appTime : "---",
                                diagnosis != null ? diagnosis : "---",
                                address,
                                vaccination,
                                experience,
                                phone);
                    }
                    System.out.println(line + "\n");
                }
            } catch (Exception e) {
                System.err.println("Помилка формування повного звіту: " + e.getMessage());
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