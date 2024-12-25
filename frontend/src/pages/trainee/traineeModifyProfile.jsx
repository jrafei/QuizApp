import React from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";


function ModifyProfile() {

    const userProfile = {
        firstname: "John",
        lastname: "Doe",
        email: "john.doe@example.com",
        password: "**********",
        company: "Example Corp",
        phone: "+1234567890",
        creationDate: "2024-01-01",
        isActive: true,
        role: "User",
      };


  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderTrainee /> </header>
        <main className="flex-1 flex flex-col items-center">
            <h1 className="text-4xl font-bold text-gray-800 mb-16 text-center mt-16">
                Update your Contact Details
            </h1>
            <form className="w-full max-w-sm">
                <div className="mb-4">
                    <label htmlFor="firstname" className="block text-gray-700 text-sm font-bold mb-2">First Name</label>
                    <input 
                        type="text" 
                        id="firstname" 
                        name="firstname" 
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