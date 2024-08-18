package com.revature.services;

import com.revature.DAOs.AuthDAO;
import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AuthDAO aDAO;

    @Autowired
    public AuthService(AuthDAO aDAO) {
        this.aDAO = aDAO;
    }

    public OutgoingUserDTO userLogin(LoginDTO lDTO, HttpSession session) throws Exception {

        User userObj = aDAO.findByUsernameAndPassword(lDTO.getUsername(), lDTO.getPassword());

        if (userObj != null) {
            OutgoingUserDTO outBoundUser = new OutgoingUserDTO(
                    userObj.getUserId(), userObj.getFirstName(), userObj.getLastName(),
                    userObj.getUsername(), userObj.getRole());

            session.setAttribute("userId", userObj.getUserId());
            session.setAttribute("firstName", userObj.getFirstName());
            session.setAttribute("lastName", userObj.getLastName());
            session.setAttribute("username", userObj.getUsername());
            session.setAttribute("role", userObj.getRole());

            return outBoundUser;
        } else {
            throw new Exception("Unable to find User");
        }
    }
}
