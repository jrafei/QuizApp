import React, {useState} from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";

import NavbarQuiz from "../../components/trainee/navbarQuiz";
import RecordsHistory from "../../components/trainee/recordsHistory";
import RecordsResults from "../../components/trainee/recordsResults";



function TraineeRecords() {
    const [selectedRecord, setSelectedRecord] = useState(null);
    const [selectedQuiz, setSelectedQuiz] = useState(null);

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderTrainee /> </header>
        <NavbarQuiz />
        <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
            Records
        </h1>
        <main className="flex-1 flex flex-col items-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4 px-4 w-full">
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <RecordsHistory setSelectedRecord={setSelectedRecord} setSelectedQuiz={setSelectedQuiz}/>
                </div>
                <div className="border-r border-gray-300 p-4 flex justify-center">
                    <RecordsResults record={selectedRecord} quiz={selectedQuiz}/>
                </div>
            </div>
        </main>
        <Footer />
    </div>
  );
}

export default TraineeRecords;