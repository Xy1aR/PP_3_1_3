package ru.kata.spring.boot_security.demo.controllers;

import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NotFoundException;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        model.addAttribute("usersList", userService.findAllUsers());
        return "users";
    }

    @GetMapping(value = "/new")
    public String addUser(@ModelAttribute("user") User user,
                          ModelMap model) {
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("roles", roleService.findAllRoles());
        return "new-user";
    }

    @PostMapping(value = "/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam("id") Long id,
                           ModelMap model) throws NotFoundException {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAllRoles());
        return "edit-user";
    }

    @PatchMapping(value = "/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping()
    public String removeUser(@RequestParam("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

}
