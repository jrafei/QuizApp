// src/pages/Dashboard.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const userEmail = localStorage.getItem('userEmail');
        if (!userEmail) {
            navigate('/login'); // Remplace history.push
        }
    }, [navigate]);

    return (
        <div>
            <h2>Bienvenue dans votre tableau de bord</h2>
            <p>Vous êtes connecté en tant que : {localStorage.getItem('userEmail')}</p>
            <button onClick={() => {
                localStorage.removeItem('userEmail');
                navigate('/'); // Remplace history.push
            }}>
                Déconnexion
            </button>
        </div>
    );
};

export default Dashboard;
