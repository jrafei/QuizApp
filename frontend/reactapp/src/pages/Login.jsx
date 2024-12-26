import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Footer from '../components/Footer';

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
            <form onSubmit={handleSubmit}>
                {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}
                <div style={{ marginTop: '50px' }}>
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
                <div style={{ marginTop: '20px' }}>
                    <button type="submit">Se connecter</button>
                </div>
            </form>
            <div style={{ marginTop: '20px' }}>
                <Footer />
            </div>
        </div>

    );
};

export default Login;
