import React from 'react';

const CurrentQuiz = () => {

    const record = {
        name: 'Quiz 2',
        nb_questions: 15,
        quiz: [
            {
                question: "What is the capital of France?",
                answer1: "Berlin",
                answer2: "Madrid",
                answer3: "Paris",
                answer4: "Rome"
            }
        ]
    };

    return (
        <div className="overflow-x-auto p-4">
            <div className="space-y-6">
                {record.quiz.map((item, index) => (
                    <div key={index} className="mb-4">
                        <p className="font-semibold text-gray-700 mb-2">{item.question}</p>
                        <div className="space-y-2">
                            {['answer1', 'answer2', 'answer3', 'answer4'].map((answerKey, idx) => (
                                <label key={idx} className="flex items-center space-x-2">
                                    <input
                                        type="radio"
                                        name={`question-${index}`}
                                        value={item[answerKey]}
                                        className="form-radio text-blue-500"
                                    />
                                    <span>{item[answerKey]}</span>
                                </label>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default CurrentQuiz;
