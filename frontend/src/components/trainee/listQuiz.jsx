import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Ensure axios is imported


const ListQuiz = ({searchQuery}) => {
    const navigate = useNavigate();
    const [quizzes, setQuizzes] = useState([]); // State for quizzes
    const [error, setError] = useState(null); // State for error handling

    // Fetch quizzes on component mount
    useEffect(() => {
        const fetchQuizzes = async () => {
            try {
                const response = await axios.get("http://localhost:8080/quizzes", {
                    headers: { 
                        Authorization: "Bearer ${token}" //TODO : Implémenter l'accès au token
                    }
                });
                setQuizzes(response.data);
            } catch (err) {
                setError("Failed to load quizzes. Please try again later.");
            }
        };

        fetchQuizzes();
    }, []); // Empty dependency array ensures it runs once on mount

    // Filter quizzes based on the search query
    const filteredQuizzes = quizzes.filter((quiz) =>
        quiz.name.toLowerCase().includes(searchQuery.toLowerCase())
    );


    return (
        <div className="overflow-x-auto">
            <h1 className="text-xl font-bold text-gray-800 mt-6 mb-8">
                Available Quizzes
            </h1>

            {error ? (
                <p className="text-red-500 text-center mb-4">{error}</p>
            ) : (
                <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Name
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Theme
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Number of Questions
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Creation Date
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-auto">
                                Action
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredQuizzes.map((quiz, index) => (
                            <tr key={index} className="odd:bg-white even:bg-gray-50">
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.name}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.theme}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.questions}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.creationDate}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    <button
                                        onClick={() => navigate("/traineespace/quiz")}
                                        className="bg-blue-700 text-white px-6 py-2 rounded hover:bg-blue-600"
                                    >
                                        Start
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            <h1 className="text-xl font-bold text-red-500 mt-6 mb-8 text-center">
                ADD PAGINATION
            </h1>
        </div>
    );
};

export default ListQuiz;
