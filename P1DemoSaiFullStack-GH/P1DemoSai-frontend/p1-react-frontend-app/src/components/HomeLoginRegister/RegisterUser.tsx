import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Card, Container, Form, FormControl, Nav, Navbar } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { store } from "../globalStore/store"

export const RegisterUser: React.FC = () => {

    const navigate = useNavigate()

    const[newUser, setNewUser] = useState({
        firstName:"",
        lastName:"",
        username:"",
        password:"",
        role:"Employee" //we want role to be 'Employee' by default
    })

    const storeValues = (input:any) => {
        if(input.target.name === "username"){
            setNewUser((newUser) => ({...newUser, username:input.target.value}))
        } else if (input.target.name === "password") {
            setNewUser((newUser) => ({...newUser, password:input.target.value}))
        } else if(input.target.name === "firstName"){
            setNewUser((newUser) => ({...newUser, firstName:input.target.value}))
        } else if(input.target.name === "lastName"){
            setNewUser((newUser) => ({...newUser, lastName:input.target.value}))
        } 
    }

    const registerUser = async () => {
        try {
            console.log(`Username: ${newUser.username}  Password: ${newUser.password}`)
            console.log(`First Name: ${newUser.firstName}  Last Name: ${newUser.lastName}`)

            const response = await axios.post("http://localhost:8080/users", newUser)

            //alert("Register User Button clicked")

            navigate("/")

        }
        catch (error) {
            alert("Error: {" + error + "}")
        }
    }

    return(
        <div className="register-user">
            <Navbar bg="dark" data-bs-theme="dark">
                <Container fluid>
                    <Nav>
                    <Button className="me-2" onClick={() => navigate("/")}>Back to Login</Button> 
                    </Nav>
                </Container>
            </Navbar>

            <Card>
                <Card.Header>
                    <h3>Register New User</h3>
                </Card.Header>
                <Form>
                    <Form.Group className="mx-3 mt-3">
                        <Form.FloatingLabel controlId="firstName" label="First Name:">
                            <Form.Control type="text" placeholder="First Name" name="firstName" onChange={storeValues} />
                        </Form.FloatingLabel>
                    </Form.Group>

                    <Form.Group className="mx-3 mt-3">
                        <Form.FloatingLabel controlId="lastName" label="Last Name:">
                            <Form.Control type="text" placeholder="Last Name" name="lastName" onChange={storeValues} />
                        </Form.FloatingLabel>
                    </Form.Group>

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

                    <Button type="button" className="mb-3 mt-3" onClick={registerUser}>Submit</Button>
                </Form>
            </Card>
        </div>
    )
}