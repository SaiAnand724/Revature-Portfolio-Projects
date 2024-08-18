import { useState } from "react";
import { Button, Card, Container, Form, Nav, Navbar } from "react-bootstrap";
import { store } from "../globalStore/store";
import { useNavigate } from "react-router-dom";

export const UserProfile: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const userObj = store.loggedInUser

    const navigate = useNavigate()

    const storeValues = (input:any) => {
        if(input.target.name === "newUsername"){
            setUsername(input.target.value)
        }
    }

    const updateUsername = async () => {
        alert(`Current username: ${userObj.username} -> Eventual updated username: ${username}`)
        alert(`Update username button has been clicked for user with ID: ${userObj.userId}`)
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
                        <Button className="me-2" onClick={() => navigate("/reimbursements")}>Reimbursements</Button>
                        {userObj.role === "Manager"? (
                            <>
                            <Button className="me-2" onClick={() => navigate("/users")}>Users</Button>
                            </>
                        ) : <></>}
                    </Nav>
                    <Nav className="ms-auto">
                        <Button onClick={() => navigate("/userProfile")}>Profile</Button>
                    </Nav>
                </Container>
            </Navbar>
            
           <Card>
                <Card.Header>
                    <h3>User Profile</h3>
                </Card.Header>
                <h4>First Name: {userObj.firstName}</h4>
                <h4>Last Name: {userObj.lastName}</h4>
                <h4>Username: {userObj.username}</h4>
                <h4>User Role: {userObj.role}</h4>
                <Form>
                    <Form.Group className="mx-3 mt-3 mb-3">
                    <Form.FloatingLabel controlId="username" label="New Username:">
                        <Form.Control type="text" placeholder="Username" name="newUsername" onChange={storeValues} />
                    </Form.FloatingLabel>
                    </Form.Group>                
                    <Button className="mb-3" onClick={(updateUsername)}>Update Username</Button>
                </Form>
            </Card>            

        </div>
    )
}