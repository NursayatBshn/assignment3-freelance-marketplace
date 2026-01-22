package service;

import exception.InvalidInputException;
import model.Project;
import repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository repository = new ProjectRepository();

    public void create(Project project) {
        project.validate();
        repository.create(project);
    }

    public List<Project> getAll() {
        return repository.getAll();
    }
}
