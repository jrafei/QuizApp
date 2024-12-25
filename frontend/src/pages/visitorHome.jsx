import React from "react";
import HeaderVisitor from "../components/headerAndFooter/headerVisitor";
import Footer from "../components/headerAndFooter/footer";


function VisitorHome() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderVisitor /> </header>
        
        <main className="flex-1 flex flex-col justify-center items-center">
            <h1 className="text-4xl font-bold text-gray-800 mb-6 text-center">
                Welcome on QuizApp !
            </h1>
            <div className="flex gap-4">
                <button className="bg-blue-500 text-white px-6 py-3 rounded-lg hover:bg-blue-400">
                    Themes
                </button>
                <button className="bg-blue-500 text-white px-6 py-3 rounded-lg hover:bg-blue-400">
                    Clients
                </button>
            </div>
        </main>
        <div className="mt-auto flex justify-end w-full p-4">
            <button className="bg-blue-700 text-white px-6 py-3 rounded-lg hover:bg-blue-500">
                Sign in
            </button>
        </div>
        <Footer />
    </div>
  );
}

export default VisitorHome;