import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

const CurrentQuiz = () => {
    const navigate = useNavigate();
    const [questionId, setQuestionId] = useState(0);
    const [selectedAnswer, setSelectedAnswer] = useState(""); // State for selected answer
    const [score, setScore] = useState(0); // Add this state

    /*const navigate = useNavigate(quizId);
    const [quiz, setQuiz] = useState(null);

    useEffect(() => {
        // Fetch the quiz details based on the ID
        const fetchQuiz = async () => {
            try {
                const response = await axios.get('http://localhost:8080/questions/${quizId}');
                setQuiz(response.data);
            } catch (err) {
                setError('Failed to load questions. Please try again later.');
            }
        };

        fetchQuiz();
    }, [quizId]);
*/

    const handleNextQuestion = () => {
        // Check the selected answer and update the score
        if (selectedAnswer === record.quiz[questionId].correct) {
            setScore((prevScore) => prevScore + 1);
        }

        // Move to the next question if available
        if (questionId < record.quiz.length - 1) {
            setQuestionId((prevId) => prevId + 1);
            setSelectedAnswer(""); // Reset selected answer
        } else {
            // Navigate to the end screen or show the final score
            navigate('/traineespace/endquiz')
        }
    };

    const record = {
        name: 'Quiz 2',
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
        <div className="overflow-x-auto p-4">
            <div className="space-y-6">
                <div key={questionId} className="mb-4">
                    <p className="font-semibold text-gray-700 mb-2">{record.quiz[questionId].question}</p>
                    <div className="space-y-2">
                        {['answer1', 'answer2', 'answer3', 'answer4'].map((answerKey, idx) => (
                            <label key={idx} className="flex items-center space-x-2">
                                <input
                                    type="radio"
                                    name={`question-${questionId}`}
                                    value={record.quiz[questionId][answerKey]}
                                    checked={selectedAnswer === record.quiz[questionId][answerKey]}
                                    onChange={(e) => setSelectedAnswer(e.target.value)} // Update selected answer
                                    className="form-radio text-blue-500"
                                />
                                <span>{record.quiz[questionId][answerKey]}</span>
                            </label>
                        ))}
                    </div>
                    <button
                        onClick={handleNextQuestion}
                        disabled={!selectedAnswer} // Disable button if no answer is selected
                        className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:bg-gray-300"
                    >
                    {questionId < record.quiz.length - 1 ? "Next Question" : "Submit Quiz"}
                </button>                </div>
            </div>
        </div>
    );
}

export default CurrentQuiz;
