import React, { useState } from "react";
import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import SignInButton from "../../components/headerAndFooter/signInButton";
import Footer from "../../components/headerAndFooter/footer";

import TraineeTrainee from "../../components/visitor/traineesTrainees"; 
import TraineeTheme from "../../components/visitor/traineesThemes"; 
import TraineeQuiz from "../../components/visitor/traineesQuiz"; 
import TraineeResultQuiz from "../../components/visitor/traineesResultsQuiz"; 
import TraineeResultTheme from "../../components/visitor/traineesResultsTheme";


function VisitorByTrainees() {
    
    const [selectedButton, setSelectedButton] = useState(null);
    const [selectedTraineeId, setSelectedTraineeId] = useState(null); 
    const [selectedQuizId, setSelectedQuizId] = useState(null); 
    const [selectedThemeId, setSelectedThemeId] = useState(null)

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderVisitor /> </header>
            
            <main className="flex-1 flex flex-col items-center px-4 py-8">
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-4">
                    Trainees
                </h1>

                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">

                    {/* Trainees */}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                        <TraineeTrainee
                            selectedTraineeId={selectedTraineeId}
                            onSelectTrainee={setSelectedTraineeId}
                            selectedButton={selectedButton}
                            setSelectedButton={setSelectedButton}
                        />
                    </div>
    
                    {/* Quiz/ Theme */}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                    {selectedTraineeId ? (
                            selectedButton === 'quiz' ? (
                                <TraineeQuiz 
                                    traineeId={selectedTraineeId} 
                                    onSelectQuiz={setSelectedQuizId}
                                    selectedQuizId={selectedQuizId}
                                />
                            ) : (
                                <TraineeTheme 
                                    traineeId={selectedTraineeId}
                                    onSelectTheme={setSelectedThemeId}
                                    selectedThemeId={selectedThemeId}
                                />
                            )
                        ) : (
                            <p className="text-gray-500 text-center">Select a trainee to view associated quizzes/themes.</p>
                        )}
                    </div>

                    {/* Results */}
                    <div className="flex flex-col items-center border border-gray-300 rounded-lg p-4">
                    {selectedButton === "quiz" && selectedQuizId ? (
                            <TraineeResultQuiz
                                traineeId={selectedTraineeId} 
                                quizId={selectedQuizId}  
                            />
                    ) : selectedButton === "theme" && selectedThemeId ? (
                            <TraineeResultTheme
                                traineeId={selectedTraineeId} 
                                themeId={selectedThemeId}  
                            />
                    ) : (
                        <p className="text-gray-500 text-center">Select a quiz/theme to view results.</p>
                    )}
                    </div> 
                </div>
            </main>
             
            <SignInButton />
            <Footer />
        </div>
    );
}

export default VisitorByTrainees;