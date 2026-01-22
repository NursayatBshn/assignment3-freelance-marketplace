package controller;

import model.Bid;
import model.Client;
import model.Freelancer;
import model.Project;
import service.BidService;
import service.ProjectService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        ProjectService projectService = new ProjectService();
        BidService bidService = new BidService();

        Client client = new Client(
                1,
                "Айдос",
                "Жанабаев",
                "aidos@mail.com",
                LocalDate.of(2024, 1, 10)
        );

        Freelancer freelancer = new Freelancer(
                1,
                "Бекзат",
                "Ибраев",
                "bekzat@mail.com",
                4.8,
                LocalDate.of(2023, 11, 1),
                "+77010000001"
        );

        Project project = new Project(
                0,
                "Website Landing Page",
                1200,
                LocalDate.now(),
                client
        );

        try {
            projectService.create(project);
            System.out.println("Project created successfully");
        } catch (Exception e) {
            System.out.println("ERROR creating project: " + e.getMessage());
        }

        Project invalidProject = new Project(
                0,
                "Broken Project",
                -500,
                LocalDate.now(),
                client
        );

        try {
            projectService.create(invalidProject);
        } catch (Exception e) {
            System.out.println("EXPECTED PROJECT ERROR: " + e.getMessage());
        }

        System.out.println("\nAll projects:");
        projectService.getAll().forEach(p ->
                System.out.println(p.getTitle() + " | " + p.getBudget())
        );

        Project projectFromDb = projectService.getAll().get(0);

        Bid bid1 = new Bid(
                0,
                projectFromDb,
                freelancer,
                900,
                LocalDate.now()
        );

        try {
            bidService.create(bid1);
            System.out.println("Bid created successfully");
        } catch (Exception e) {
            System.out.println("ERROR creating bid: " + e.getMessage());
        }

        Bid bid2 = new Bid(
                0,
                project,
                freelancer,
                850,
                LocalDate.now()
        );

        try {
            bidService.create(bid2);
        } catch (Exception e) {
            System.out.println("EXPECTED DUPLICATE BID ERROR: " + e.getMessage());
        }
    }
}
