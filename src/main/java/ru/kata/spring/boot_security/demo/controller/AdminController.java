package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;

    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    // Отображение админ-панели
    @GetMapping({"", "/"})
    public String showAdminPanel(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("currentUser", adminService.getCurrentUser());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/admin";
    }

    // Создание пользователя
    @PostMapping("/new-user")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesSelected", required = false) List<Long> rolesSelected) {
        adminService.registerUser(user, rolesSelected);
        return "redirect:/admin";
    }

    // Форма обновления пользователя
    @GetMapping("/update/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = adminService.getUserById(id);
        if (user == null) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/update-user";
    }

    // Обновление пользователя
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User updatedUser,
                             @RequestParam(value = "rolesSelected", required = false) List<Long> rolesSelected) {
        adminService.updateUser(updatedUser, rolesSelected);
        return "redirect:/admin";
    }

    // Удаление пользователя
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUser(id);
        return "redirect:/admin";
    }
}