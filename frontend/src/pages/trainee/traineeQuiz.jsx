import React from "react";
import { useNavigate } from 'react-router-dom';

import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";

import CurrentQuiz from "../../components/trainee/currentQuiz";

function TraineeQuiz() {

    const navigate = useNavigate();

     // Extract the query string
     const queryParams = new URLSearchParams(location.search);
     const quizId = queryParams.get("index");

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
                Quiz : {/*record.name*/}
            </h1>
            <main className="flex-1 flex flex-col justify-center items-center">
                <CurrentQuiz quizId={quizId} />
            </main>
            <Footer />
        </div>
    );
}

export default TraineeQuiz;