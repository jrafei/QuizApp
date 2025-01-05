import React from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/logo.png';

const HeaderVisitor = () => {

    const navigate = useNavigate();
    
    return (
        <header className="bg-black text-white flex justify-between items-center py-4 px-6">
            <div className="flex gap-4">
                <button
                    onClick={() => navigate('/home')}
                    className="bg-black font-bold px-4 py-2 rounded hover:bg-gray-600">
                        Home
                </button>
                <button 
                    onClick={() => navigate('/home/bythemes')}
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Themes
                </button>
                <button 
                    onClick={() => navigate('/home/byclients')}
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Trainees
                </button>
            </div>
            <div>
                <img src={logo} alt="Logo" className="h-10" />
            </div>
        </header>
    );
}

export default HeaderVisitor;
