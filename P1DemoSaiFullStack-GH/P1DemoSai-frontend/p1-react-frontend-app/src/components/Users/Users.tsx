import {Button, Form, OverlayTrigger, Popover, Table} from "react-bootstrap"
import { UserInterface } from "../../interfaces/UserInterface"
import { useEffect, useState } from "react"
import axios from "axios";

// Destructuring Users array to access the Array in the .map() function
export const Users: React.FC<{users:UserInterface[]}> = ({users}) => {

    const usersBaseUrl = "http://localhost:8080/users";

    const [userList, setUserList] = useState<UserInterface[]>(users);
    const [activeUserId, setActiveUserId] = useState<number | null>(null);
    const [roleStatus, setRoleStatus] = useState<string>('');

    useEffect(() => {
        console.log(users);
    }, [userList]);

    // Update user popover logic
    const updateUser = async (user: UserInterface) => {
        if (typeof user.userId === 'number') {
            if (activeUserId === user.userId) {
                setActiveUserId(null);
            } else {
                setActiveUserId(user.userId);
                setRoleStatus(user.role);
            }
        } else {
            console.error("User ID not found.");
        }
    };

    // Handle role change and update the state
    const handleRoleChange = async () => {
        if (activeUserId !== null) {
            try {
                const response = await axios.patch(`${usersBaseUrl}/${activeUserId}/${roleStatus}`);
                alert('Updated User Role for: ' + activeUserId);

                // Update the users state after changing the role
                setUserList((prevUsers) =>
                    prevUsers.map((user) =>
                        user.userId === activeUserId ? { ...user, role: roleStatus } : user
                    )
                );

                setActiveUserId(null);
            } catch (error) {
                console.error("Error updating user role:", error);
            }
        }
    };

    // Delete user and update the state
    const deleteUser = async (user: UserInterface) => {
        try {
            const response = await axios.delete(`${usersBaseUrl}/${user.userId}`);
            alert(`Deleted User with ID: ${user.userId}`);

            // Update the users state after deleting a user
            setUserList((prevUsers) =>
                prevUsers.filter((u) => u.userId !== user.userId)
            );
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    };

    const roleChangePopover = (user:UserInterface) => (
        <Popover id={`popover-${user.userId}`}>
            <Popover.Header as="h3">Resolve Reimbursement</Popover.Header>
            <Popover.Body>
                <Form>
                    <Form.Group controlId="formStatus" className="mt-3">
                        <Form.Label>Select Status</Form.Label>
                        <Form.Select
                            aria-label="Status"
                            value={roleStatus}
                            onChange={(e) => setRoleStatus(e.target.value)}
                        >
                            <option value="Employee">----------</option>
                            <option value="Employee">Employee</option>
                            <option value="Manager">Manager</option>
                        </Form.Select>
                    </Form.Group>
                    <Button variant="primary" className="mt-3" onClick={handleRoleChange}>Save Changes</Button> 
                    <Button className="float-end mt-3 mx-3" onClick={() => setActiveUserId(null)}>Close</Button>
                </Form>
            </Popover.Body>
        </Popover>
    );


    return(
        <div className="container">
            <h3>Users</h3>

            <Table striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>ID</th>                        
                        <th>Name</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Options</th>

                    </tr>
                </thead>
                <tbody>
                    {users.map((user, index) => (
                        <tr key={user.userId}>
                            <td>{user.userId}</td>                            
                            <td>{user.firstName + " " + user.lastName}</td>
                            <td>{user.username}</td>
                            <td>{user.role}</td>

                            <td>
                                {/* change later */}
                                    <OverlayTrigger
                                    trigger="click"
                                    placement="right"
                                    show={activeUserId === user.userId} // Show only if active
                                    overlay={roleChangePopover(user)}
                                    rootClose
                                >
                                    <Button variant="outline-info" className="ms-2" onClick={() => updateUser(user)}>Update</Button>
                                </OverlayTrigger>
                                <Button variant="outline-danger" className="ms-2" onClick={() => deleteUser(user)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    )

}