import React from "react";
import { useNavigate } from 'react-router-dom';

import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import Footer from "../../components/headerAndFooter/footer";


function Signup() {

    const navigate = useNavigate();

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-16">
                    Sign up
                </h1>
                <form className="w-full max-w-sm">
                    <div className="mb-4">
                        <label htmlFor="firstname" className="block text-gray-700 text-sm font-bold mb-2">First Name</label>
                        <input 
                            type="text" 
                            id="firstname" 
                            name="firstname" 
                            placeholder="Your name"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="lastname" className="block text-gray-700 text-sm font-bold mb-2">Last Name</label>
                        <input 
                            type="text" 
                            id="lastname" 
                            name="lastname" 
                            placeholder="Your lastname"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

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

                    <div className="mb-4">
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

                    <div className="mb-4">
                        <label htmlFor="phone" className="block text-gray-700 text-sm font-bold mb-2">Phone</label>
                        <input 
                            type="tel" 
                            id="phone" 
                            name="phone" 
                            placeholder="0744546785"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="mb-8">
                        <label htmlFor="company" className="block text-gray-700 text-sm font-bold mb-2">Company</label>
                        <input 
                            type="text" 
                            id="company" 
                            name="company" 
                            placeholder="Your company name"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
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
            <div className="mt-auto flex justify-end w-full p-4">
                <button
                    onClick={() => navigate('/signin')}
                    className="bg-blue-700 text-white text-italic px-6 py-3 rounded-lg hover:bg-blue-500">
                        Already have an account?
                </button>
            </div>
            <Footer />
        </div>
      );
}

export default Signup;