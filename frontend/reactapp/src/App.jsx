import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Header from './components/Header';
import QuizList from './pages/QuizList';
import QuizDetails from './pages/QuizDetails';


function App() {
  return (
      <Router>
          <Header /> {/* Header global */}
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/quizlist" element={<QuizList />} />
          <Route path="/quizzes" element={<QuizDetails />} />


        </Routes>
      </Router>
  );
}

export default App;
