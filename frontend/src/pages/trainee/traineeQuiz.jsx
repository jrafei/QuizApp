import React from "react";
import { useNavigate } from 'react-router-dom';

import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";

import CurrentQuiz from "../../components/trainee/currentQuiz";

function TraineeQuiz() {

    const navigate = useNavigate();
    
    const record = {
        name: 'Géographie - La Terre',
        nb_questions: 15,
        quiz: [
            {
                question: "What is the capital of France?",
                answer1: "Berlin",
                answer2: "Madrid",
                answer3: "Paris",
                answer4: "Rome"
            },
            {
                question: "Which planet is known as the Red Planet?",
                answer1: "Earth",
                answer2: "Mars",
                answer3: "Venus",
                answer4: "Jupiter"
            },
            {
                question: "Who wrote 'Romeo and Juliet'?",
                answer1: "Shakespeare",
                answer2: "Dickens",
                answer3: "Hemingway",
                answer4: "Fitzgerald"
            }
        ]
    };

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
                Quiz : {record.name}
            </h1>
            <main className="flex-1 flex flex-col justify-center items-center">
                <CurrentQuiz />
            </main>
            
            <div className="mt-auto flex justify-center w-full p-4 mb-2">
                <button 
                    onClick={() => navigate('/traineespace/endquiz')}
                    className="bg-blue-700 text-white text-italic px-12 py-4 rounded-lg hover:bg-blue-500">
                        Continue
                </button>
            </div>
            <Footer />
        </div>
    );
}

export default TraineeQuiz;