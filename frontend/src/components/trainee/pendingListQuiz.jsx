import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Ensure axios is imported


const PendingListQuiz = ({ setQuizNb, searchQuery, currentPage, itemsPerPage }) => {
    const navigate = useNavigate();
    const [quizzes, setQuizzes] = useState([]); 
    const [records, setRecords] = useState([]);
    const [themes, setThemes] = useState([]); 
    const [filteredQuizzes, setFilteredQuizzes] = useState([]);
    const [error, setError] = useState(null); 

    // Fetch quizzes on component mount
    useEffect(() => {
        const token = localStorage.getItem("authToken");
        const fetchQuizzes = async () => {
            try {
                const themesResponse = await axios.get(`http://localhost:8080/themes`, {
                    headers: { 
                        Authorization: `Bearer ${token}`
                    }
                });
                setThemes(themesResponse.data);

                const quizzesResponse = await axios.get("http://localhost:8080/records/pending-quizzes", {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setQuizzes(quizzesResponse.data);
                setQuizNb(quizzesResponse.data.length);

                const recordsResponse = await axios.get("http://localhost:8080/records/pending", {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setRecords(recordsResponse.data)

            } catch (err) {
                setError("Failed to load quizzes. Please try again later.");
            }
        };

        fetchQuizzes();
    }, []);

    const findTheme = (quizThemeId) => {

        const theme = themes.find((theme) => theme.id === quizThemeId);
        return theme ? theme.title : "Unknown Theme"; 
    };

    const launchQuiz = (quiz) => {
        console.log(records)
        const record = records.find((rec) => rec.quizId = quiz.id);
        console.log(record);
        navigate(`/traineespace/quiz`, {
            state: { record: record, quizId: quiz.id }
        });
    };

    // Filter quizzes based on searchQuery
    useEffect(() => {
        const filtered = quizzes.filter(quiz =>
            quiz.name.toLowerCase().includes(searchQuery.toLowerCase())
        );
        setFilteredQuizzes(filtered);
    }, [searchQuery, quizzes]);

    // Paginate the filtered quizzes
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentQuizzes = filteredQuizzes.slice(startIndex, startIndex + itemsPerPage);

    return (
        <div className="overflow-x-auto">

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
                                Creation Date
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-auto">
                                Action
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentQuizzes.map(quiz => (
                        <tr key={quiz.id} className="quiz-item">
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.name}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {findTheme(quiz.themeId)}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {quiz.creationDate}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    <button
                                        onClick={() => launchQuiz(quiz)}
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

        </div>
    );
};

export default PendingListQuiz;
