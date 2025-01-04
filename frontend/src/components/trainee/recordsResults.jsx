import React, { useEffect, useRef } from "react";
import axios from "axios";
import CurrentQuiz from './currentQuiz';

const RecordsResults = ({record, quiz}) => {

    if (!quiz) {
        return <div></div>
    }

    return (
        <div className="flex flex-col items-center p-4">
           
            <h2 className="text-xl font-bold mb-6 text-center">Results</h2>
            
            <div className="w-full max-w-md flex flex-col items-start bg-grey-400 p-6 rounded-lg shadow-lg">
                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Score:
                    </label>
                    <p className="text-gray-800 text-lg">{record.score}/ {quiz.length}</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Runtime:
                    </label>
                    <p className="text-gray-800 text-lg">{Math.floor(record.duration / 60)} minutes</p>
                </div>
            </div>
        </div>
    );
};

export default RecordsResults;
