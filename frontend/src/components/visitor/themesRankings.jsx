import React, { useState, useEffect } from "react";
import axios from "axios";

const ThemeRanking = ({ quizId }) => {
    const [rankings, setRankings] = useState([]);
    const [viewMode, setViewMode] = useState("best");

    const fetchRankings = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/records/stats/themes/rankings/${quizId}`);
            setRankings(res.data);
        } catch (error) {
            console.log("Error fetching rankings:", error);
        }
    };

    useEffect(() => {
        if (quizId) {
            fetchRankings();
        }

    }, [quizId]);

    const handleBestScore = () => setViewMode("best");
    const handleAvgScore = () => setViewMode("avg");

    return (
        <div className="flex flex-col items-center">
            <div className="mb-16">
                <button
                    onClick={handleBestScore} 
                    className={`px-6 py-2 rounded-lg mr-6 ${
                        viewMode === "best" ? "bg-black text-white" : "bg-blue-700 text-white hover:bg-blue-500"
                    }`}>
                    Best Score
                </button>
                <button
                    onClick={handleAvgScore} 
                    className={`px-6 py-2 rounded-lg ${
                        viewMode === "avg" ? "bg-black text-white" : "bg-blue-700 text-white hover:bg-blue-500"
                    }`}>
                    Avg Score
                </button>
            </div>
 
            {rankings.length > 0 ? (
                viewMode === "best" ? (
                    <table className="table-auto w-full text-left">
                        <thead>
                            <tr className="bg-gray-100">
                                <th className="text-center align-middle px-4 py-2 w-1/5">Rank</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Name</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Score</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Runtime</th>
                            </tr>
                        </thead>
                        <tbody>
                            {rankings.map((ranking, index) => (
                                <tr key={ranking.id} className="odd:bg-gray-100 even:bg-gray-200">
                                    <td className="text-center align-middle px-4 py-2">{index + 1}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.firstname} {ranking.lastname}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.score}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.runtime}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <table className="table-auto w-full text-left">
                        <thead>
                            <tr className="bg-gray-100">
                                <th className="text-center align-middle px-4 py-2 w-1/5">Rank</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Name</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Avg Score</th>
                                <th className="text-center align-middle px-4 py-2 w-1/5">Avg Runtime</th>
                            </tr>
                        </thead>
                        <tbody>
                            {rankings.map((ranking, index) => (
                                <tr key={ranking.id} className="odd:bg-gray-100 even:bg-gray-200">
                                    <td className="text-center align-middle px-4 py-2">{index + 1}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.firstname} {ranking.lastname}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.averagescore}</td>
                                    <td className="text-center align-middle px-4 py-2">{ranking.averageruntime}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )
            ) : (
                <p>No rankings available for this quiz.</p>
            )}
        </div>
    );
};

export default ThemeRanking;
