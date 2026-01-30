package controller;

import model.*;
import model.interfaces.Payable;
import repository.*;
import service.*;
import utils.ReflectionUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final FreelancerService freelancerService;
    private final ProjectService projectService;

    public Main() {
        // Инициализация сервисов с репозиториями
        this.clientService = new ClientService(new ClientRepository());
        this.freelancerService = new FreelancerService(new FreelancerRepository());
        this.projectService = new ProjectService(new ProjectRepository());
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Freelance Marketplace (Assignment 4) ===");
            System.out.println("1. Client Management (CRUD)");
            System.out.println("2. Freelancer Management (CRUD)");
            System.out.println("3. Project Management (CRUD + Payable)");
            System.out.println("4. Reflection Demo");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> clientMenu();
                case "2" -> freelancerMenu();
                case "3" -> projectMenu();
                case "4" -> ReflectionUtils.inspectEntity(Project.class);
                case "0" -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // --- 1. CLIENT MENU ---
    private void clientMenu() {
        System.out.println("\n--- Client CRUD ---");
        System.out.println("1. Create Client");
        System.out.println("2. List All Clients");
        System.out.println("3. Find by ID");
        System.out.println("4. Update Client");
        System.out.println("5. Delete Client");
        System.out.print("Select: ");

        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("First Name: "); String fn = scanner.nextLine();
                    System.out.print("Last Name: "); String ln = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    clientService.create(new Client(0, fn, ln, email, LocalDate.now()));
                    System.out.println("Success.");
                }
                case "2" -> clientService.getAll().forEach(BaseEntity::printInfo);
                case "3" -> {
                    System.out.print("ID: ");
                    clientService.getById(Integer.parseInt(scanner.nextLine())).printInfo();
                }
                case "4" -> {
                    System.out.print("ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Client c = clientService.getById(id);
                    System.out.print("New Name (current " + c.getFirstName() + "): ");
                    String input = scanner.nextLine();
                    if (!input.isBlank()) c.setFirstName(input);
                    clientService.update(id, c);
                    System.out.println("Updated.");
                }
                case "5" -> {
                    System.out.print("ID to delete: ");
                    clientService.delete(Integer.parseInt(scanner.nextLine()));
                    System.out.println("Deleted.");
                }
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    // --- 2. FREELANCER MENU ---
    private void freelancerMenu() {
        System.out.println("\n--- Freelancer CRUD ---");
        System.out.println("1. Create Freelancer");
        System.out.println("2. List All (Sorted by Rating)");
        System.out.println("3. Find by ID");
        System.out.println("4. Update Rating");
        System.out.println("5. Delete Freelancer");
        System.out.print("Select: ");

        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("First Name: "); String fn = scanner.nextLine();
                    System.out.print("Last Name: "); String ln = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Rating (0-5): "); double r = Double.parseDouble(scanner.nextLine());
                    freelancerService.create(new Freelancer(0, fn, ln, email, r, LocalDate.now()));
                    System.out.println("Success.");
                }
                case "2" -> freelancerService.getAll().forEach(f ->
                        System.out.println(f.getFirstName() + " | Rating: " + f.getRating()));
                case "3" -> {
                    System.out.print("ID: ");
                    freelancerService.getById(Integer.parseInt(scanner.nextLine())).printInfo();
                }
                case "4" -> {
                    System.out.print("ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Freelancer f = freelancerService.getById(id);
                    System.out.print("New Rating: ");
                    f.setRating(Double.parseDouble(scanner.nextLine()));
                    freelancerService.update(id, f);
                    System.out.println("Updated.");
                }
                case "5" -> {
                    System.out.print("ID to delete: ");
                    freelancerService.delete(Integer.parseInt(scanner.nextLine()));
                    System.out.println("Deleted.");
                }
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    // --- 3. PROJECT MENU ---
    private void projectMenu() {
        System.out.println("\n--- Project Management ---");
        System.out.println("1. Create Project");
        System.out.println("2. List Projects");
        System.out.println("3. Find by ID");
        System.out.println("4. Update Budget");
        System.out.println("5. Delete Project");
        System.out.print("Select: ");

        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Title: "); String t = scanner.nextLine();
                    // Description удалил, его нет в конструкторе Project
                    System.out.print("Budget: "); double b = Double.parseDouble(scanner.nextLine());

                    // ФИКС: Создаем временного клиента, так как Project требует объект Client
                    // Если у тебя есть clientService.getById(1), лучше использовать его
                    Client dummyClient = new Client(1, "Admin", "User", "admin@mail.com", LocalDate.now());

                    projectService.create(new Project(0, t, b, LocalDate.now(), dummyClient));
                    System.out.println("Success.");
                }
                case "2" -> {
                    // Используем обычный getAll()
                    // Если IDE ругается на getAll(), проверь ProjectService! Он должен наследовать CrudService
                    projectService.getAll().forEach(p -> {
                        // Приводим к Payable безопасным способом (если класс реализует интерфейс)
                        if (p instanceof Payable) {
                            System.out.println(p.getTitle() + " | Payment: " + ((Payable)p).getAmount());
                        } else {
                            System.out.println(p.getTitle());
                        }
                    });
                }
                case "3" -> {
                    System.out.print("ID: ");
                    Project p = projectService.getById(Integer.parseInt(scanner.nextLine()));
                    System.out.println(p.getTitle() + " - " + p.getBudget());
                }
                case "4" -> {
                    System.out.print("ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Project p = projectService.getById(id);
                    System.out.print("New Budget: ");
                    p.setBudget(Double.parseDouble(scanner.nextLine()));
                    projectService.update(id, p);
                    System.out.println("Updated.");
                }
                case "5" -> {
                    System.out.print("ID to delete: ");
                    projectService.delete(Integer.parseInt(scanner.nextLine()));
                    System.out.println("Deleted.");
                }
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }
}