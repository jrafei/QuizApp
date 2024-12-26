import React from "react";
import { useNavigate } from 'react-router-dom';

import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import Footer from "../../components/headerAndFooter/footer";


function Signin() {

    const navigate = useNavigate();

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-32">
                    Sign in
                </h1>
                <form className="w-full max-w-sm">
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-gray-700 text-sm font-bold mb-2">E-mail</label>
                        <input 
                            type="email" 
                            id="email" 
                            name="email" 
                            placeholder="your.email@gmail.com" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="mb-6">
                        <label htmlFor="password" className="block text-gray-700 text-sm font-bold mb-2">Password</label>
                        <input 
                            type="password" 
                            id="password" 
                            name="password" 
                            placeholder="**********" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="flex justify-center">
                        <button 
                            type="submit"
                            className="bg-blue-700 text-white py-2 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Submit
                        </button>
                    </div>
                </form>
                
            </main>
            <div className="mt-auto flex justify-between w-full p-4">
                <button 
                    onClick={() => navigate('/signup')}
                    className="bg-blue-700 text-white text-italic px-6 py-3 rounded-lg hover:bg-blue-500">
                        New here?
                </button>
                <button 
                    onClick={() => navigate('/forgotpassword')}
                    className="bg-blue-700 text-white text-italic px-6 py-3 rounded-lg hover:bg-blue-500">
                        Forgot password?
                </button>
            </div>
            <Footer />
        </div>
      );
}

export default Signin;