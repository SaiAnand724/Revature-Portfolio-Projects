import { UserInterface } from "../../interfaces/UserInterface";

export const store:any = {
    loggedInUser: {
        userId:0,
        firstName:"",
        lastName:"",
        username:"",
        role:""
    } as UserInterface, 
    loggedIn: false,
    baseURL: "http://localhost:8080/"
}