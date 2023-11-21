package hh.soft03.ProjectControl.web;

import hh.soft03.ProjectControl.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppUserController {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @ModelAttribute("allTasks")
    public List<Task> allTasks() {
        return (List<Task>) taskRepository.findAll(); // Fetch all tasks
    }

    @ModelAttribute("allProjects")
    public List<Project> allProjects() {
        return (List<Project>) projectRepository.findAll(); // Fetch all projects
    }


    @GetMapping("/Users")
    public String listUsers(Model model) {
        Iterable<AppUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users"; // Luo näkymä "users.html" kaikille käyttäjille
    }


    @GetMapping("/Users/viewUser/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        // Fetch the user by ID
        AppUser appUser = userRepository.getUserById(id);
        // Fetch the user's projects and tasks
        List<Project> projects = projectRepository.getProjectsByUser(appUser);
        List<Task> tasks = taskRepository.getTasksByAssignedUser(appUser);

        // Add the data to the model
        model.addAttribute("appUser", appUser);
        model.addAttribute("projects", projects);
        model.addAttribute("tasks", tasks);

        return "viewuser";
    }


    @GetMapping("/Users/createuser")
    public String createUserForm(Model model) {
        AppUser newUser = new AppUser();
        newUser.setUsername("defaultUsername");
        model.addAttribute("user", new AppUser());

        return "createuser";
    }

    @PostMapping("/Users/createuser")
    public String createUser(@ModelAttribute AppUser user) {
        userRepository.save(user);
        return "redirect:/Users";
    }

    @GetMapping("/Users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        AppUser user = userRepository.findById(id).orElse(null);
        List<Role> allRoles = (List<Role>) roleRepository.findAll();  // Fetch all roles
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);  // Add roles to the model
        return "edituser";
    }

    @PostMapping("/Users/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") AppUser updatedUser) {
        AppUser user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Update other user details (e.g., username, first name, last name)
            user.setUsername(updatedUser.getUsername());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());

            // Update selected tasks and projects
            user.setTasks(updatedUser.getTasks());
            user.setProjects(updatedUser.getProjects());

            userRepository.save(user);
        }
        return "redirect:/Users";
    }


    @GetMapping("/Users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/Users";
    }
}
