package hh.soft03.ProjectControl.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/formlogin")
    public String index(Model model) {
        return "formlogin";
    }
}
