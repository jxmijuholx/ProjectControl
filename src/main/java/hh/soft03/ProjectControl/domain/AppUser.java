package hh.soft03.ProjectControl.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

@OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
private List<Task> tasks;


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
private List<Project> projects;


	public AppUser(List<String> roles) {
		this.roles = roles;
	}

	public AppUser(String username, String password, String firstName, String lastName, List<String> roles) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
	}

	public AppUser() {
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<String> getRoles() {
		return roles;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "AppUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", roles=" + roles +
				", tasks=" + tasks +
				", projects=" + projects +
				'}';
	}
}
