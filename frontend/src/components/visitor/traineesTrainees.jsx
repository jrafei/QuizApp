import React, { useState, useEffect } from "react";
import axios from "axios";


const TraineeTrainee = ({onSelectTrainee, selectedTraineeId, selectedButton, setSelectedButton}) => {

    const [trainees, setTrainees] = useState([]); 
    
    const fetchTrainees = async () => {
        try {
            const res = await axios.get("http://localhost:8080/records/stats/trainees/all");
            setTrainees(res.data);
            console.log(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchTrainees();
    }, [])

    return (
        <div className="overflow-x-auto w-full">
            <h2 className="text-xl font-bold mb-4 text-center">Trainees</h2> 
            <table className="table-auto w-full text-left">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="align-middle px-4 py-2 flex-grow">
                            Names
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {trainees.map((trainee) => (
                        <tr key={trainee.id} className="border bg-gray-100">

                            <td className="px-4 py-2 text-center pl-2">
                                {trainee.firstname} {trainee.lastname}
                            </td>

                            <td className="border text-center align-middle px-4 py-2 w-[100px]">
                                <button 
                                    onClick={() => {
                                        onSelectTrainee(trainee.id);
                                        setSelectedButton('quiz');
                                    }}
themeId
                                    className={`text-center align-middle py-2 px-4 rounded-lg w-full" ${
                                        selectedButton === 'quiz' && selectedTraineeId === trainee.id
                                        ? "bg-black text-white"
                                        : "bg-blue-700 hover:bg-blue-400 text-white"
                                    }`}>
                                    Quizzes
                                </button>
                            </td>

                            <td className="border text-center align-middle px-4 py-2 w-[100px]">
                                <button 
                                    onClick={() => {
                                        onSelectTrainee(trainee.id);
                                        setSelectedButton('theme');
                                    }}

                                    className={`text-center align-middle py-2 px-4 rounded-lg w-full" ${
                                        selectedButton === 'theme' && selectedTraineeId === trainee.id
                                        ? "bg-black text-white"
                                        : "bg-blue-700 hover:bg-blue-400 text-white"
                                    }`}>
                                    Themes
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default TraineeTrainee;