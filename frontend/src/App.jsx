import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';

import VisitorHome from "./pages/visitor/visitorHome.jsx";
import VisitorByTheme from "./pages/visitor/visitorByThemes.jsx";
import VisitorByTrainees from "./pages/visitor/visitorByTrainees.jsx";
import Signin from "./pages/authentification/signIn.jsx";
import Signup from "./pages/authentification/signUp.jsx";
import ForgotPassword from "./pages/authentification/forgotPassword.jsx";
import WelcomeBack from "./pages/trainee/welcomeBack.jsx";
import ErrorSpecified from "./pages/exceptionProcessing.jsx";
import WantToDeactivate from "./pages/trainee/traineeDoYouWantToDeactivate.jsx";
import WantToLogOut from "./pages/trainee/traineeDoYouWantToLogOut.jsx";
import Profile from "./pages/trainee/traineeProfile.jsx";
import ModifyProfile from "./pages/trainee/traineeModifyProfile.jsx";
import TraineeHome from "./pages/trainee/traineeHome.jsx";
import TraineeRecords from "./pages/trainee/traineeRecords.jsx";
import TraineeQuiz from "./pages/trainee/traineeQuiz.jsx";
import TraineeQuizEnd from "./pages/trainee/traineeQuizEnd.jsx";
import TraineePendingQuizzes from "./pages/trainee/traineePendingQuizzes.jsx"
 
function App() {

  return (
    <Router>
      <Routes>
        {/* Visitor */}
        <Route path="/" element={<Navigate to="/home" />} />
        <Route path="/home" element={<VisitorHome />} />

        <Route path="/home/bythemes" element={<VisitorByTheme />} />
        <Route path="/home/byclients" element={<VisitorByTrainees />} />

        {/* Authentication */}
        <Route path="/signin" element={<Signin />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgotpassword" element={<ForgotPassword />} />
        <Route path="/errorspecified" element={<ErrorSpecified />} />

        {/* Trainee */}
        <Route path="/traineespace" element={<TraineeHome />} />
        <Route path="/traineespace/pending" element={<TraineePendingQuizzes />} />
        <Route path="/traineespace/records" element={<TraineeRecords />} />
        <Route path="/traineespace/quiz" element={<TraineeQuiz />} />
        <Route path="/traineespace/endquiz" element={<TraineeQuizEnd />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/updateprofile" element={<ModifyProfile />} />
        <Route path="/activate" element={<WelcomeBack />} />
        <Route path="/confirmdeactivation" element={<WantToDeactivate />} />
        <Route path="/confirmlogout" element={<WantToLogOut />} />
      </Routes>
    </Router>
  );
}
export default App
