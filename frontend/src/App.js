import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Header from './components/Header';
import QuizList from "./components/QuizList";

function App() {
  return (
      <Router>
          <Header /> {/* Header global */}
        <Routes>
            <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/quizzes" element={<QuizList />} />
        </Routes>
      </Router>
  );
}

export default App;
