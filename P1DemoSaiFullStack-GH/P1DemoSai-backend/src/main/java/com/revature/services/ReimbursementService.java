package com.revature.services;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    private ReimbursementDAO reimbDAO;
    private UserDAO userDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbDAO, UserDAO userDAO) {
        this.reimbDAO = reimbDAO;
        this.userDAO = userDAO;
    }

    // Method to get the list of Reimbursements of a User by Id -> Sends to controller
    public List<Reimbursement> getReimbursementsByUserId(int userId) throws Exception {
        if (userDAO.existsById(userId)){
            return reimbDAO.findByUserUserId(userId);
        }
        throw new Exception("User does not exist");
    }

    // Method to get the list of Reimbursements by status for a User by Id ->
    // Status should be 'PENDING', 'APPROVED', or 'DENIED'
    public List<Reimbursement> getReimbursementsByStatusForUser(int userId, String status) throws Exception {
        if (userDAO.existsById(userId)){
            // userDAO.findByUserId(userId) == null;
            return reimbDAO.findByUserUserIdAndStatus(userId, status);
        }
        throw new Exception("User does not exist");
    }

    // Method to add a Reimbursement using data from the controller -> Send added Reimbursement back to controller
    public Reimbursement insertReimbursement(IncomingReimbursementDTO newReimbursement){
        Reimbursement modelReimbursement = new Reimbursement(0, newReimbursement.getDescription(),
                                            newReimbursement.getAmount(), newReimbursement.getStatus(), null);

        Optional<User> modelUser = userDAO.findById(newReimbursement.getUserId());

        if (modelUser.isPresent()){
            modelReimbursement.setUser(modelUser.get());
            Reimbursement addedReimbursement = reimbDAO.save(modelReimbursement);
            return addedReimbursement;
        } else {
            return null;
        }
    }

    // Method to update description of a Reimbursement -> Send back to controller
    public Reimbursement updateReimbDescripAmountById(int reimbId, String newDescription, double newAmount){
        Optional<Reimbursement> modelReimbursement = reimbDAO.findById(reimbId);

        if (modelReimbursement.isPresent()){
            Reimbursement updatedReimbursement = modelReimbursement.get();
            updatedReimbursement.setDescription(newDescription);
            updatedReimbursement.setAmount(newAmount);
            return reimbDAO.save(updatedReimbursement);
        } else {
            return null; // Throw exception?
        }
    }

    // ---------- Manager Functions ----------

    // ** Manager **
    // Method to get a list of all Reimbursements -> Sends to controller
    public List<Reimbursement> getAllReimbursements() {
        return reimbDAO.findAll();
    }

    // ** Manager **
    // Method to get the list of Reimbursements by status -> Sends to controller
    public List<Reimbursement> getReimbursementsByStatus(String status){
        return reimbDAO.findByStatus(status);
    }

    // ** Manager **
    // Method to delete a Reimbursement and its reference to a User
    public void deleteReimbursementById(int reimbId) throws Exception {
        Optional<Reimbursement> optionalReimbursement = reimbDAO.findById(reimbId);

        if (optionalReimbursement.isPresent()){
            Reimbursement modelReimbursement = optionalReimbursement.get();
            modelReimbursement.getUser().getReimbursements().remove(modelReimbursement);
            reimbDAO.deleteById(reimbId);
        } else {
            throw new Exception("Reimbursement does not exist for ID: " + reimbId);
        }
    }

    // ** Manager **
    // Method to update/resolve status of a Reimbursement -> Send back to controller
    public Reimbursement resolveReimbursementById (int reimbId, String newStatus) throws Exception{
        Optional<Reimbursement> modelReimbursement = reimbDAO.findById(reimbId);

        if (modelReimbursement.isPresent()){
            Reimbursement updatedReimbursement = modelReimbursement.get();
            updatedReimbursement.setStatus(newStatus);
            return reimbDAO.save(updatedReimbursement);
        } else {
            throw new Exception("Unable to resolve Reimbursement status"); // Throw exception?
        }
    }

}
