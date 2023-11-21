package hh.soft03.ProjectControl.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private String priority;
	private String deadline;
	private String status;

	// Add the following relationship annotations
	@ManyToOne
@JoinColumn(name = "ASSIGNED_USER_ID")
private AppUser assignedUser;


	@ManyToMany
	@JoinTable(
			name = "task_user",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<AppUser> assignedUsers;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	public Task(String name, String description, String priority, String deadline, String status, AppUser assignedUser, Project project) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.deadline = deadline;
		this.status = status;
		this.assignedUser = assignedUser;
		this.project = project;
	}

	public Task() {

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

	public String getPriority() {
		return priority;
	}

	public String getDeadline() {
		return deadline;
	}

	public String getStatus() {
		return status;
	}

	public AppUser getAssignedUser() {
		return assignedUser;
	}

	public Project getProject() {
		return project;
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

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAssignedUser(AppUser assignedUser) {
		this.assignedUser = assignedUser;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", priority='" + priority + '\'' +
				", deadline='" + deadline + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
