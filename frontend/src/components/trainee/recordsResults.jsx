import React from 'react';
import CurrentQuiz from './currentQuiz';

const RecordsResults = () => {
    const quiz = JSON.parse(localStorage.getItem("currentQuiz"));
    const quizAnswers = JSON.parse(localStorage.getItem("quizAnswers"));

    const scoreCalculation = () => {
        let score = 0;
        quiz.forEach((question, index) => {
            if (question.answers[quizAnswers[index]].isCorrect) {
                score += 1;
            }
        });
        return score;
    };

    const quizRecord = {
        duration: "10.22",
        score: scoreCalculation(),
        quizId : localStorage.getItem("CurrentQuizId"),
        answersId : quizAnswers,
        traineeId : ""
    };

    
    
    /*useEffect (() => {
        const postRecord = async () => {
            const token = localStorage.getItem("authToken");
            try {
            
                const response = await axios.post("http://localhost:8080/records", {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                    params: { quizId }
                });
            }
            catch {

            }
        }
    })*/

    return (
        <div className="flex flex-col items-center p-4">
           
            <h2 className="text-xl font-bold mb-6 text-center">Results</h2>
            
            <div className="w-full max-w-md flex flex-col items-start bg-grey-400 p-6 rounded-lg shadow-lg">
                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Score:
                    </label>
                    <p className="text-gray-800 text-lg">{/*quizRecord.score*/}/ {/*quiz.length*/}</p>
                </div>;

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Runtime:
                    </label>
                    <p className="text-gray-800 text-lg">{/*quizRecord.duration*/} minutes</p>
                </div>

                <div className="mb-4 w-full">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Your Answers:
                    </label>
                    <ul className="list-inside pl-4 text-gray-800">
                        
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default RecordsResults;
