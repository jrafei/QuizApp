import React from "react";
import './App.css'
import VisitorHome from "./pages/visitorHome.jsx";
import VisitorByTheme from "./pages/visitorByThemes.jsx";
import VisitorByTrainees from "./pages/visitorByTrainees.jsx";
import Signin from "./pages/signIn.jsx";
import Signup from "./pages/signUp.jsx";
import ForgotPassword from "./pages/forgotPassword.jsx";
import WelcomeBack from "./pages/welcomeBack.jsx";
import ErrorSpecified from "./pages/exceptionProcessing.jsx";
import WantToDeactivate from "./pages/traineeDoYouWantToDeactivate.jsx";
import WantToLogOut from "./pages/traineeDoYouWantToLogOut.jsx";
import Profile from "./pages/traineeProfile.jsx";
import ModifyProfile from "./pages/traineeModifyProfile.jsx";

function App() {

  return (
    <div>
      <ModifyProfile/>
    </div>
  );
}

export default App
