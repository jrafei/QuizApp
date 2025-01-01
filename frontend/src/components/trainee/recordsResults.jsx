import React, { useEffect, useRef } from "react";
import axios from "axios";
import CurrentQuiz from './currentQuiz';

const RecordsResults = () => {
    const quiz = JSON.parse(localStorage.getItem("currentQuiz")) || [];
    const quizAnswersId = JSON.parse(localStorage.getItem("quizAnswersId")) || [];

    const hasPostExecuted = useRef(false); // UseRef to track POST request status
    const getQuizAnswersUUID = () => {
        return Object.values(quizAnswersId).map((answer) => answer.answerId);
    };
    

    const scoreCalculation = () => {
        let score = 0;
        quiz.forEach((question, index) => {
            const selectedAnswer = quizAnswersId[index];
            const answer = question.answers.find((ans) => ans.id === selectedAnswer.answerId);
            if (answer && answer.isCorrect) {
                score += 1;
            }
        });
        return score;
    };

    const quizRecord = {
        traineeId : localStorage.getItem("userId"),
        quizId : localStorage.getItem("currentQuizId"),
        answerIds : getQuizAnswersUUID(),
        duration: 10,
        status: "COMPLETED"
    };

    
    useEffect (() => {
        if (!hasPostExecuted.current) { // Only run the POST request once
            const postRecord = async () => {

                const token = localStorage.getItem("authToken");
                try {
                
                    const response = await axios.post("http://localhost:8080/records", quizRecord, {
                        headers: { 
                            Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json"
                        },
                    });
                }
                catch(error) {
                    console.error("Failed to post quiz record:", error);
                }
            };
        
            postRecord();
            hasPostExecuted.current = true; // Set flag to avoid further POST requests
        }
    }, [quiz, quizAnswersId]);

    console.log(quizRecord)

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
                    <p className="text-gray-800 text-lg">{quizRecord.duration} minutes</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Your Answers:
                    </label>
                    <ul className="list-inside pl-4 text-gray-800">
                        {quiz.map((question, index) => (
                            <li key={index} className="mb-2">
                                <strong>{question.label}</strong>: {question.answers[quizAnswersId[index].localId].label}
                            </li>
                        ))}

                    </ul>
                </div>
            </div>
        </div>
    );
};

export default RecordsResults;
