package hh.soft03.ProjectControl.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long>{
	 // Etsi projekti projektin nimen perusteella
    List<Project> findByName(String name);

    List<Project> getProjectsByUser(AppUser appUser);
}
