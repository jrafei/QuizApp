import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem('userEmail'); // Supprimer l'email de localStorage
        navigate('/login'); // Rediriger vers la page de connexion
    };

    return (
        <header style={{ display: 'flex', justifyContent: 'space-between', padding: '10px', backgroundColor: '#f5f5f5' }}>
            <h1>Quiz Platform</h1>
            {localStorage.getItem('userEmail') && ( // Affiche le bouton uniquement si connecté
                <button onClick={logout} style={{ cursor: 'pointer' }}>
                    Se déconnecter
                </button>
            )}
        </header>
    );
};

export default Header;
