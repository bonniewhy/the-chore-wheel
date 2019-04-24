package com.bonniewhy.thechorewheel.controllers;

import com.bonniewhy.thechorewheel.models.Room;
import com.bonniewhy.thechorewheel.models.Task;
import com.bonniewhy.thechorewheel.models.User;
import com.bonniewhy.thechorewheel.models.data.RoomDao;
import com.bonniewhy.thechorewheel.models.data.TaskDao;
import com.bonniewhy.thechorewheel.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAllTasks(Model model) {

        User currentUser = User.getCurrentUser();
        User modelUser = userDao.findOne(currentUser.getId());

        model.addAttribute("title", "Tasks");
        model.addAttribute("modelUser", modelUser);
        model.addAttribute("rooms", roomDao.findAll());
        model.addAttribute(new Task());

        return "tasks/list-all";

    }

    @RequestMapping(value = "list/{roomId}", method = RequestMethod.GET)
    public String listTasksByRoom(Model model, @PathVariable int roomId) {

        User currentUser = User.getCurrentUser();
        User modelUser = userDao.findOne(currentUser.getId());
        Room room = roomDao.findOne(roomId);

        model.addAttribute("title", "Tasks for " + room.getName());
        model.addAttribute("room", room);
        model.addAttribute("modelUser", modelUser);
        model.addAttribute(new Task());

        return "tasks/list-room";

    }

//    @RequestMapping(value = "add", method = RequestMethod.GET)
//    public String displayAddNewTaskForm(Model model) {
//
//        User currentUser = User.getCurrentUser();
//
//        model.addAttribute("title", "Add Tasks");
//        model.addAttribute("currentUser", currentUser);
//        model.addAttribute("rooms", roomDao.findAll());
//        model.addAttribute(new Task());
//
//        return "tasks/add";
//    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processAddNewTaskFormListAll(Model model, @ModelAttribute @Valid Task newTask, Errors errors, @RequestParam int roomId) {

        User currentUser = User.getCurrentUser();

        if (errors.hasErrors()) {
            model.addAttribute("title", "List All Tasks");
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("rooms", roomDao.findAll());

            return "redirect:/task/";
        }

        Room room = roomDao.findOne(roomId);
        newTask.setRoom(room);
        newTask.setUser(currentUser);
        taskDao.save(newTask);

        return "redirect:/task/";

    }

    @RequestMapping(value = "/list/{roomId}", method = RequestMethod.POST)
    public String processAddNewTaskFormListRoom(Model model, @ModelAttribute @Valid Task newTask, Errors errors, @RequestParam int formRoomId) {

        User currentUser = User.getCurrentUser();

        if (errors.hasErrors()) {
            model.addAttribute("title", "List All Tasks");
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("rooms", roomDao.findAll());

            return "redirect:/task/list/" + formRoomId;
        }

        Room room = roomDao.findOne(formRoomId);
        newTask.setRoom(room);
        newTask.setUser(currentUser);
        taskDao.save(newTask);

        return "redirect:/task/list/" + formRoomId;

    }


}
