import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from 'react-router-dom';

import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";
import EndQuizResults from "../../components/trainee/EndQuizResults";


function TraineeQuizEnd() {
    const navigate = useNavigate();
    const [record, setRecord] = useState("");
    const [quiz, setQuiz] = useState();

    const location = useLocation();

    useEffect(() => {
        setRecord(location.state?.quizRecord || null);
        setQuiz(location.state?.quiz || null);
    }, [location.state]);

    if (!record) {
        return <p>No quiz record available.</p>;
    }

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
                Quiz : {record.name}
            </h1>
            <main className="flex-1 flex flex-col justify-center items-center">
                <EndQuizResults record={record} quiz={quiz} />
            </main>
            
            <div className="mt-auto flex justify-center w-full p-4 mb-2">
                <button
                    onClick={() => navigate('/traineespace/records')}
                    className="bg-blue-700 text-white text-italic px-12 py-4 rounded-lg hover:bg-blue-500">
                        End
                </button>
            </div>
            <Footer />
        </div>
    );
}

export default TraineeQuizEnd;