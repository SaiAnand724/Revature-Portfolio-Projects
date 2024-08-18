import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Card, Container, Form, FormControl, Nav, Navbar } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { store } from "../globalStore/store"

export const LoginPage: React.FC = () => {

    useEffect(() => {
        store.loggedIn = false
    }, [])

    const navigate = useNavigate()

    const[user, setUser] = useState({
        username:"",
        password:"",
        role:"Employee" //we want role to be Employee by default
    })

    const storeValues = (input:any) => {
        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        } else if (input.target.name === "password") {
            setUser((user) => ({...user, password:input.target.value}))
        }
    }

    const userLogin = async () => {
        try {
            const response = await axios.post("http://localhost:8080/login", user, {withCredentials:true})
            .then(
                (response) => {
    
                    //print the data
                    console.log(response.data)
    
                    //Save the incoming user data in our global state (store.ts in the globalData folder)
                    store.loggedInUser = response.data
    
                    alert("Welcome, " + store.loggedInUser.username)

                    store.loggedIn = true
    
                    //depending on the user's role value, send them to one of two components
                    if(response.data.role === "Employee"){
                        //useNavigate hook switches views to the Reimbursements Container Component
                        navigate("/reimbursements")
                    } 
    
                    if(response.data.role === "Manager"){
                        //useNavigate hook switches views to the Users Container Component
                        navigate("/users")
                    }
                    
                }
            )
            //alert("User login Button clicked")

        }
        catch (error) {
            alert("Error: {" + error + "}")
        }
    }

    return(
        <div className="login-page">
            <Navbar bg="dark" data-bs-theme="dark">
                <Container fluid>
                    <Nav>
                    <Button className="me-2" onClick={() => navigate("/registerUser")}>Register New User</Button>
                    </Nav>
                </Container>
            </Navbar>

            <Card>
                <Card.Header>
                    <h3>Login Page</h3>
                </Card.Header>
                <Form>
                    <Form.Group className="mx-3 mt-3">
                    <Form.FloatingLabel controlId="username" label="Username:">
                        <Form.Control type="text" placeholder="Username" name="username" onChange={storeValues} />
                    </Form.FloatingLabel>
                    </Form.Group>

                    <Form.Group className="mx-3 mt-3">
                    <Form.FloatingLabel controlId="password" label="Password:">
                        <Form.Control type="password" placeholder="Password" name="password" onChange={storeValues} />
                    </Form.FloatingLabel>
                    </Form.Group>

                    <Button type="button" className="mb-3 mt-3" onClick={userLogin}>Login</Button>
                </Form>
            </Card>
        </div>
    )
}