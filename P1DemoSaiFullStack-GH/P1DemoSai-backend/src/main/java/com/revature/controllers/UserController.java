package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService us) {
        this.userService = us;
    }

    // Method to get a List of all Users in DB
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> usersList = userService.getAllUsers();

        return ResponseEntity.ok(usersList);
    }

    // Method to get a List of Users by their role: 'Employee' or 'Manager'
    @GetMapping("/{role}")
    public ResponseEntity<List<User>> getAllUsersByRole(@PathVariable String role){
        List<User> usersList = userService.getUsersByRole(role);

        return ResponseEntity.ok(usersList);

    }

    // Method to create a User/account with default role of 'Employee'
    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User newUser) {
        try {
            User addedUser = userService.registerNewUser(newUser);
            return ResponseEntity.status(201).body(addedUser);
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to delete a User
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteReimbursement(@PathVariable int userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID: " + userId + " was deleted");
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to update a User's username
    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUsername(@PathVariable int userId, @RequestBody String newUsername){
        try {
            return ResponseEntity.ok(userService.updateUsername(userId, newUsername));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to update a User's role
    @PatchMapping("/{userId}/{newRole}")
    public ResponseEntity<?> updateRole(@PathVariable int userId, @PathVariable String newRole){
        try {
            return ResponseEntity.ok(userService.updateUserRole(userId, newRole));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
