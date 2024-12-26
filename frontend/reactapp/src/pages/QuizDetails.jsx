import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const Quiz = () => {
    const { quizId } = useParams(); // Get the quiz ID from the URL
    const [quiz, setQuiz] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetch the quiz details based on the ID
        const fetchQuiz = async () => {
            try {
                const response = await axios.get('http://localhost:8080/questions/${quizId}');
                setQuiz(response.data);
            } catch (err) {
                setError('Failed to load quiz details. Please try again later.');
            }
        };

        fetchQuiz();
    }, [quizId]);

    return (
        <div>
            <h1>{quiz.name}</h1>
            {/* Add more quiz details here */}
        </div>
    );
};

export default Quiz;