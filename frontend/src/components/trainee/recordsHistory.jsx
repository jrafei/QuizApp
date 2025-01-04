import React, { useState, useEffect } from "react";
import axios from 'axios';
import { use } from "react";

const RecordsHistory = ({ setRecordNb, setSelectedRecord, setSelectedQuiz, searchQuery, currentPage, itemsPerPage }) => {
    const [recordList, setRecordList] = useState([]);
    const [filteredRecords, setFilteredRecords] = useState([]);
    const [themes, setThemes] = useState([]); 
      
    
    useEffect (() => {
        const getRecord = async () => {
            const token = localStorage.getItem("authToken");
            try {
                const themesResponse = await axios.get(`http://localhost:8080/themes`, {
                    headers: { 
                        Authorization: `Bearer ${token}`
                    }
                });
                setThemes(themesResponse.data);
            
                const response = await axios.get("http://localhost:8080/records/completed", {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                });

                setRecordList(response.data)
                setRecordNb(response.data.length)
            }
            catch(error) {
                console.error("Failed to get quiz record:", error);
            }
        };
        getRecord()
        ;
    }, [setRecordNb]);

    // Fetch details for a selected record
    const getQuizDetails = async (record) => {
        const quizId = record.quizId
        const token = localStorage.getItem("authToken");

        try {

            const quizResponse = await axios.get(`http://localhost:8080/questions?quizId=${quizId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
            setSelectedRecord(record || null)
            setSelectedQuiz(quizResponse.data || null);

        } catch (error) {
            console.error("Failed to load quiz details:", error);
            
        }
    };

    useEffect(() => {
        const filtered = recordList.filter(record =>
            record.quizName.toLowerCase().includes(searchQuery.toLowerCase())
        );
        setFilteredRecords(filtered);
    }, [searchQuery, recordList]);

    const findTheme = (quizThemeId) => {

        const theme = themes.find((theme) => theme.id === quizThemeId);
        return theme ? theme.title : "Unknown Theme"; 
    };

    // Paginate the filtered quizzes
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentRecords = filteredRecords.slice(startIndex, startIndex + itemsPerPage);
    

    return (
        <div className="flex flex-col items-center p-4">
            <h2 className="text-xl font-bold mb-4">Quiz Records History</h2>

            {recordList.length > 0 ? (
                <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="text-center align-middle border border-gray-300 px-4 py-2">Quiz Name</th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2">Theme</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentRecords.map(record => (
                            <tr key={record.recordId} className="quiz-item">
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">{record.quizName}</td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">{findTheme(record.themeId)}</td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    <button
                                        className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-700"
                                        onClick={() => getQuizDetails(record) }
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