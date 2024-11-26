import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const navigate = useNavigate();

    useEffect(() => {
        // Vérifier si l'utilisateur est connecté
        const email = localStorage.getItem('userEmail');
        if (!email) {
            navigate('/login'); // Redirige vers la page de connexion si non connecté
        }
    }, [navigate]);

    return (
        <div>
            <h1>Tableau de bord</h1>
            <p>Bienvenue, {localStorage.getItem('userEmail')} !</p>
        </div>
    );
};

export default Dashboard;
