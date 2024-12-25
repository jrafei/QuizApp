import React from 'react';

const ThemeQuiz = () => {
    return (
        <div className="flex flex-col items-center p-4 ">

            <h2 className="text-xl font-bold mb-4">Quizzes</h2>
            
            <div className="flex gap-4 w-full justify-center">
                <button className="bg-blue-700 text-white py-4 px-6 rounded-lg mb-4 hover:bg-blue-400 w-[300px]">
                    Quiz name 
                </button>
                <button className="bg-blue-800 text-white py-4 px-4 rounded-full mb-4 hover:bg-blue-600">
                    Scores
                </button>
                <button className="bg-blue-800 text-white py-4 px-4 rounded-full mb-4  hover:bg-blue-600">
                    Run times
                </button>
            </div>
        </div>
    );
}

export default ThemeQuiz;
