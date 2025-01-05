import React, { useState, useEffect } from "react";
import axios from "axios";

// Graphique 
import { Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);


const ThemeTheme = ({ onSelectTheme, selectedThemeId}) => {

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

    // Chart prep
    const chartData = {
        labels: themes.map((theme) => theme.title), 
        datasets: [
            {
                label: "Choice Frequency (%)",
                data: themes.map((theme) => theme.frequency), 
                backgroundColor: "rgba(54, 162, 235, 0.6)", 
                borderColor: "rgba(54, 162, 235, 1)", 
                borderWidth: 1,
            },
        ],
    };

    const chartOptions = {
        responsive: true,
        plugins: {
            legend: {
                position: "top",
            },
            title: {
                display: false,
                text: "Most Frequently Chosen Themes",
            },
        },
    };
    
    return (
        <div className="flex flex-col items-center p-4">          
            <table className="table-auto w-full text-left">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="text-center align-middle px-4 py-2 w-1/5">
                            Name
                        </th>
                        <th className="text-center align-middle px-4 py-2 w-1/5">
                            Choice Frequency
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {themes.map((theme) => (
                        <tr key={theme.id} className="odd:bg-gray-100 even:bg-gray-100">
                            <td className="text-center align-middle px-4 py-2">
                                <button
                                    onClick={() => onSelectTheme(theme.id)}
                                    className={`py-4 px-6 rounded-lg mb-2 w-[300px] ${
                                        selectedThemeId === theme.id
                                            ? "bg-black text-white"
                                            : "bg-blue-700 text-white hover:bg-blue-400"
                                        }`}>
                                        {theme.title} 
                                </button>
                            </td>
                            <td className="text-center align-middle px-4 py-2">
                                {theme.frequency}%
                            </td> 
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="w-full mt-8 flex justify-center">
            <div className="w-full max-w-[800px] h-[400px]">
                <Bar data={chartData} options={chartOptions} />
            </div>
            </div>

        </div>
    );
    
};

export default ThemeTheme;
