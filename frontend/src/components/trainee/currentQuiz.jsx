import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

const CurrentQuiz = ({quizId}) => {
    const navigate = useNavigate();
    const [questionId, setQuestionId] = useState(0);
    const [selectedAnswers, setSelectedAnswers] = useState({});
    const [score, setScore] = useState(0);
    const [error, setError] = useState(null);

    const [quiz, setQuiz] = useState(null);

    useEffect(() => {    
        // Fetch the quiz details based on the ID
        const fetchQuiz = async () => {
            const token = localStorage.getItem("authToken");
            try {
            
                const response = await axios.get("http://localhost:8080/questions", {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                    params: { quizId }
                });
                setQuiz(response.data || []);
            } catch (err) {
                setError('Failed to load questions. Please try again later.');
            }
        };

        fetchQuiz();
    }, [quizId]);

    const handleNextQuestion = () => {
        // Check the selected answer and update the score

        if (quiz[questionId].answers[selectedAnswers[questionId]].isCorrect) {
            console.log("previous score", score)
            setScore((prevScore) => prevScore + 1);
            console.log("new score", score)
        }

        // Move to the next question if available
        if (questionId < quiz.length - 1) {
            setQuestionId((prevId) => prevId + 1);
        } else {
            // Navigate to the end screen or show the final score
            localStorage.setItem("quizAnswers", JSON.stringify(selectedAnswers));
            localStorage.setItem("currentQuiz", JSON.stringify(quiz));
            localStorage.setItem("currentQuizId", quizId)
            navigate('/traineespace/endquiz');
        }
    };

    if (error) {
        return <p>{error}</p>;
    }

    if (!quiz) {
        return <p>Loading... </p>;
    }

    if (quiz.length === 0) {
        return <p>No quiz questions available.</p>;
    }

    return (
        <div className="overflow-x-auto p-4">
            <div className="space-y-6">
                <div key={questionId} className="mb-4">
                    <p className="font-semibold text-gray-700 mb-2">{quiz[questionId].label}</p>
                    <div className="space-y-2">
                        {quiz[questionId].answers.map((answerKey, idx) => (
                            <label key={idx} className="flex items-center space-x-2">
                                <input
                                    type="radio"
                                    name={`question-${quiz[questionId].label}`}
                                    value={answerKey.label}
                                    checked={selectedAnswers[questionId] === idx}
                                    onChange={() => {
                                        setSelectedAnswers((prevAnswers) => ({
                                            ...prevAnswers,
                                            [questionId]: idx, // Update answer for the current question
                                        }));
                                                                       
                                    }}
                                    className="form-radio text-blue-500"
                                />
                                <span>{answerKey.label}</span>
                            </label>
                        ))}
                    </div>
                        <button
                            onClick={handleNextQuestion}
                            disabled={!selectedAnswers[questionId]} // Disable button if no answer is selected
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:bg-gray-300"
                        >
                        {questionId < quiz.length - 1 ? "Next Question" : "Submit Quiz"}
                        </button>                
                    </div>
            </div>
        </div>
    );
}

export default CurrentQuiz;
