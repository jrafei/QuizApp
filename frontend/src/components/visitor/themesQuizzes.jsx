import React, { useState, useEffect } from "react";
import axios from "axios";

const ThemeQuiz = ({ themeId, onSelectQuiz }) => {

    const [quizzes, setQuizzes] = useState([]); 
    
    const fetchQuizzes = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/records/stats/themes/byquiz/${themeId}`);
            setQuizzes(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        if (themeId) {
            fetchQuizzes();
        }
    }, [themeId])


    return (
        <div className="flex flex-col items-center p-4">
            {quizzes.length > 0 ? (      
            <table className="table-auto w-full text-left">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="text-center align-middle px-4 py-2 w-1/5">
                            Name
                        </th>
                        <th className="text-center align-middle px-4 py-2 w-1/5">
                            Avg Score
                        </th>
                        <th className="text-center align-middle px-4 py-2 w-1/5">
                            Avg Runtime
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {quizzes.map((quiz) => (
                        <tr key={quiz.id} className="odd:bg-gray-100 even:bg-gray-100">
                            <td className="text-center align-middle px-4 py-2">
                                <button
                                    onClick={() => onSelectQuiz(quiz.id)}
                                    className={`py-4 px-6 rounded-lg mb-2 w-[300px] ${
                                        themeId === quiz.id
                                            ? "bg-black text-white"
                                            : "bg-blue-700 text-white hover:bg-blue-400"
                                    }`}>
                                        {quiz.name}  ({quiz.questionCount} questions)
                                </button>
                            </td>
                            <td className="text-center align-middle px-4 py-2">
                                {quiz.meanscore}/ {quiz.questionCount}
                            </td>
                            <td className="text-center align-middle px-4 py-2">
                                {quiz.meanruntime} s
                            </td>  
                        </tr>
                    ))}
                </tbody>
            </table>
            ) : (
                <p>No quiz available for this theme.</p>
            )}
        </div>
    );
    
}

export default ThemeQuiz;
