package com.bonniewhy.thechorewheel.controllers;

import com.bonniewhy.thechorewheel.controllers.service.UserContext;
import com.bonniewhy.thechorewheel.models.User;
import com.bonniewhy.thechorewheel.models.data.RoomDao;
import com.bonniewhy.thechorewheel.models.data.TaskDao;
import com.bonniewhy.thechorewheel.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private RoomDao roomDao;

    @Resource(name="authenticationManager")
    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;



    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public String displaySignIn(Model model) {

        model.addAttribute("title", "Sign In");
        model.addAttribute(new User());

        return "user/signin";

    }

//    @RequestMapping(value = "signin", method = RequestMethod.POST)
//    public String processSignIn(Model model, @ModelAttribute @Valid User user, Errors errors) {
//
//        // [X] TODO: How do you use Spring to sign someone in? A: It's automatic.
//
//    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String displaySignUp(Model model) {

        model.addAttribute("title", "Sign Up");
        model.addAttribute(new User());

        return "user/signup";

    }

    // [ ] TODO: Write a setCurrentUser method to log in the newly registered user automatically.
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String processSignUp(Model model, HttpServletRequest request, @ModelAttribute @Valid User user, Errors errors) {

        if (userDao.findByUsername(user.getUsername()) != null) {

            model.addAttribute("title", "Sign Up");
            return "user/signup";

        }

        if (errors.hasErrors()) {

            model.addAttribute("title", "Sign Up");
            return "user/signup";

        }

        if (!user.getPassword().equals(user.getVerifyPassword())) {

            model.addAttribute("title", "Sign Up");
            return "user/signup";

        }

        user.setPassword(user.getPassword());
        userDao.save(user);
//        user.setCurrentUser(user);

        return "redirect:";

    }

    @RequestMapping(value = "signout")
    public String signout() {
        return "redirect:/";
    }

    @RequestMapping(value = "account/{userId}", method = RequestMethod.GET)
    public String viewUserAccount(Model model, @PathVariable int userId) {

        model.addAttribute("title", "Your Account");
        model.addAttribute("user", userDao.findOne(userId));

        return "user/account";

    }

}
