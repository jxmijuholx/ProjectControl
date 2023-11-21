package hh.soft03.ProjectControl.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    
	// Etsi käyttäjä käyttäjänimen perusteella
    AppUser findByUsername(String username);

    // Etsi käyttäjä etu- ja sukunimen perusteella
    List<AppUser> findByFirstNameAndLastName(String firstName, String lastName);

    AppUser findUserById(Long id);

    AppUser getUserById(Long id);
}

