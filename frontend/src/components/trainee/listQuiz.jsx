import React from 'react';
import { useNavigate } from 'react-router-dom';

const ListQuiz = () => {
    const navigate = useNavigate();
    const quizzes = [
        { name: 'Quiz 1', theme: 'Math', questions: 10, creationDate: '2024-12-01' },
        { name: 'Quiz 2', theme: 'Science', questions: 15, creationDate: '2024-12-05' },
        { name: 'Quiz 3', theme: 'History', questions: 20, creationDate: '2024-12-10' },
    ];

    return (

        
        <div className="overflow-x-auto">
            
            <h1 className="text-xl font-bold text-gray-800 mt-6 mb-8 ">
                Available quizzes
            </h1>

            <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                <thead>
                    <tr className="bg-gray-200">
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Name</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Theme</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Number of Questions</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Creation Date</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-auto">Action</th>
                    </tr>
                </thead>
                <tbody>
                    {quizzes.map((quiz, index) => (
                        <tr key={index} className="odd:bg-white even:bg-gray-50">
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.name}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.theme}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.questions}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.creationDate}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">
                                <button 
                                    onClick={() => navigate('/traineespace/quiz')}
                                    className="bg-blue-700 text-white px-6 py-2 rounded hover:bg-blue-600">
                                        Start
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            
            <h1 className="text-xl font-bold text-red-500 mt-6 mb-8 text-center">
                AJOUTER PAGINATION
            </h1>

        </div>
    );
}

export default ListQuiz;
