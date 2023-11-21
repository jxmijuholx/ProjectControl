package hh.soft03.ProjectControl.web;

import hh.soft03.ProjectControl.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Näytä task-lista projektille
    @GetMapping("/tasklist/{projectId}")
    public String viewTaskList(@PathVariable Long projectId, Model model) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            List<Task> tasks = project.getTasks();
            model.addAttribute("tasks", tasks);
            model.addAttribute("project", project);
            return "tasklist";
        } else {
            // Käsittele tilanne, kun projektia ei löydy
            return "redirect:/projects";
        }
    }

    // Lisää uusi task projektille
    // Näytä lomake uuden taskin luomiseksi projektille
    @GetMapping("/createtask/{projectId}")
    public String createTaskForm(@PathVariable Long projectId, Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        // Lisää alla käyttäjät tarpeen mukaan modeliin
        List<AppUser> users = (List<AppUser>) userRepository.findAll(); // Käyttäjien haku
        model.addAttribute("users", users);
        return "createtask"; // Luo tämä sivu (näytä lomake)
    }

    @PostMapping("/createtask/{projectId}")
    public String createTask(@ModelAttribute Task task, @PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            task.setProject(project);
            taskRepository.save(task);
        }

        return "redirect:/tasklist/" + projectId;
    }

    // Muokkaa taskia projektille
    @GetMapping("/edittask/{taskId}")
    public String editTaskForm(@PathVariable Long taskId, Model model) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("task", task);
            return "edittask";
        }
        return "redirect:/tasklist";
    }

    @PostMapping("/edittask/{taskId}")
    public String editTask(@PathVariable Long taskId, @ModelAttribute Task updatedTask) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setPriority(updatedTask.getPriority());
            task.setDeadline(updatedTask.getDeadline());
            task.setStatus(updatedTask.getStatus());
            taskRepository.save(task);
        }
        return "redirect:/tasklist/" + task.getProject().getId(); // Käytä taskin projektin ID:ta
    }
    // Poista task projektilta
    @GetMapping("/deletetask/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        }
        // Ohjaa takaisin task-listan sivulle
        return "redirect:/tasklist/" + task.getProject().getId();
    }
}
