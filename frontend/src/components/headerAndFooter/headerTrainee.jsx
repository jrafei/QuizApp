import React from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/logo.png';

const HeaderTrainee = () => {

    const navigate = useNavigate();

    return (
        <header className="bg-black text-white flex justify-between items-center py-4 px-6">
            <div className="flex gap-4">
                <button 
                    onClick={() => navigate('/traineespace')}
                    className="bg-black font-bold px-4 py-2 rounded hover:bg-gray-600">
                        Home
                </button>
                <button 
                    onClick={() => navigate('/traineespace/pending')}
                    className="bg-black font-bold px-4 py-2 rounded hover:bg-gray-600">
                        Pending
                </button>
                <button
                    onClick={() => navigate('/traineespace/records')} 
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Records
                </button>
                <button 
                    onClick={() => navigate('/profile')}
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Profile
                </button>
                <button
                    onClick={() => navigate('/confirmdeactivation')} 
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Deactivate
                </button>
                <button
                    onClick={() => navigate('/confirmlogout')} 
                    className="bg-black px-4 py-2 rounded hover:bg-gray-600">
                        Log Out
                </button>
            </div>
            <div>
                <img src={logo} alt="Logo" className="h-10" />
            </div>
        </header>
    );
}

export default HeaderTrainee;
