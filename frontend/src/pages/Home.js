// src/pages/Home.js
import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div>
            <h1>Bienvenue sur la plateforme de gestion d'évaluation des compétences</h1>
            <p>Connectez-vous pour accéder à vos questionnaires et résultats.</p>
            <Link to="/login">
                <button>Se connecter</button>
            </Link>
        </div>
    );
};

export default Home;
