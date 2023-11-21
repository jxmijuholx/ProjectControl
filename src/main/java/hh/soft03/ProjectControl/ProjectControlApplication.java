package hh.soft03.ProjectControl;

import hh.soft03.ProjectControl.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"hh.soft03.ProjectControl", "hh.soft03.ProjectControl.web"})
public class ProjectControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectControlApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public CommandLineRunner demoData(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            TaskRepository taskRepository,
            PasswordEncoder passwordEncoder) {

        return (args) -> {
            // Create and save "ADMIN" role if not exists
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ADMIN");
                roleRepository.save(adminRole);
            }

            // Create and save admin user with "ADMIN" role
            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);

            AppUser adminUser = new AppUser("admin", passwordEncoder.encode("admin"), "Admin", "User",Collections.singletonList("ADMIN"));
            userRepository.save(adminUser);

            // Create and save project
            Project testProject = new Project("ProjectControl", "Project management application", "01.10.2023", "31.12.2023", null, null);
            projectRepository.save(testProject);

            // Create and save demo user without "ADMIN" role
            List<Role> demoRoles = new ArrayList<>();
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new Role("USER");
                roleRepository.save(userRole);
            }
            demoRoles.add(userRole);

            AppUser demoUser = new AppUser("demo_user", passwordEncoder.encode("password"), "Demo", "User", Collections.singletonList("USER"));
            userRepository.save(demoUser);

            // Create and save task associated with demo user and project
            Task task = new Task("Sample Task", "Task description", "High", "31.12.2023", "Open", demoUser, testProject);
            taskRepository.save(task);

            // Set task in the project
            testProject.setTasks(Arrays.asList(task));
            projectRepository.save(testProject);
        };
    }

}
