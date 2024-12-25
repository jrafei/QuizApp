import React from 'react';

const RecordsResults = () => {

    const quizDetails = {
        score: 17,
        nb_questions: 20,
        runtime: "10.22",
        questions: [
            "What is your name?", 
            "What is your favorite color?", 
        ],
        answers: [
            "John", 
            "Blue", 
        ]
    };

    return (
        <div className="flex flex-col items-center p-4">
           
            <h2 className="text-xl font-bold mb-6 text-center">Results</h2>
            
            <div className="w-full max-w-md flex flex-col items-start bg-grey-400 p-6 rounded-lg shadow-lg">
                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Score:
                    </label>
                    <p className="text-gray-800 text-lg">{quizDetails.score}/ {quizDetails.nb_questions}</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Runtime:
                    </label>
                    <p className="text-gray-800 text-lg">{quizDetails.runtime} minutes</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Your Answers:
                    </label>
                    <ul className="list-inside pl-4 text-gray-800">
                        {quizDetails.questions.map((question, index) => (
                            <li key={index} className="mb-2">
                                <strong>{question}</strong>: {quizDetails.answers[index]}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default RecordsResults;
