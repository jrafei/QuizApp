import React from "react";
import HeaderTrainee from "../components/headerAndFooter/headerTrainee";
import Footer from "../components/headerAndFooter/footer";


function Profile() {

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
        <main className="flex-1 flex flex-col justify-center items-center">
        <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
          Profile
        </h1>

        <div className="w-full max-w-sm flex flex-col items-center">
            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Firstname:
                </label>
                <p className="text-gray-800">{userProfile.firstname}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Lastname:
                </label>
                <p className="text-gray-800">{userProfile.lastname}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    E-mail:
                </label>
                <p className="text-gray-800">{userProfile.email}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Password:
                </label>
                <p className="text-gray-800">{userProfile.password}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Company:
                </label>
                <p className="text-gray-800">{userProfile.company}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Phone:
                </label>
                <p className="text-gray-800">{userProfile.phone}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Role:
                </label>
                <p className="text-gray-800">{userProfile.role}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Account Active:
                </label>
                <p className="text-gray-800">{userProfile.isActive ? "Active" : "Inactive"}</p>
            </div>

            <div className="mb-4 w-full">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                Account Created:
                </label>
                <p className="text-gray-800">{userProfile.creationDate}</p>
            </div>
        </div>

      </main>
        <div className="mt-auto flex justify-center w-full p-4 mb-2">
            <button className="bg-blue-700 text-white text-italic px-12 py-4 rounded-lg hover:bg-blue-500">
                Modify
            </button>
        </div>
        <Footer />
    </div>
  );
}

export default Profile;