import React from "react";
import HeaderTrainee from "../components/headerAndFooter/headerTrainee";
import Footer from "../components/headerAndFooter/footer";


function WantToLogOut() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderTrainee /> </header>
        
        <main className="flex-1 flex flex-col justify-center items-center">
            <h1 className="text-3xl font-bold text-gray-800 mb-16 text-center">
                Do you want to log out? 
            </h1>
            <div className="flex gap-16">
                <button className="bg-red-700 font-bold text-white px-12 py-3 rounded-lg hover:bg-red-500">
                    Yes
                </button>
                <button className="bg-green-800 font-bold text-white px-12 py-3 rounded-lg hover:bg-green-700">
                    No
                </button>
            </div>
        </main>
        <Footer />
    </div>
  );
}

export default WantToLogOut;