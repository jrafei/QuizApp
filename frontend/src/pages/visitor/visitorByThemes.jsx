import React from "react";
import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import Footer from "../../components/headerAndFooter/footer";

import ThemeTheme from "../../components/visitor/themesThemes"; 
import ThemeQuiz from "../../components/visitor/themesQuizzes"; 
import ThemeRanking from "../../components/visitor/themesRankings"; 


function VisitorByTheme() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderVisitor /> </header>
        
        <main className="flex-1 flex flex-col items-center">
            <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-4">
                Themes
            </h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 px-4 w-full">
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <ThemeTheme />
                </div>
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <ThemeQuiz />
                </div>
                <div className="p-4 flex justify-center">
                    <ThemeRanking />
                </div>
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

export default VisitorByTheme;