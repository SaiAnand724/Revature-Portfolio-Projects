import { useEffect, useState } from "react"
import { ReimbInterface } from "../../interfaces/ReimbInterface"

import { Button, Card, Container, Form, FormControl, Nav, Navbar } from "react-bootstrap"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import { store } from "../globalStore/store"


export const AddReimbursement: React.FC = () => {

    const reimbBaseUrl = "http://localhost:8080/reimbursements";

    const loggedUserId = store.loggedInUser.userId // <- insert get logged User ID


    const navigate = useNavigate()

    const [newReimb, setNewReimb] = useState<ReimbInterface>({
        description: "",
        amount: 0,
        status: "PENDING",
        userId: loggedUserId
    })

    const storeValues = (input:any) => {
        if(input.target.name === "description"){
            setNewReimb(newReimb => ({...newReimb, description:input.target.value}))
        }
        else if(input.target.name === "amount"){
            setNewReimb(newReimb => ({...newReimb, amount:parseFloat(input.target.value)}))
        }
    }

    const addNewReimbursement = async () => {
        try {

            console.log("logged in user ID: ", loggedUserId)

            const response = await axios.post(reimbBaseUrl, newReimb)
            .then((response) => {
                console.log(response.data)
            })  

            //alert("Add Reimbursement Button clicked")

            navigate("/reimbursements")

        }
        catch (error) {
            console.log(error)
            alert("Error: {" + error + "}")
        }
    }

    return(
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
                    {store.loggedInUser.role === "Manager"? (
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
            
            <Card>
                <Card.Header>
                    <h3>Enter Reimbursement Info:</h3>
                </Card.Header>
                <Form>
                    <Form.Group className="mx-3 mt-3">
                    <Form.FloatingLabel controlId="description" label="Description:">
                        <Form.Control as="textarea" rows={3} type="text" placeholder="Add Description" name="description" onChange={storeValues} />
                    </Form.FloatingLabel>
                    </Form.Group>

                    <Form.Group className="mx-3 mt-3">
                    <Form.FloatingLabel controlId="amount" label="Amount:">
                        <Form.Control type="number" placeholder="Enter Amount" name="amount" onChange={storeValues} />
                    </Form.FloatingLabel>
                    </Form.Group>

                    <Button type="button" className="mb-3 mt-3" onClick={addNewReimbursement}>Submit</Button>
                </Form>
            </Card>

        </div>
    )
}