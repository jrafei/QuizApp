import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Simuler une requête vers l'API pour valider l'utilisateur
            const response = await axios.post('http://localhost:8080/login', {
                email,
                password,
            });

            // Si la réponse est réussie, stocker l'email dans localStorage
            localStorage.setItem('userEmail', email);

            // Redirection vers le tableau de bord
            navigate('/dashboard');
        } catch (err) {
            // Gérer les erreurs (simule une erreur si l'email/password ne sont pas corrects)
            setError('Email ou mot de passe incorrect.');
        }
    };

    return (
        <div>
            <h2>Se connecter</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Mot de passe</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Se connecter</button>
            </form>
        </div>
    );
};

export default Login;
