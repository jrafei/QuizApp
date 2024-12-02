// src/pages/Login.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

// Simuler une API fictive
const login = async (email, password) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (email === "test@exemple.com" && password === "StrongPassword123!") {
                resolve({ status: 200 });
            } else {
                reject(new Error("Email ou mot de passe incorrect"));
            }
        }, 1000);
    });
};

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate(); // Remplace useHistory

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage(''); // Réinitialiser le message d'erreur
        try {
            // Appel à la fonction de simulation d'authentification
            const response = await login(email, password);
            if (response.status === 200) {
                // Stocker l'email dans localStorage
                localStorage.setItem('userEmail', email);

                // Rediriger vers le tableau de bord
                navigate('/dashboard');
            }
        } catch (error) {
            // Gestion des erreurs d'authentification
            setErrorMessage('Échec de l’authentification. Vérifiez vos informations.');
        }
    };

    return (
        <div style={{ maxWidth: '400px', margin: 'auto', padding: '20px', textAlign: 'center' }}>
            <h2>Se connecter</h2>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Email</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Mot de passe</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>
                <button type="submit" style={{ padding: '10px 20px', cursor: 'pointer' }}>
                    Se connecter
                </button>
            </form>
        </div>
    );
};

export default Login;
