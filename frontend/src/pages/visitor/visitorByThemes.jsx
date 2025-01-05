import React, { useState } from "react";
import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import SignInButton from "../../components/headerAndFooter/signInButton";
import Footer from "../../components/headerAndFooter/footer";

import ThemeTheme from "../../components/visitor/themesThemes"; 
import ThemeQuiz from "../../components/visitor/themesQuizzes"; 
import ThemeRanking from "../../components/visitor/themesRankings"; 


function VisitorByTheme() {

    const [selectedThemeId, setSelectedThemeId] = useState(null); 
    const [selectedQuizId, setSelectedQuizId] = useState(null); 
  
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center px-4 py-8">
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">
                    Themes
                </h1>
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">

                    {/* Theme */}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                        <h2 className="text-xl font-bold mb-4 text-center">Themes</h2>
                        <div className="mb-8 w-full">
                            <ThemeTheme 
                                onSelectTheme={setSelectedThemeId}
                                selectedThemeId={selectedThemeId}
                            /> 
                        </div>
                    </div>

                    {/* Quiz*/}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                        <h2 className="text-xl font-bold mb-4 text-center ">Quizzes</h2>
                        <div className="mb-8 w-full overflow-x-auto">
                        {selectedThemeId ? (
                            <ThemeQuiz themeId={selectedThemeId} onSelectQuiz={setSelectedQuizId} />
                        ) : (
                            <p className="text-gray-500 text-center">Select a theme to view quizzes.</p>
                        )}
                        </div>
                    </div> 

                    {/* Ranking */}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                        <h2 className="text-xl font-bold mb-4 text-center">Rankings</h2>
                        <div className="mb-8 w-full">
                        {selectedQuizId ? (
                            <ThemeRanking quizId={selectedQuizId} />
                        ) : (
                            <p className="text-gray-500 text-center">Select a quiz to view rankings.</p>
                        )}
                        </div>
                    </div> 
                </div>
            </main>

            <SignInButton />
            <Footer />
        </div>
    );
}

export default VisitorByTheme;