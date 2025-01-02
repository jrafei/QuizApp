import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const CurrentQuiz = ({ quizId }) => {
    const navigate = useNavigate();
    const [questionId, setQuestionId] = useState(0);
    const [selectedAnswers, setSelectedAnswers] = useState([]);
    const [quiz, setQuiz] = useState(null);
    const [error, setError] = useState(null);
    const [startTime, setStartTime] = useState(null);
    const hasPostExecuted = useRef(false); // Track if the POST request has been executed

    // Start the quiz and set the start time
    const handleStartQuiz = () => {
        setStartTime(Date.now());
    };

    // Fetch the quiz details
    useEffect(() => {
        const fetchQuiz = async () => {
            const token = localStorage.getItem("authToken");
            try {
                const url = `http://localhost:8080/questions?quizId=${quizId}`;
                const response = await axios.get(url, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });
                setQuiz(response.data || []);
            } catch (err) {
                setError("Failed to load questions. Please try again later.");
            }
        };

        fetchQuiz();
        handleStartQuiz();

    }, [quizId]);

    const quizRecord = {
        traineeId: localStorage.getItem("userId"),
        quizId : quizId,
        answerIds: selectedAnswers,
        duration: 10,
        status: "COMPLETED",
    };

    const handleSubmitQuiz = (quizRecord) => {
        const endTime = Date.now();
        const timeSpent = endTime - startTime; // Time spent in milliseconds
    
        const timeSpentInSeconds = Math.floor(timeSpent / 1000);
        quizRecord.duration = timeSpentInSeconds
    }

    

    
    // Handle quiz submission (POST request)
    useEffect(() => {
        if (questionId === quiz?.length && !hasPostExecuted.current) {

            handleSubmitQuiz(quizRecord);
            
            console.log(selectedAnswers)
            const postRecord = async () => {
                const token = localStorage.getItem("authToken");
                try {
                    await axios.post("http://localhost:8080/records", quizRecord, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                        },
                    });
                    navigate("/traineespace/endquiz", { state: { quizRecord, quiz } });

                } catch (error) {
                    console.error("Failed to post quiz record:", error);
                }
            };

            postRecord();
            hasPostExecuted.current = true; // Prevent further POST requests
        }
    }, [questionId, quiz, navigate, quizRecord]);

    const handleNextQuestion = () => {
        if (questionId < quiz.length) {
            setQuestionId((prevId) => prevId + 1);
        }
    };

    const handleAnswerSelection = (answerId) => {
        // Update the selectedAnswers array
        setSelectedAnswers((prevAnswers) => {
            const newAnswers = [...prevAnswers];
            newAnswers[questionId] = answerId; // Assign the answerId to the current question index
            return newAnswers
        })
    };

    if (error) {
        return <p>{error}</p>;
    }

    if (!quiz) {
        return <p>Loading...</p>;
    }

    if (quiz.length === 0) {
        return <p>No quiz questions available.</p>;
    }

    return (
        <div className="overflow-x-auto p-4">
            <div className="space-y-6">
                {questionId < quiz.length && (
                    <div key={questionId} className="mb-4">
                        <p className="font-semibold text-gray-700 mb-2">{quiz[questionId].label}</p>
                        <div className="space-y-2">
                            {quiz[questionId].answers.map((answer, idx) => (
                                <label key={idx} className="flex items-center space-x-2">
                                    <input
                                        type="radio"
                                        name={`question-${quiz[questionId].label}`}
                                        value={answer.label}
                                        checked={selectedAnswers[questionId] === answer.id}
                                        onChange={() => handleAnswerSelection(answer.id)} // Update selected answer for this question
                                        className="form-radio text-blue-500"
                                    />
                                    <span>{answer.label}</span>
                                </label>
                            ))}
                        </div>
                        <button
                            onClick={handleNextQuestion}
                            disabled={selectedAnswers[questionId] === undefined} // Disable button if no answer is selected
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:bg-gray-300"
                        >
                            {questionId < quiz.length - 1 ? "Next Question" : "Submit Quiz"}
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default CurrentQuiz;
