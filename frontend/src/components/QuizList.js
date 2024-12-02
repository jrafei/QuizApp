import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

const QuizList = () => {
    const [quizzes, setQuizzes] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [quizzesPerPage] = useState(5); // Nombre de quiz par page
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchQuizzes = async () => {
            try {
                setLoading(true);
                const response = await axios.get("http://localhost:8080/quizzes"); // Remplacez par l'URL de votre API
                setQuizzes(response.data); // Assurez-vous que la réponse correspond au format attendu
                setLoading(false);
            } catch (error) {
                console.error("Erreur lors de la récupération des quiz:", error);
                setLoading(false);
            }
        };

        fetchQuizzes();
    }, []);

    // Gestion de la pagination
    const indexOfLastQuiz = currentPage * quizzesPerPage;
    const indexOfFirstQuiz = indexOfLastQuiz - quizzesPerPage;
    const currentQuizzes = quizzes.slice(indexOfFirstQuiz, indexOfLastQuiz);

    const handleNextPage = () => {
        if (currentPage < Math.ceil(quizzes.length / quizzesPerPage)) {
            setCurrentPage(currentPage + 1);
        }
    };

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    if (loading) {
        return <p>Chargement des quiz...</p>;
    }

    return (
        <div>
            <h2>Liste des Quiz</h2>
            <ul>
                {currentQuizzes.map((quiz) => (
                    <li key={quiz.id}>
                        <h3>{quiz.name}</h3>
                        <p>Thème : {quiz.theme?.name || "N/A"}</p>
                        <Link to={`/quizzes/${quiz.id}/start`}>Démarrer le quiz</Link>
                    </li>
                ))}
            </ul>

            {/* Pagination */}
            <div>
                <button onClick={handlePreviousPage} disabled={currentPage === 1}>
                    Précédent
                </button>
                <span> Page {currentPage} </span>
                <button
                    onClick={handleNextPage}
                    disabled={currentPage === Math.ceil(quizzes.length / quizzesPerPage)}
                >
                    Suivant
                </button>
            </div>
        </div>
    );
};

export default QuizList;
