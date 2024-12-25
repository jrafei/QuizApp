import React from 'react';

const RecordsHistory = () => {

    const quizzes = [
        { name: 'Quiz 1', theme: 'Math', questions: 10, creationDate: '2024-12-01' },
        { name: 'Quiz 2', theme: 'Science', questions: 15, creationDate: '2024-12-05' },
        { name: 'Quiz 3', theme: 'History', questions: 20, creationDate: '2024-12-10' },
    ];

    return (
        <div className="flex flex-col items-center p-4 ">
            <h2 className="text-xl font-bold mb-4">History</h2>


            <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                <thead>
                    <tr className="bg-gray-200">
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Name</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Theme</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Number of Questions</th>
                        <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">Creation Date</th>

                    </tr>
                </thead>
                <tbody>
                    {quizzes.map((quiz, index) => (
                        <tr key={index} className="odd:bg-white even:bg-gray-50">
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 flex items-center justify-center">
                                <button className="bg-blue-800 text-white py-4 px-6 rounded-lg hover:bg-blue-400 w-[300px]">
                                    {quiz.name}
                                </button>
                            </td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.theme}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.questions}</td>
                            <td className="text-center align-middle border border-gray-300 px-4 py-2 text-center">{quiz.creationDate}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            
            <h1 className="text-xl font-bold text-red-500 mt-6 mb-8 text-center">
                AJOUTER PAGINATION
            </h1>
        </div>
    );
};

export default RecordsHistory;
