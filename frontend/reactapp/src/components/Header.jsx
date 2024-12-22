import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();

    const logout = () => {
        navigate('/login'); // Rediriger vers la page de connexion
    };

    return (
        <header class="w-full p-3 bg-black shadow-md fixed top-0 left-0 right-0">
            <div class="container mx-auto flex items-center justify-between">
                <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
                </svg>
            </div>
        </header>
    );
};

export default Header;
