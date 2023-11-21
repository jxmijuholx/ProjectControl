package hh.soft03.ProjectControl.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hh.soft03.ProjectControl.domain.AppUser;
import hh.soft03.ProjectControl.domain.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("user") AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        // Set the default role as USER
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        // Encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save user to the database
        userRepository.save(user);
        return "redirect:/formlogin";
    }
}

