package com.bonniewhy.thechorewheel.controllers;

import com.bonniewhy.thechorewheel.models.Room;
import com.bonniewhy.thechorewheel.models.data.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("room")
public class RoomController {

    @Autowired
    RoomDao roomDao;

    // LIST ALL ROOMS
    // [ ] TODO: Only show the rooms associated with the logged in ID number.
    // [ ] TODO: Get it to show up as checkboxes that are checked if the room is added to their "house".
    // [ ] TODO: Grey out the ones that do not apply. AKA when someone's done all the tasks associated with that room, it'll grey these out.
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String editRooms(Model model) {

        model.addAttribute(new Room());
        model.addAttribute("rooms", roomDao.findAll());
        model.addAttribute("title", "My House");

        return "rooms/index";

    }

    // [ ] TODO: Save the checked boxes from the initial view if / when the user presses the "Edit" button.
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String editRooms(Model model, @ModelAttribute @Valid Room room, Errors errors) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "My House");

            return "rooms/index";

        }

        roomDao.save(room);
        return "redirect:";

    }

    // [ ] TODO: Make sure to add a blocker for non-admin users to this route.
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addRooms(Model model) {

        model.addAttribute(new Room());

        return "rooms/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddRooms(Model model, @ModelAttribute @Valid Room newRoom, Errors errors) {

        if (errors.hasErrors()) {

            return "room/add";

        }

        roomDao.save(newRoom);
        return "redirect:/room/add";

    }

}
