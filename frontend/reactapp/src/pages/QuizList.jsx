import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const QuizList = () => {
    const navigate = useNavigate();
    const [quizzes, setQuizzes] = useState([]); // State to store quizzes
    const [error, setError] = useState(null);  // State to handle errors
    const [searchQuery, setSearchQuery] = useState(''); // State for search query

    useEffect(() => {
        // Check if the user is logged in
        const email = localStorage.getItem('userEmail');
        if (!email) {
            navigate('/login'); // Redirect to login page if not logged in
        } else {
            // Fetch the quizzes from the backend
            const fetchQuizzes = async () => {
                try {
                    const response = await axios.get('http://localhost:8080/quizzes');
                    setQuizzes(response.data);
                } catch (err) {
                    setError('Failed to load quizzes. Please try again later.');
                }
            };

            fetchQuizzes();
        }
    }, [navigate]);

    
    // Filter quizzes based on the search query
    const filteredQuizzes = quizzes.filter((quiz) =>
        quiz.name.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div>
            <h1>Liste des quizzes</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            
            {/* Search Bar */}
            <input
                type="text"
                placeholder="Rechercher un quiz..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                style={{ marginBottom: '20px', padding: '10px', width: '100%' }}
            />

            {/* Quiz List */}
            <ul>
                {filteredQuizzes.length > 0 ? (
                    filteredQuizzes.map((quiz) => (
                        <li key={quiz.id}>
                            <Link to={`/quizzes?${quiz.id}`}></Link>
                            <h3>{quiz.name}</h3>
                        </li>
                    ))
                ) : (
                    <p>Aucun quiz trouvé.</p>
                )}
            </ul>
        </div>
    );
};

export default QuizList;
