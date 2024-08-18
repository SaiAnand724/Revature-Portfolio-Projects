import { Button, Table, OverlayTrigger, Popover, Form } from "react-bootstrap";
import { ReimbInterface } from "../../interfaces/ReimbInterface";
import { useState, useEffect } from "react";
import axios from "axios";
import { store } from "../globalStore/store";

export const Reimbursements: React.FC<{ reimbursements: ReimbInterface[] }> = ({ reimbursements }) => {
    const reimbBaseUrl = "http://localhost:8080/reimbursements";
    const role_obj: String = store.loggedInUser.role; // <- insert get user role logic

    // State to manage the currently active reimbursement for updating
    const [activeReimbId, setActiveReimbId] = useState<number | null>(null);
    const [status, setStatus] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [amount, setAmount] = useState<string>('');
    const [reimbList, setReimbList] = useState<ReimbInterface[]>(reimbursements);

    useEffect(() => {
        setReimbList(reimbursements);
        console.log(reimbursements);
    }, [reimbursements]);

    const fetchReimbursements = async () => {
        try {
            let response:any = null;
            // GET request for Reimbursements list
            if (role_obj === "Employee"){
                response = await axios.get(reimbBaseUrl + `/user/${store.loggedInUser.userId}`)
            }
            else if (role_obj === "Manager"){
                response = await axios.get(reimbBaseUrl)
            }
            setReimbList(response.data);
        } catch (error) {
            console.error("Failed to fetch reimbursements:", error);
        }
    };

    const updateReimb = (reimb: ReimbInterface) => {
        if (typeof reimb.reimbId === 'number') {
            if (activeReimbId === reimb.reimbId) {
                // If the popover is already active, close it
                setActiveReimbId(null);
            } else {
                // Otherwise, set the active reimbursement ID
                setActiveReimbId(reimb.reimbId);
                setDescription(reimb.description);
                setAmount(reimb.amount.toString());
            }
        } else {
            console.error("Reimbursement ID not found.");
        }
    };

    const resolveReimb = (reimb: ReimbInterface) => {
        if (typeof reimb.reimbId === 'number') {
            if (activeReimbId === reimb.reimbId) {
                // If the popover is already active, close it
                setActiveReimbId(null);
            } else {
                // Otherwise, set the active reimbursement ID
                setActiveReimbId(reimb.reimbId);
                setDescription(reimb.status);
            }
        } else {
            console.error("Reimbursement ID not found.");
        }
    };

    const handleUpdate = async () => {
        if (activeReimbId !== null) {
            try {
                await axios.patch(`${reimbBaseUrl}/${activeReimbId}/${parseFloat(amount)}`, description, {
                    headers: { 'Content-Type': 'text/plain' },
                });
                alert('Updated Reimbursement: ' + activeReimbId);
                fetchReimbursements(); // Refetch data after update
            } catch (error) {
                console.error("Failed to update reimbursement:", error);
            } finally {
                setActiveReimbId(null);
            }
        }
    };

    const handleResolve = async () => {
        if (activeReimbId !== null) {
            try {
                await axios.patch(`${reimbBaseUrl}/resolve/${activeReimbId}/${status}`);
                alert('Resolved Reimbursement: ' + activeReimbId);
                fetchReimbursements(); // Refetch data after resolving
            } catch (error) {
                console.error("Failed to resolve reimbursement:", error);
            } finally {
                setActiveReimbId(null);
            }
        }
    };

    const deleteReimb = async (reimb: ReimbInterface) => {
        try {
            await axios.delete(`${reimbBaseUrl}/${reimb.reimbId}`);
            alert(`Deleted reimbursement with ID: ${reimb.reimbId}`);
            fetchReimbursements(); // Refetch data after delete
        } catch (error) {
            console.error("Failed to delete reimbursement:", error);
        }
    };

    const updatePopover = (reimb: ReimbInterface) => (
        <Popover id={`popover-${reimb.reimbId}`}>
            <Popover.Header as="h3">Update Reimbursement</Popover.Header>
            <Popover.Body>
                <Form>
                    <Form.Group controlId="formDescription">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter new description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId="formAmount" className="mt-3">
                        <Form.Label>Amount</Form.Label>
                        <Form.Control
                            type="number"
                            placeholder="Enter new amount"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                        />
                    </Form.Group>
                    <Button variant="primary" className="mt-3" onClick={handleUpdate}>Save Changes</Button>
                    <Button className="float-end mt-3 mx-3" onClick={() => setActiveReimbId(null)}>Close</Button>
                </Form>
            </Popover.Body>
        </Popover>
    );

    const resolvePopover = (reimb: ReimbInterface) => (
        <Popover id={`popover-${reimb.reimbId}`}>
            <Popover.Header as="h3">Resolve Reimbursement</Popover.Header>
            <Popover.Body>
                <Form>
                    <Form.Group controlId="formStatus" className="mt-3">
                        <Form.Label>Select Status</Form.Label>
                        <Form.Select
                            aria-label="Status"
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                        >
                            <option value="PENDING">----------</option>
                            <option value="PENDING">PENDING</option>
                            <option value="APPROVED">APPROVED</option>
                            <option value="DENIED">DENIED</option>
                        </Form.Select>
                    </Form.Group>
                    <Button variant="primary" className="mt-3" onClick={handleResolve}>Save Changes</Button> 
                    <Button className="float-end mt-3 mx-3" onClick={() => setActiveReimbId(null)}>Close</Button>
                </Form>
            </Popover.Body>
        </Popover>
    );

    return (
        <div className="container">
            {role_obj === "Employee"? (
            <>
            <h3>{store.loggedInUser.firstName} {store.loggedInUser.lastName} Reimbursements</h3>
            </>
            ) : <></>}
            {role_obj === "Manager"? (
            <>
            <h3>User Reimbursements</h3>
            </>
            ) : <></>}

            <Table striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Options</th>
                    </tr>
                </thead>
                <tbody>
                    {reimbursements.map((reimb) => (
                        <tr key={reimb.reimbId}>
                            <td>{reimb.reimbId}</td>
                            <td>{reimb.description}</td>
                            <td>{reimb.amount}</td>
                            <td>{reimb.status}</td>
                            <td>
                                {/* Need role check cond to display buttons
                                {store.loggedInUser.role === "Employee" ? : <>}
                                {store.loggedInUser.role === "Manager" ? : <>}
                                
                                {store.loggedInUser.role === "Manager"? (
                                    <>

                                    </>
                                ) : <></>}*/}

                                {role_obj === "Employee"? (
                                    <>
                                <OverlayTrigger
                                    trigger="click"
                                    placement="right"
                                    show={activeReimbId === reimb.reimbId} // Show only if active
                                    overlay={updatePopover(reimb)}
                                    rootClose
                                >
                                    <Button variant="outline-info" className="ms-2" onClick={() => updateReimb(reimb)}>Update</Button>
                                </OverlayTrigger>
                                    </>
                                ) : <></>}

                                {role_obj === "Manager"? (
                                    <>
                                <OverlayTrigger
                                    trigger="click"
                                    placement="right"
                                    show={activeReimbId === reimb.reimbId} // Show only if active
                                    overlay={resolvePopover(reimb)}
                                    rootClose
                                >
                                    <Button variant="outline-info" className="ms-2" onClick={() => resolveReimb(reimb)}>Resolve</Button>
                                </OverlayTrigger>
                                <Button variant="outline-danger" className="ms-2" onClick={() => deleteReimb(reimb)}>Delete</Button>
                                    </>
                                ) : <></>}

                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};
