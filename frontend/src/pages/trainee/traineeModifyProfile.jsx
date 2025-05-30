import React, { useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";
import axios from 'axios';
import { toast, Toaster } from 'sonner';

function ModifyProfile() {
    const navigate = useNavigate();

    const [userProfile, setUserProfile] = useState({});

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("authToken");

    const fetchUser = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/users/${userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setUserProfile(res.data);
        } catch (error) {
            console.log("Error fetching rankings:", error);
        }
    };

    useEffect(() => {
        if (userId && token) {
            fetchUser();
        }

    }, [userId, token]);

    // Handle form field changes and update the state
    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserProfile((prevProfile) => ({
        ...prevProfile,
        [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        console.log(userProfile)

        e.preventDefault(); // Prevent default form submission behavior
    
        const token = localStorage.getItem("authToken");
    
        try {
            const response = await axios.patch(`http://localhost:8080/users/${userId}`, userProfile, {
                headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
                },
            });

            toast.success("Profile updated successfully!");
            setTimeout(() => {
                navigate("/profile");
            }, 2000);
            
        } catch (error) {
            toast.error("Failed to update profile !");
        }
    };

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-16 text-center mt-16">
                    Update your Contact Details
                </h1>
                <Toaster /> 
                <form className="w-full max-w-sm" onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label htmlFor="firstname" className="block text-gray-700 text-sm font-bold mb-2">First Name</label>
                        <input 
                            type="text" 
                            id="firstname" 
                            name="firstname" 
                            onChange={handleChange}
                            placeholder={userProfile.firstname}
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
                            onChange={handleChange}
                            placeholder={userProfile.lastname}
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
                            onChange={handleChange}
                            placeholder={userProfile.email}
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
                            onChange={handleChange}
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
                            onChange={handleChange}
                            placeholder={userProfile.phone}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="mb-8">
                        <label htmlFor="company" className="block text-gray-700 text-sm font-bold mb-2">Company</label>
                        <input 
                            type="text" 
                            id="company" 
                            name="company"
                            onChange={handleChange} 
                            placeholder={userProfile.company}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="flex justify-center">
                        <button 
                            type="submit"
                            className="bg-blue-700 text-white py-3 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Update
                        </button>
                    </div>
                </form>
                
            </main>
            <Footer />
        </div>
  );
}

export default ModifyProfile;