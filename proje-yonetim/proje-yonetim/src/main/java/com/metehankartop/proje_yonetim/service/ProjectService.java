package com.metehankartop.proje_yonetim.service;

import com.metehankartop.proje_yonetim.model.Employee;
import com.metehankartop.proje_yonetim.model.Project;
import com.metehankartop.proje_yonetim.repository.EmployeeRepository;
import com.metehankartop.proje_yonetim.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepo;
    private final EmployeeRepository employeeRepo;

    public ProjectService(ProjectRepository projectRepo, EmployeeRepository employeeRepo) {
        this.projectRepo = projectRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<Project> getAll() { return projectRepo.findAll(); }
    public Optional<Project> getById(Long id) { return projectRepo.findById(id); }
    public Project save(Project p) { return projectRepo.save(p); }
    public Project update(Long id, Project p) {
        p.setId(id);
        return projectRepo.save(p);
    }
    public void delete(Long id) { projectRepo.deleteById(id); }

    public Project assignEmployee(Long projectId, Long employeeId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı"));
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Çalışan bulunamadı"));

        project.getEmployees().add(employee);
        employee.getProjects().add(project);

        employeeRepo.save(employee);
        return projectRepo.save(project);
    }
    public Project removeEmployee(Long projectId, Long employeeId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı"));
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Çalışan bulunamadı"));

        project.getEmployees().remove(employee);
        employee.getProjects().remove(project);

        employeeRepo.save(employee);
        return projectRepo.save(project);
    }
}
