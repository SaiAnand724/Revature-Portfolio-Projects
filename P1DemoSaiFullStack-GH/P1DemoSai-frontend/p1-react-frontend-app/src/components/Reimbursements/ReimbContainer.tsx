import React, { useEffect, useState } from "react"
import { ReimbInterface } from "../../interfaces/ReimbInterface"
import { useNavigate } from "react-router-dom"
import axios, { AxiosResponse } from "axios"
import { Reimbursements } from "./Reimbursements"
import { Button, Container, Nav, Navbar } from "react-bootstrap"
import { store } from "../globalStore/store"



export const ReimbContainer: React.FC = () => {
    // Base URL for Reimbursements
    const reimbBaseUrl = "http://localhost:8080/reimbursements"

    // Store state of Reimbursement object array using ReimbInterface
    const [reimbList, setReimbList] = useState<ReimbInterface[]>([])

    const role_obj:String = store.loggedInUser.role // <- insert get user role logic

    const navigate = useNavigate()

    // Get Reimbursements when component is rendered - useEffect
    // Check roles
    useEffect(() => {
        const sortedReimbursements = reimbList.sort();
        setReimbList(sortedReimbursements);
        getAllReimbursements()
        console.log("Logged In userID: ", store.loggedInUser.userId)
        console.log("Logged In user role: ", role_obj)
    }, []) //<- insert 'showPending' into [] 

    const getAllReimbursements = async () => {
        try {


            let response:any = null;
            // GET request for Reimbursements list
            if (role_obj === "Employee"){
                response = await axios.get(reimbBaseUrl + `/user/${store.loggedInUser.userId}`)
            }
            else if (role_obj === "Manager"){
                response = await axios.get(reimbBaseUrl)
            }


            //const response = await axios.get(reimbBaseUrl)

            response.data.sort((a: { reimbId: number }, b: { reimbId: number }) => a.reimbId - b.reimbId);

            // Populate Reimbursement state
            setReimbList(response.data)

            console.log(response.data)



        }
        catch {
            console.log("Error getting Reimbursements")
        }
    }

    const getPendingReimbursements = async () => {
        try {


            let response: any = null;

            if (role_obj === "Employee") {
                response = await axios.get(reimbBaseUrl + `/user/${store.loggedInUser.userId}/PENDING`)
            } else if (role_obj === "Manager") {
                response = await axios.get(reimbBaseUrl + "/PENDING")
            }



            //const response = await axios.get(reimbBaseUrl + "/PENDING")

            response.data.sort((a: { reimbId: number }, b: { reimbId: number }) => a.reimbId - b.reimbId);

            setReimbList(response.data)

            console.log(response.data)

            //alert("Pending Reimbursements are showing")
        } catch (error) {
            console.log("Error getting pending Reimbursements", error)
        }
    }

    const getApprovedReimbursements = async () => {
        try {

            //if (role_obj === 'Manager') {}
            const response = await axios.get(reimbBaseUrl + "/APPROVED")

            response.data.sort((a: { reimbId: number }, b: { reimbId: number }) => a.reimbId - b.reimbId);

            setReimbList(response.data)

            console.log(response.data)

            //alert("Approved Reimbursements are showing")
        } catch (error) {
            console.log("Error getting approved Reimbursements", error)
        }
    }

    const getDeniedReimbursements = async () => {
        try {

            //if (role_obj === 'Manager') {}


            const response = await axios.get(reimbBaseUrl + "/DENIED")

            response.data.sort((a: { reimbId: number }, b: { reimbId: number }) => a.reimbId - b.reimbId);

            setReimbList(response.data)
            //setShowPending(true)
            console.log(response.data)

            //alert("Denied Reimbursements are showing")
        } catch (error) {
            console.log("Error getting denied Reimbursements", error)
        }
    }
  
    
    return (
        <div className="container">  
            <Navbar bg="dark" data-bs-theme="dark">
                <Container fluid>
                    <Nav>
                    {store.loggedIn === true ? (
                            <>
                            <Button className="me-2" onClick={() => navigate("/")}>Logout</Button>
                            </>
                        ) : <></>} 
                        <Button className="me-2" onClick={() => navigate("/addReimbursement")}>Add New Reimbursement</Button>
                        {role_obj === "Manager"? (
                            <>
                            <Button className="me-2" onClick={() => navigate("/users")}>Users</Button>
                            </>
                        ) : <></>}
                    </Nav>
                    <Nav className="ms-auto">
                    {store.loggedIn === true ? (
                            <>
                            <Button onClick={() => navigate("/userProfile")}>Profile</Button>
                            </>
                        ) : <></>}  
                    </Nav>
                </Container>
            </Navbar>

            <div>
                <Reimbursements reimbursements={reimbList}></Reimbursements>
            </div>
            
            <div>
                <Button className="ml-3" onClick={() => getAllReimbursements()}>Show All Reimbursements</Button>
                <Button className="mx-3" onClick={() => getPendingReimbursements()}>Show Pending Reimbursements</Button>
                

                {role_obj === "Manager"? (
                    <>
                    <Button className="ml-3" onClick={() => getApprovedReimbursements()}>Show Approved Reimbursements</Button>
                    <Button className="mx-3" onClick={() => getDeniedReimbursements()}>Show Denied Reimbursements</Button>
                    </>
                ) : <></>}
            </div>
        </div>
    )
}