import React, { useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";


function WantToDeactivate() {
    
    const navigate = useNavigate();

    const deactivation = () => {
        const token = localStorage.getItem("authToken");
        const userId = localStorage.getItem("userId")

        const deactivateAccount = async () => {
            try {
                const response = await axios.patch(`http://localhost:8080/users/${userId}/deactivate`, {}, {
                    headers: { 
                        Authorization: `Bearer ${token}`
                    }
                });
            } catch (err) {
            }
        };
        deactivateAccount();
        navigate("/")
    };

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            
            <main className="flex-1 flex flex-col justify-center items-center">
                <h1 className="text-3xl font-bold text-gray-800 mb-16 text-center">
                    Do you want to deactivate your account? 
                </h1>
                <div className="flex gap-16">
                    <button
                        onClick={() => deactivation()} 
                        className="bg-red-700 font-bold text-white px-12 py-3 rounded-lg hover:bg-red-500">
                        Yes
                    </button>
                    <button
                        onClick={() => navigate('/traineespace')}
                        className="bg-green-800 font-bold text-white px-12 py-3 rounded-lg hover:bg-green-700">
                            No
                    </button>
                </div>
            </main>
            <Footer />
        </div>
    );
}

export default WantToDeactivate;