package hh.soft03.ProjectControl.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
	 // Etsi tehtävät, jotka on määritetty tietylle käyttäjälle
    List<Task> findByAssignedUser(AppUser user);

    // Etsi tehtävät tietyssä projektissa
    List<Task> findByProject(Project project);

    // Etsi tehtävät tietyllä prioriteetilla projektissa
    List<Task> findByProjectAndPriority(Project project, String priority);

    List<Task> getTasksByAssignedUser(AppUser appUser);
}

