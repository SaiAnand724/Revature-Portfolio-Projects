package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    // Method to add a new User by taking in a new User object and sending it to the UserDAO
    public User registerNewUser(User newUser) throws Exception{
        // Error handling for null newUser object - check for unique
        if (newUser != null) {
            if (userDAO.findByUsername(newUser.getUsername()) == null){
                return userDAO.save(newUser);
            } else {
                throw new Exception("Username is not unique");
                //return new UserNotFoundException(); // Throws an Exception?
            }
        } else {
            return null;
        }

    }

    // Method to update a User's username - must be unique
    public User updateUsername(int userId, String newUsername) throws Exception{
        Optional<User> selectedUser = userDAO.findById(userId);

        if(selectedUser.isPresent()){

            User user = selectedUser.get();

            if (userDAO.findByUsername(newUsername) == null){
                user.setUsername(newUsername);
                return userDAO.save(user);
            } else {
                throw new Exception("Username is not unique");
                //return new UserNotFoundException(); // Throws an Exception?
            }
        }
        else {
            return null;
        }

    }

    // ---------- Manager Functions ----------

    // ** Manager **
    // Method to get a list of Users -> Sends to controller
    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    // ** Manager **
    // Method to get a list of Users by their role
    public List<User> getUsersByRole(String role){
        if (role != null){
            return userDAO.findByRole(role);
        } else {
            return null;
        }
    }

    // ** Manager **
    // Method to update a User's role
    public User updateUserRole(int userId, String newRole) throws Exception{
        Optional<User> selectedUser = userDAO.findById(userId);

        if(selectedUser.isPresent()){

            User user = selectedUser.get();

            if (newRole != null){
                user.setRole(newRole);
                return userDAO.save(user);
            } else {
                throw new Exception("User's role is unable to be updated");
                //return new UserNotFoundException(); // Throws an Exception?
            }
        }
        else {
            return null;
        }

    }

    // ** Manager **
    // Method to delete a User by Id, throws custom Exception?
    public void deleteUser(int userId) throws Exception {
        Optional<User> selectedUser = userDAO.findById(userId);

        if(selectedUser.isPresent()){
            User user = selectedUser.get();

            List<Reimbursement> userReimbursements = user.getReimbursements();
            user.getReimbursements().removeAll(userReimbursements);

            userDAO.delete(user);
        } else {
            throw new Exception("Unable to delete user");
            //throw new UserDetailsNotFound(); // Throws an Exception?
        }

    }



}
