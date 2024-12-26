import React from "react";
import HeaderVisitor from "../../components/headerAndFooter/headerVisitor";
import SignInButton from "../../components/headerAndFooter/signInButton";
import Footer from "../../components/headerAndFooter/footer";

import TraineeTrainee from "../../components/visitor/traineesTrainees"; 
import TraineeTheme from "../../components/visitor/traineesThemes"; 
import TraineeQuiz from "../../components/visitor/traineesQuiz"; 
import TraineeResult from "../../components/visitor/traineesResults"; 


function VisitorByTrainees() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderVisitor /> </header>
        
        <main className="flex-1 flex flex-col items-center">
            <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center mt-4">
                Clients
            </h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 px-4 w-full">
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <TraineeTrainee />
                </div>
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <TraineeQuiz />
                </div>
                <div className="p-4 flex justify-center">
                    <TraineeResult />
                </div>
            </div>
            
        </main>
        
        <SignInButton />
        <Footer />
    </div>
  );
}

export default VisitorByTrainees;