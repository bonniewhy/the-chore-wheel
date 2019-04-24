package com.bonniewhy.thechorewheel.controllers;

import com.bonniewhy.thechorewheel.models.Task;
import com.bonniewhy.thechorewheel.models.User;
import com.bonniewhy.thechorewheel.models.data.RoomDao;
import com.bonniewhy.thechorewheel.models.data.TaskDao;
import com.bonniewhy.thechorewheel.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "task")
public class TaskController {

    @Autowired
    RoomDao roomDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;

    User currentUser = User.getCurrentUser();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAllTasks(Model model) {

        model.addAttribute("title", "Tasks");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("tasks", taskDao.findAll());

        return "tasks/list-all";

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayAddNewTaskForm(Model model) {

        model.addAttribute("title", "Add Tasks");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("rooms", roomDao.findAll());
        model.addAttribute(new Task());

        return "tasks/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddNewTaskForm(Model model, @ModelAttribute @Valid Task newTask, Errors errors) {

        model.addAttribute("title", "List All Tasks");
        model.addAttribute("currentUser", currentUser);

        if (errors.hasErrors()) {

            return "tasks/add";
        }

        taskDao.save(newTask);

        return "redirect:/task/";

    }


}
