import React from "react";
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
 
function App() {

  return (
    <div>
      <VisitorHome/>
    </div>
  );
}

export default App
