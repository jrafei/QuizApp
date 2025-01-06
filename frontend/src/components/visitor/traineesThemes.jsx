import React, { useState, useEffect } from "react";
import axios from "axios";

const TraineeTheme = ({traineeId, onSelectTheme, selectedThemeId}) => {

    const [themes, setThemes] = useState([]); 
    
    const fetchThemes = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/records/stats/trainees/${traineeId}/themes`);
            setThemes(res.data);
            console.log(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        if (traineeId) {
            fetchThemes();
        }
    }, [traineeId])

    return (
        <div className="overflow-x-auto w-full">
            <h2 className="text-xl font-bold mb-4 text-center">Themes</h2>
            {themes.length > 0 ? (     
            <table className="table-auto w-full text-left">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="align-middle px-4 py-2 flex-grow">
                            Titles
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {themes.map((theme) => (
                        <tr key={theme.id} className="border bg-gray-100">
                            <td className="px-4 py-2 text-center pl-2">  
                                {theme.name}
                            </td>
                            <td className="border text-center align-middle px-4 py-2 w-[100px]">
                                <button 
                                    onClick={() => onSelectTheme(theme.id)}
                                    className={`py-2 px-4 rounded-lg mb-2 w-full ${
                                        selectedThemeId === theme.id
                                            ? "bg-black text-white"
                                            : "bg-blue-700 text-white hover:bg-blue-400"
                                    }`}>
                                    Results
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            ) : (
                <p>No theme available for this trainee.</p>
            )}

        </div>
    );
};

export default TraineeTheme;