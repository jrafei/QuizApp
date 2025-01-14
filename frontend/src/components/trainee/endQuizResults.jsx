import React, { useEffect, useRef } from "react";
import axios from "axios";
import CurrentQuiz from './currentQuiz';

const EndQuizResults = ({record, quiz}) => {

    if (!quiz) {
        return <div></div>
    }
    
    const findAnswers = () => {
        const selectedAnswers = [];
    
        quiz.forEach((question, index) => {
            const selectedAnswer = record.answerIds[index];
            const answer = question.answers.find((ans) => ans.id === selectedAnswer);  
    
            if (answer) {
                selectedAnswers.push(answer); 
            }
        });
    
        return selectedAnswers;
    };

    const answers = findAnswers();
    const scoreCalculation = () => {
        let score = 0;
        for (const ans of answers) {
            
        if (ans && ans.isCorrect) {
                score += 1;
            }
        };
        return score;
    };

    return (
        <div className="flex flex-col items-center p-4">
           
            <h2 className="text-xl font-bold mb-6 text-center">Results</h2>
            
            <div className="w-full max-w-md flex flex-col items-start bg-grey-400 p-6 rounded-lg shadow-lg">
                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Score:
                    </label>
                    <p className="text-gray-800 text-lg">{scoreCalculation()}/ {quiz.length}</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Runtime:
                    </label>
                    <p className="text-gray-800 text-lg">{Math.floor(record.duration)} s</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Your Answers:
                    </label>
                    <ul className="list-inside pl-4 text-gray-800">
                        {quiz.map((question, index) => (
                            <li key={index} className="mb-2">
                                <strong>{question.label}</strong> : {answers[index].label}
                            </li>
                        ))}

                    </ul>
                </div>
            </div>
        </div>
    );
};

export default EndQuizResults;
