import React from 'react';
import { Link } from 'react-router-dom';
import Footer from '../components/Footer';

const Home = () => (
    <div style={{ marginTop: '50px' }}>
        <h1>Bienvenue sur la plateforme d'évaluation</h1>
        <Link to="/login">Se connecter</Link>
        <Footer></Footer>
    </div>
);

export default Home;
