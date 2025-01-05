import React from "react";
import { useNavigate } from 'react-router-dom';

import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import SignInButton from "../../components/headerAndFooter/signInButton";
import Footer from "../../components/headerAndFooter/footer";


function VisitorHome() {

    const navigate = useNavigate();
    
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col justify-center items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-6 text-center">
                    Welcome on QuizApp !
                </h1>
                <div className="flex gap-4">
                    <button 
                        onClick={() => navigate('/home/bythemes')}
                        className="bg-blue-500 text-white px-6 py-3 rounded-lg hover:bg-blue-400">
                        Themes
                    </button>
                    <button 
                        onClick={() => navigate('/home/byclients')}
                        className="bg-blue-500 text-white px-6 py-3 rounded-lg hover:bg-blue-400">
                        Trainees
                    </button>
                </div>
            </main>
            <SignInButton />
            <Footer />
        </div>
    );
}

export default VisitorHome;