import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // requêtes HTTP
import { toast, Toaster } from 'sonner';

import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import Footer from "../../components/headerAndFooter/footer";


function ForgotPassword() {

    const navigate = useNavigate();
    const [email, setemail] = useState();
    const handlecredentialssending= async(e) => {
        e.preventDefault(); // pour ne pas rafraichir la page (juste tu envoies les req vers le back)
        try {
            const response = await axios.post("http://localhost:8080/auth/forgot-password", {email:email}); //envoie 2 infos vers le backpour qu'ils soient traités
            toast.success("Your credentials have been sent successfully !");
            setTimeout(() => {
                navigate("/signin");
            }, 5000);
        } catch (error) {
            console.log(error);
            toast.error("Error sending your credentials ! ");
        }
    }

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-16 text-center mt-32">
                    Forgot password?
                </h1>
                <Toaster /> 
                <form className="w-full max-w-sm" onSubmit={handlecredentialssending}>
                    <div className="mb-8">
                        <label htmlFor="email" className="block text-gray-700 text-sm font-bold mb-2">E-mail</label>
                        <input
                            value={email}
                            onChange={(e) => setemail(e.target.value)}
                            type="email" 
                            id="email" 
                            name="email" 
                            placeholder="your.email@gmail.com" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="text-center mb-16">
                    We will send your login details to this email address if an existing account matches 
                    </div>

                    <div className="flex justify-center">
                        <button 
                            type="submit"
                            className="bg-blue-700 text-white py-2 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Send
                        </button>
                    </div>
                </form>
                
            </main>
            <div className="mt-auto flex justify-end w-full p-4">
                <button
                    onClick={() => navigate('/signin')}
                    className="bg-blue-700 text-white text-italic px-6 py-3 rounded-lg hover:bg-blue-500">
                        Back
                </button>
            </div>
            <Footer />
        </div>
      );
}

export default ForgotPassword;