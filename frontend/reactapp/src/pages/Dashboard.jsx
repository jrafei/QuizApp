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

            
            <a href="/quizlist">
                <button className="btn1">
                    <i className="fas fa-copy"></i>
                    Questionnaires
                </button>
            </a>

            <a href="lien">
                <button className="btn2">
                    <i className="fas fa-star"></i>
                    Résultats aux évaluations
                </button>
            </a>

            <a href="lien">
                <button className="btn3">
                    <i className="fas fa-user"></i>
                    Profil
                </button>
            </a>
        </div>
    );
};

export default Dashboard;
