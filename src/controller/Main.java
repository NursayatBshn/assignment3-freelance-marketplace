package controller;

import model.*;
import service.*;
import exception.*;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ClientService clientService = new ClientService();
        FreelancerService freelancerService = new FreelancerService();
        ProjectService projectService = new ProjectService();
        BidService bidService = new BidService();

        try {
            /* ================= CLIENT ================= */
            System.out.println("=== CLIENT CRUD ===");

            Client client;
            try {
                client = new Client(2, "Aizhan", "Zhanabayeva", "aizhan@mail.com", LocalDate.now());
                clientService.create(client);
                System.out.println("Client created");
            } catch (DuplicateResourceException e) {
                client = clientService.getAll().stream()
                        .filter(c -> c.getEmail().equals("aizhan@mail.com") || c.getEmail().equals("aizhan.zh@mail.com"))
                        .findFirst()
                        .orElse(clientService.getAll().get(0));
                System.out.println("Client already exists, using existing one");
            }

            try {
                client.setEmail("aizhan.zh@mail.com");
                clientService.update(client.getId(), client);
                System.out.println("Client updated");
            } catch (Exception e) {
                System.out.println("Could not update client: " + e.getMessage());
            }

            /* ================= FREELANCER ================= */
            System.out.println("\n=== FREELANCER CRUD ===");

            Freelancer freelancer;
            try {
                freelancer = new Freelancer(
                        2,
                        "Bekzhan",
                        "Maqsat",
                        "bekzhan@mail.com",
                        4.0,
                        LocalDate.now()
                );
                freelancerService.create(freelancer);
                System.out.println("Freelancer created");
            } catch (DuplicateResourceException e) {
                freelancer = freelancerService.getAll().get(0);
                System.out.println("Freelancer already exists, using existing one");
            }

            freelancer.setRating(4.9);
            freelancerService.update(freelancer.getId(), freelancer);
            System.out.println("Freelancer updated");

            /* ================= PROJECT ================= */
            System.out.println("\n=== PROJECT CRUD ===");

            Project project = new Project(
                    2,
                    "Website Deploy",
                    2000,
                    LocalDate.now(),
                    client
            );
            projectService.create(project);
            System.out.println("Project created");

            List<Project> projects = projectService.getAll();
            Project existingProject = projects.get(2);

            existingProject.setBudget(2000);
            projectService.update(existingProject.getId(), existingProject);
            System.out.println("Project updated");

            /* ================= BID ================= */
            System.out.println("\n=== BID CRUD + BUSINESS RULE ===");

            Bid bid = new Bid(
                    0,
                    existingProject,
                    freelancer,
                    1400,
                    LocalDate.now()
            );

            bidService.create(bid);
            System.out.println("Bid created");

            try {
                bidService.create(bid);
            } catch (DuplicateResourceException e) {
                System.out.println("EXPECTED ERROR: " + e.getMessage());
            }

            /* ================= POLYMORPHISM ================= */
            System.out.println("\n=== POLYMORPHISM (BaseUser) ===");

            List<BaseUser> users = List.of(client, freelancer);
            users.forEach(BaseUser::printInfo);

            System.out.println("\n=== POLYMORPHISM (Payable) ===");

            List<Payable> payables = List.of(existingProject, bid);
            payables.forEach(p ->
                    System.out.println("Amount: " + p.getAmount())
            );

            /* ================= VALIDATION ================= */
            System.out.println("\n=== VALIDATION ERROR DEMO ===");

            try {
                Project badProject = new Project(
                        0,
                        "",
                        -500,
                        LocalDate.now(),
                        client
                );
                projectService.create(badProject);
            } catch (InvalidInputException e) {
                System.out.println("EXPECTED ERROR: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("UNEXPECTED ERROR: " + e.getMessage());
        }
    }
}
