package com.revature.controllers;

import com.revature.models.DTOs.IncomingReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Float.parseFloat;

@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private ReimbursementService reimbService;
    private UserService userService;

    @Autowired
    public ReimbursementController(ReimbursementService rs, UserService us) {
        this.reimbService = rs;
        this.userService = us;
    }

    // Method to return all Reimbursements in HTTP Response
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(){
        return ResponseEntity.ok(reimbService.getAllReimbursements());
    }

    // Method to return all Reimbursements by status
    @GetMapping("/{status}")
    public ResponseEntity<List<Reimbursement>> getAllReimbursementsByStatus(@PathVariable String status){
        return ResponseEntity.ok(reimbService.getReimbursementsByStatus(status));
    }

    // Method to return all Reimbursements for a User
    @GetMapping("/user/{userId}")
    // Lizt<Reimbursements> is return type in try - status code and error message is in catch
    public ResponseEntity<?> getAllUserReimbursementsById(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(reimbService.getReimbursementsByUserId(userId));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to return all Reimbursements for a User by status
    @GetMapping("/user/{userId}/{status}")
    public ResponseEntity<?> getUserReimbursementsByStatus(@PathVariable int userId, @PathVariable String status){
        try {
            return ResponseEntity.ok(reimbService.getReimbursementsByStatusForUser(userId, status));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to create a new Reimbursement
    @PostMapping
    public ResponseEntity<?> insertNewReimbursement(@RequestBody IncomingReimbursementDTO newReimbursement){
        Reimbursement addedReimbursement = reimbService.insertReimbursement(newReimbursement);
        if (addedReimbursement == null) {
            return ResponseEntity.status(400).body("Unable to find User with ID: " + newReimbursement.getUserId());
        }
        return ResponseEntity.status(201).body(addedReimbursement);
    }

    // Method to update/resolve status for a Reimbursement
    @PatchMapping("/resolve/{reimbId}/{newStatus}")
    public ResponseEntity<?> resolveReimbursement(@PathVariable int reimbId, @PathVariable String newStatus){
        try {
            return ResponseEntity.ok(reimbService.resolveReimbursementById(reimbId, newStatus));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to delete a Reimbursement
    @DeleteMapping("/{reimbId}")
    public ResponseEntity<?> deleteReimbursement(@PathVariable int reimbId){
        try {
            reimbService.deleteReimbursementById(reimbId);
            return ResponseEntity.ok("Reimbursement with ID: " + reimbId + " was deleted");
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Method to update description and amount for a Reimbursement
    @PatchMapping("/{reimbId}/{newAmount}")
    public ResponseEntity<?> updateReimbDescriptionAmount(@PathVariable int reimbId, @RequestBody String newDescription, @PathVariable Float newAmount){
        try {
            return ResponseEntity.ok(reimbService.updateReimbDescripAmountById(reimbId, newDescription, newAmount));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



}
