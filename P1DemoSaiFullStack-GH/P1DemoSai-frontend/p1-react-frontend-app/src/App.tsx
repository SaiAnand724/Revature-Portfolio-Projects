import React from 'react';
import logo from './logo.svg';
import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css'; // Importing Bootstrap CSS globally

import { BrowserRouter, Route, Routes } from "react-router-dom";


// Component imports
//import {LoginPage} from "../components/HomeLoginRegister/LoginPage";
import { LoginPage } from './components/HomeLoginRegister/LoginPage';
import { RegisterUser } from './components/HomeLoginRegister/RegisterUser';
import { UserContainer } from './components/Users/UserContainer';
import { ReimbContainer } from "./components/Reimbursements/ReimbContainer";
import { AddReimbursement } from './components/Reimbursements/AddReimbursement';
import { UserProfile } from './components/Users/UserProfile';




function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<LoginPage/>}/>
              <Route path="/registerUser" element={<RegisterUser/>}/>              
              <Route path="/users" element={<UserContainer/>}/>
              <Route path="/reimbursements" element={<ReimbContainer/>}/>
              <Route path="/addReimbursement" element={<AddReimbursement/>}/>
              <Route path="/userProfile" element={<UserProfile/>}/>
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
