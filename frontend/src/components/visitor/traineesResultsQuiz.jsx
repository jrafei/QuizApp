import React, { useState, useEffect } from "react";
import axios from "axios";


const TraineeResultQuiz = ({ traineeId, quizId }) => {
    const [results, setResults] = useState([]);

    const fetchRankings = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/records/stats/trainees/${traineeId}/quizs/${quizId}`);
            setResults(res.data);
            console.log(res.data)
        } catch (error) {
            console.log("Error fetching results:", error);
        }
    };

    useEffect(() => {
        if (traineeId && quizId) {
            fetchRankings();
        }

    }, [traineeId, quizId]);
    
    return (
        <div className="flex flex-col items-center p-4 ">

            <h2 className="text-xl font-bold mb-4">Results</h2>

            <div className="w-full max-w-md flex flex-col items-start bg-grey-400 p-6 rounded-lg shadow-lg">
                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Average score: {results.averageScore}
                    </label>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Best score: {results.bestScore}
                    </label>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Worst score: {results.worstScore}
                    </label>
                </div>
            </div>
            
        </div>
    );
};

export default TraineeResultQuiz;
