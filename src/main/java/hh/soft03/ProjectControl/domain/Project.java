package hh.soft03.ProjectControl.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private String startDate;
	private String endDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Task> tasks = new ArrayList<>();
    
    public Project() {
    	
    }

	public Project( String name, String description, String startDate, String endDate, AppUser user,
			List<Task> tasks) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		if (user != null && user.getId() == null) {
			// Set the user field to null so that a new user can be created
			this.user = null;
		}
	
		// Handle the case where tasks are empty
		if (tasks != null && tasks.isEmpty()) {
			// Set the tasks field to null
			this.tasks = null;
		}
	}



	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public AppUser getUser() {
		return user;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", user=" + user + ", tasks=" + tasks + "]";
	}
 
	
}
