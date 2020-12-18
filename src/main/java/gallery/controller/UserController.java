package gallery.controller;

import gallery.model.User;
import gallery.service.SecurityService;
import gallery.service.UserService;
import gallery.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        //model.addAttribute("param", false);
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (userService.findByEmail(userForm.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с данной почтой уже зарегистрирован");
            return "registration";
         }
        if (bindingResult.hasErrors()) {
            String field =  bindingResult.getFieldError().getField();
            if (field.equals("login")){
                model.addAttribute("error", "Логин занят");
            }
            else if (field.equals("passwordConfirm")){
                model.addAttribute("error", "Пароли не совпадают");
            }
            else if (field.equals("password")){
                model.addAttribute("error","Пароль слишком слабый или длинный");
            }
            return "registration";
        }

        userService.save(userForm);

        //securityService.autologin(userForm.getLogin(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }


}
