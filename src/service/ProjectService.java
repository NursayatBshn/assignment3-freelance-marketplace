package service;

import exception.InvalidInputException;
import model.Project;
import repository.interfaces.CrudRepository;
import utils.SortingUtils;

import java.util.List;

public class ProjectService {
    private final CrudRepository<Project> repository;

    public ProjectService(CrudRepository<Project> repository) {
        this.repository = repository;
    }

    public void create(Project project) {
        if (project == null) {
            throw new InvalidInputException("Project cannot be null");
        }

        project.logValidationStatus();
        project.validate();

        repository.create(project);
    }

    public List<Project> getAll() {
        List<Project> projects = repository.getAll();
        SortingUtils.sort(projects, (p1, p2) -> Double.compare(p2.getBudget(), p1.getBudget()));
        return projects;
    }

    public Project getById(int id) {
        return repository.getById(id);
    }

    public void update(int id, Project project) {
        project.validate();
        repository.update(id, project);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}