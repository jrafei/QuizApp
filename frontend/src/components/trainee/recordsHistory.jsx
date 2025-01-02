import React, { useState, useEffect } from "react";
import axios from 'axios';
import { use } from "react";

const RecordsHistory = () => {
      const [recordList, setRecordList] = useState('');
      const [selectedQuiz, setSelectedQuiz] = useState();
    
    useEffect (() => {
        const getRecord = async () => {
            const token = localStorage.getItem("authToken");
            try {
            
                const response = await axios.get("http://localhost:8080/records/completed", {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                });

                setRecordList(response.data)
            }
            catch(error) {
                console.error("Failed to get quiz record:", error);
            }
        };
        getRecord()
        ;
    }, []);

    // Fetch details for a selected record
    const getQuizDetails = async (quizId) => {
        const token = localStorage.getItem("authToken");
        console.log(quizId)
        try {
            const response = await axios.get(`http://localhost:8080/questions?quizId=${quizId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });
            setSelectedQuiz(response.data || null);
            console.log("ok quiz",selectedQuiz)

        } catch (error) {
            console.error("Failed to load quiz details:", error);
            console.log(" not ok quiz",selectedQuiz)
            
        }
    };

    return (
        <div className="flex flex-col items-center p-4">
            <h2 className="text-xl font-bold mb-4">Quiz Records History</h2>

            {recordList.length > 0 ? (
                <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="text-center align-middle border border-gray-300 px-4 py-2">Quiz Name</th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2">Score</th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2">Duration (mins)</th>
                        </tr>
                    </thead>
                    <tbody>
                        {recordList.map((record, index) => (
                            <tr key={index} className="odd:bg-white even:bg-gray-50">
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">{record.quizName}</td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">{record.score}</td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">{record.duration}</td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    <button
                                        className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-700"
                                        onClick={() => getQuizDetails(record.quizId) }
                                    >
                                        Details
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p className="text-gray-500 mt-6">No records found.</p>
            )}
        </div>
    );
};

export default RecordsHistory;