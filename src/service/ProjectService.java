package service;

import exception.InvalidInputException;
import model.Project;
import repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository repository = new ProjectRepository();

    public void create(Project project) {

        if (project == null) {
            throw new InvalidInputException("Project cannot be null");
        }

        project.validate(); // если реализован Validatable

        if (project.getClient() == null || project.getClient().getId() <= 0) {
            throw new InvalidInputException("Project must have a valid client");
        }

        repository.create(project);
    }


    public List<Project> getAll() {
        return repository.getAll();
    }
    public Project getById(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Project id must be positive");
        }

        return repository.getById(id);
    }
    public void update(int id, Project project) {

        if (id <= 0) {
            throw new InvalidInputException("Project id must be positive");
        }

        if (project == null) {
            throw new InvalidInputException("Project cannot be null");
        }

        project.validate();

        repository.update(id, project);
    }
    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Project id must be positive");
        }

        repository.delete(id);
    }

}