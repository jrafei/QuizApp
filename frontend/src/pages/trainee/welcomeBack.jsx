import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // requêtes HTTP
import { toast, Toaster } from 'sonner';

import HeaderGeneral from "../../components/headerAndFooter/headerGeneral";
import Footer from "../../components/headerAndFooter/footer";

function WelcomeBack() {

    const navigate = useNavigate();
    
    const [email, setemail] = useState("");
    const [validationCode, setvalidationCode] = useState("");

    const handlecodesending = async(e) => {
        e.preventDefault(); 
        try {
            const response = await axios.post("http://localhost:8080/auth/reactivation-request", {email:email}); //envoie code par mail
            console.log(response.data);
            toast.success(response.data);
        } catch (error) {
            console.log(error);
            toast.error("Error while sending the activation code.");
        }
    }

    const handleactivation = async(e) => {
        e.preventDefault(); 
        
        if (!email || !validationCode) {
            toast.error("Please provide both email and activation code.");
            setTimeout(() => {
                navigate("/signin");
            }, 200000);
        }

        try {
            const response = await axios.post("http://localhost:8080/auth/reactivate-account", {email:email, validationCode:validationCode}); 
            console.log(response.data);
            toast.success("Your account has been successfully reactivated.");
            setTimeout(() => {
                navigate("/signin");
            }, 2000);

        } catch (error) {
            console.log(error);
            if (error.response) {
                if (error.response.status === 404) {
                    toast.error(error.response.data || "Invalid input. Please check your details.");
                } else if (error.response.status === 500) {
                    toast.error("Server error. Please try again later.");
                } else {
                    toast.error("An unexpected error occurred.");
                }
            } else {
                toast.error("Error while activating the account.");
            }
            setTimeout(() => {
                navigate("/signin");
            }, 20000);
        }
    }

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderGeneral /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                
                <h1 className="text-4xl font-bold text-gray-800 mb-28 text-center mt-32">
                    Welcome back! 
                </h1>
                <Toaster /> 
                <form className="w-full max-w-sm" onSubmit={handlecodesending}>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-gray-700 text-sm font-bold mb-2">E-mail</label>
                        <input 
                            value={email}
                            onChange={(e) => setemail(e.target.value)}
                            type="email" 
                            id="email" 
                            name="email" 
                            placeholder="Please enter your email account" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="flex justify-center mb-16">
                        <button
                            type="submit"
                            className="bg-black text-white py-2 px-6 rounded-lg hover:bg-grey-600 focus:outline-none focus:ring-2 focus:ring-grey-500">                       
                            Activation code
                        </button>
                    </div>
                </form>
                    

                <form className="w-full max-w-sm" onSubmit={handleactivation}>
                    <div className="mt-8 mb-4">
                        <label htmlFor="code" className="block text-gray-700 text-sm font-bold mb-2">Activation code</label>
                        <input
                            value={validationCode}
                            onChange={(e) => setvalidationCode(e.target.value)}
                            type="code" 
                            id="code" 
                            name="code" 
                            placeholder="******" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="flex justify-center">
                        <button
                            type="submit"
                            className="bg-blue-700 text-white py-2 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"                      
                            disabled={!email}> 
                            Activate my account
                        </button>
                    </div>
                </form>                
            
            <div className="mt-auto flex justify-end w-full p-4">
                <button
                    onClick={() => navigate('/signin')}
                    className="bg-blue-700 text-white text-italic px-6 py-2 rounded-lg hover:bg-blue-500">
                        Back
                </button>
            </div>
            </main>
            <Footer />
        </div>
      );
}

export default WelcomeBack;