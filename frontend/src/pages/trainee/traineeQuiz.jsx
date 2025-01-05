import React from "react";

import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";

import CurrentQuiz from "../../components/trainee/currentQuiz";

function TraineeQuiz() {
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
                Quiz : {/*record.name*/}
            </h1>
            <main className="flex-1 flex flex-col justify-center items-center">
                <CurrentQuiz />
            </main>
            <Footer />
        </div>
    );
}

export default TraineeQuiz;