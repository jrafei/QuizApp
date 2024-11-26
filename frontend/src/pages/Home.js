import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => (
    <div>
        <h1>Bienvenue sur la plateforme d'évaluation</h1>
        <Link to="/login">Se connecter</Link>
    </div>
);

export default Home;
