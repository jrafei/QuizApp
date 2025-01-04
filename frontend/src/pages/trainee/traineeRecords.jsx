import React, {useState} from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Pagination from "../../components/headerAndFooter/Pagination";
import Footer from "../../components/headerAndFooter/footer";

import NavbarQuiz from "../../components/trainee/navbarQuiz";
import RecordsHistory from "../../components/trainee/recordsHistory";
import RecordsResults from "../../components/trainee/recordsResults";



function TraineeRecords() {
    const [recordNb, setRecordNb] = useState(null);
    const [selectedRecord, setSelectedRecord] = useState(null);
    const [selectedQuiz, setSelectedQuiz] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    
    // Handle page changes
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderTrainee /> </header>
        <NavbarQuiz searchQuery={searchQuery} setSearchQuery={setSearchQuery}/>
        <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
            Records
        </h1>
        <main className="flex-1 flex flex-col items-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4 px-4 w-full">
                <div className="border-r border-gray-300 p-4 flex flex-col items-center">
                    <RecordsHistory 
                        setRecordNb={setRecordNb} 
                        setSelectedQuiz={setSelectedQuiz}
                        searchQuery={searchQuery}
                        currentPage={currentPage}
                        itemsPerPage={itemsPerPage}
                    />
            
                    <div className="mt-4">
                        <Pagination
                            totalItems={recordNb} // initialized in pagination to ensure it is synchronised
                            itemsPerPage={itemsPerPage}
                            currentPage={currentPage}
                            onPageChange={handlePageChange}
                        />
                    </div>
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