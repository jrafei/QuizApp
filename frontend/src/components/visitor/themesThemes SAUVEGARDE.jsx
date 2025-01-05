import React, { useState, useEffect } from "react";
import axios from "axios";

const ThemeTheme = () => {

    const [themes, setThemes] = useState([]); 
    
    const fetchThemes = async () => {
        try {
            const res = await axios.get("http://localhost:8080/records/stats/themes/all");
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
                                Average Score
                            </th>
                            <th className="text-center align-middle border border-gray-300 px-4 py-2 w-1/5">
                                Avergae Runtime (min)
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
                                    {theme.meanScore || "N/A"}
                                </td>
                                <td className="text-center align-middle border border-gray-300 px-4 py-2">
                                    {theme.meanRunTime || "N/A"}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
        </div>
    );
};

export default ThemeTheme;



// @Override
// public List<ThemeStatsDTO> getStatsOfAllThemes() {

//         List<Theme> themeList = themeRepository.findAll(); // Récup tous les thèmes
//         List<ThemeStatsDTO> themeStatsList = new ArrayList<>(); // Liste pour stocker les statistiques des thèmes

//         for (Theme theme : themeList) {
//                 List<Quiz> quizList = quizRepository.findByThemeId(theme.getId());

//                 double totalScore = 0.0;
//                 double totalDuration = 0.0;
//                 int recordCount = 0;

//                 for (Quiz quiz : quizList) {
//                 List<Record> recordList = recordRepository.findByQuizId(quiz.getId());

//                         for (Record record : recordList) {
//                                 totalScore += record.getScore();
//                                 totalDuration += record.getDuration();
//                         }
//                         recordCount += recordList.size(); // Nb total d'enregistrements pour tous les quizs pour un theme donné
//                 }

//                 double meanScore = recordCount > 0 ? totalScore / recordCount : 0.0;
//                 double meanRunTime = recordCount > 0 ? totalDuration / recordCount : 0.0;

//                 ThemeStatsDTO themeStatsDTO = new ThemeStatsDTO(
//                         theme.getId(),
//                         theme.getTitle(),
//                         meanScore,
//                         meanRunTime
//                 );
//                 themeStatsList.add(themeStatsDTO);
//         }
//         return themeStatsList; 
// }