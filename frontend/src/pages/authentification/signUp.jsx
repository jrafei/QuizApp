import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // requêtes HTTP
import { toast, Toaster } from 'sonner';

import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import Footer from "../../components/headerAndFooter/footer";

function Signup() {

    const navigate = useNavigate();
    const [firstname, setfirstname] = useState();
    const [lastname, setlastname] = useState();
    const [role, setrole] = useState("TRAINEE");
    const [company, setcompany] = useState();
    const [phone, setphone] = useState();
    const [email, setemail] = useState();
    const [isActive, setisactive] = useState(false);

    const handleauthentification = async(e) => {
        e.preventDefault(); // pour ne pas rafraichir la page (juste tu envoies les req vers le back)
        try {
            const response = await axios.post("http://localhost:8080/users", {firstname:firstname, lastname:lastname, role:role, company:company, phone:phone, email:email, isActive:isActive});
            console.log(response.data);
            toast.success("Registered successfully");
            toast.info("Please activate your account by clicking on the link received in your mails, then sign in");
            setTimeout(() => {
                navigate("/signin");
            }, 5000);
        } catch (error) {
            console.log(error);
            toast.error("Error while registering");

        }
    }

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-16">
                    Sign up
                </h1>
                <Toaster />
                <form className="w-full max-w-sm" onSubmit={handleauthentification}>
                    <div className="mb-4">
                        <label htmlFor="firstname" className="block text-gray-700 text-sm font-bold mb-2">First Name</label>
                        <input
                            value={firstname}
                            onChange={(e) => setfirstname(e.target.value)}
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
                            value={lastname}
                            onChange={(e) => setlastname(e.target.value)}
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

                    <div className="mb-4">
                        <label htmlFor="phone" className="block text-gray-700 text-sm font-bold mb-2">Phone</label>
                        <input
                            value={phone}
                            onChange={(e) => setphone(e.target.value)}
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
                            value={company}
                            onChange={(e) => setcompany(e.target.value)}
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