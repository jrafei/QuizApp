import React, { useState, useEffect } from "react";
import axios from "axios";

const ThemeTheme = () => {

    const [themes, setThemes] = useState([]); 
    
    const fetchThemes = async () => {
        try {
            const res = await axios.get("http://localhost:8080/themes");
            setThemes(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchThemes();
    }, [])
    
    
    return (
        <div className="flex flex-col items-center p-4 ">

            <h2 className="text-xl font-bold mb-4">Themes</h2>
            
            <table className="table-auto w-full border-collapse border border-gray-300 text-left">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Name
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Scores
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Run times
                            </th>
                        </tr>
                    </thead>


                    <tbody>
                        {themes.map((theme, index) => (
                            <tr key={index} className="odd:bg-white even:bg-gray-50">
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    <button className="bg-blue-700 text-white py-4 px-6 rounded-lg mb-4 hover:bg-blue-400 w-[300px]">
                                        {theme.title} 
                                    </button>
                                </td> 
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {theme.score || "N/A"}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {theme.runtime || "N/A"}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
        </div>
    );
};

export default ThemeTheme;
