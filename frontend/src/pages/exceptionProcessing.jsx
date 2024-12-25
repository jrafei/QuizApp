import React from "react";
import HeaderGeneral from "../components/headerAndFooter/headerGeneral";
import Footer from "../components/headerAndFooter/footer";


function ErrorSpecified() {
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderGeneral /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-32 text-center mt-32">
                    Error : *to be specified*
                </h1>

                <div>
                    <button className="bg-blue-700 text-white text-italic px-6 py-2 rounded-lg hover:bg-blue-500">
                        Retry
                    </button>
                </div>
            </main>

            <Footer />
        </div>
      );
}

export default ErrorSpecified;