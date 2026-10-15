package com.recipe.myrecipe.controllers;

import com.recipe.myrecipe.entity.Admin;
import com.recipe.myrecipe.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {

        try {

            Admin savedAdmin = adminService.createAdmin(admin);

            savedAdmin.setPassword(null);

            return ResponseEntity.ok(savedAdmin);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {

            Admin admin = adminService.login(
                    request.getEmail(),
                    request.getPassword()
            );

            admin.setPassword(null);

            return ResponseEntity.ok(admin);

        } catch (RuntimeException e) {

            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }

    public static class LoginRequest {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}