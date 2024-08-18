import { useEffect, useState } from "react"
import { UserInterface } from "../../interfaces/UserInterface"
import { useNavigate } from "react-router-dom"
import axios, { AxiosResponse } from "axios"
import { Users } from "./Users"
import { Button, Container, Nav, Navbar } from "react-bootstrap"
import { store } from "../globalStore/store"



export const UserContainer: React.FC = () => {
    // Base URL for Users
    const usersBaseUrl = "http://localhost:8080/users"

    // Store state of Reimbursement object array using ReimbInterface
    const [usersList, setUsersList] = useState<UserInterface[]>([])

    const navigate = useNavigate()

    // Get Users when component is rendered - useEffect
    useEffect(() => {
        getAllUsers()
    }, [])

    const getAllUsers = async () => {
        try {
            // GET request for Users list
            const response = await axios.get(usersBaseUrl)

            response.data.sort((a: { userId: number }, b: { userId: number }) => a.userId - b.userId);

            // Populate Users state
            setUsersList(response.data)

            console.log(response.data)
        }
        catch {
            console.log("Error getting Users")
        }
    }

    const getEmployees = async () => {
        try {

            const response = await axios.get(usersBaseUrl + "/Employee")

            response.data.sort((a: { userId: number }, b: { userId: number }) => a.userId - b.userId);

            setUsersList(response.data)
            console.log(response.data)

            //alert("Employee list is showing")
        } catch (error) {
            console.log("Error getting roles list: ", error)
        }
    }

    const getManagers = async () => {
        try {


            const response = await axios.get(usersBaseUrl + "/Manager")            
            
            response.data.sort((a: { userId: number }, b: { userId: number }) => a.userId - b.userId);

            setUsersList(response.data)
            console.log(response.data)

            //alert("Manager list is showing")
        } catch (error) {
            console.log("Error getting roles list: ", error)
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
                    <Button className="me-2" onClick={() => navigate("/reimbursements")}>Reimbursements</Button>
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
                <Users users={usersList}></Users>
            </div>
            
            <div>
                <Button className="mx-3" onClick={() => getAllUsers()}>Show All Users</Button>
                <Button className="mx-3" onClick={() => getEmployees()}>Show Employees</Button>
                <Button className="mx-3" onClick={() => getManagers()}>Show Managers</Button>
            </div>
        </div>
    )
}