package hh.soft03.ProjectControl.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.soft03.ProjectControl.domain.AppUser;
import hh.soft03.ProjectControl.domain.Project;
import hh.soft03.ProjectControl.domain.ProjectRepository;
import hh.soft03.ProjectControl.domain.Role;
import hh.soft03.ProjectControl.domain.RoleRepository;
import hh.soft03.ProjectControl.domain.Task;
import hh.soft03.ProjectControl.domain.TaskRepository;
import hh.soft03.ProjectControl.domain.UserRepository;


@Controller
public class ProjectController<User> {

	@Autowired
	private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/projects")
    public String booklist(Model model) {
		
        model.addAttribute("projects", projectRepository.findAll());
        return "projects";
    }
	
	// Display the "Add Project" page
	@GetMapping("/addproject")
	public String showAddProjectPage(Model model) {
	    model.addAttribute("newProject", new Project()); 
	    model.addAttribute("projects", projectRepository.findAll());
	    return "addproject";
	}


    // Create a new project
    @PostMapping("/addproject")
    public String createProject(@ModelAttribute("newProject") Project newProject) { 
        // Save the project to the database
        projectRepository.save(newProject);
        // Redirect to the projects listing page or any other appropriate page
        return "redirect:/projects";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
public String deleteStudent(@PathVariable("id") Long id, Model model) {
    projectRepository.deleteById(id);
    return "redirect:/projects";
}

    

 // Display the edit project page
// Controller method to display the editproject page
 @RequestMapping("/Users/edit/{id}")
 public String editUser(@PathVariable Long id, Model model) {
     // Fetch the user, allTasks, allProjects, and allRoles
     AppUser appUser = userRepository.findById(id).orElse(null);
     List<Task> allTasks = (List<Task>) taskRepository.findAll();
     List<Project> allProjects = (List<Project>) projectRepository.findAll();
     List<Role> allRoles = (List<Role>) roleRepository.findAll();

     model.addAttribute("user", appUser);
     model.addAttribute("allTasks", allTasks);
     model.addAttribute("allProjects", allProjects);
     model.addAttribute("allRoles", allRoles);

     return "edituser"; 
 }

 @GetMapping("/editproject/{id}")
    public String showEditProjectPage(@PathVariable Long id, Model model) {
        // Fetch the project by its id
        Project project = projectRepository.findById(id).orElse(null);
        // Set the project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "editproject";
    }

    @PostMapping("/editproject")
    public String editProject(@ModelAttribute Project project) {
        // Check if the user is a new user (not yet saved)
        if (project.getUser() != null && project.getUser().getId() == null) {
            // Save the new user before saving the project
            userRepository.save(project.getUser());
        }
    
        // Now you can save the project
        projectRepository.save(project);
    
        // Your logic for handling the edit
    
        return "redirect:/projects";
    }
}