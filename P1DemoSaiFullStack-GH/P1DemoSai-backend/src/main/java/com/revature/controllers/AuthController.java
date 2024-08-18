package com.revature.controllers;

import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class AuthController {
    private AuthService as;

    @Autowired
    public AuthController(AuthService as) {
        this.as = as;
    }

    @PostMapping
    public ResponseEntity<?> userLogin(@RequestBody LoginDTO lDTO, HttpSession session) throws Exception {

        OutgoingUserDTO loggedUserDet = as.userLogin(lDTO, session);

        if(loggedUserDet == null){
            return ResponseEntity.status(401).body("Invalid Login Credentials");
        }

        return ResponseEntity.accepted().body(loggedUserDet);

    }
}
