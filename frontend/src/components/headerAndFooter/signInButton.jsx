import React from 'react';
import { useNavigate } from 'react-router-dom';

const SignInButton = () => {

    const navigate = useNavigate();
    
    return (
        <div className="mt-auto flex justify-end w-full p-4">
        <button 
            onClick={() => navigate('/signin')}
            className="bg-blue-700 text-white px-6 py-3 rounded-lg hover:bg-blue-500">
            Sign in
        </button>
    </div>
    );
}

export default SignInButton;
