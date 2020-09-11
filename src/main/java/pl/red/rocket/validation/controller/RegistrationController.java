package pl.red.rocket.validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.red.rocket.validation.dto.RegistrationForm;

import javax.validation.Valid;

@Controller
public class RegistrationController {


    @Autowired
    MessageSource messageSource;

    @GetMapping("/")
    public ModelAndView showForm() {
        return new ModelAndView("registration", "form", new RegistrationForm());
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/")
    public String processForm(@Valid @ModelAttribute("form") RegistrationForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return "redirect:/success";
    }
}
