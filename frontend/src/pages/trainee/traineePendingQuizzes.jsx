import React, { useState, useEffect } from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";
import Pagination from "../../components/headerAndFooter/Pagination";

import NavbarQuiz from "../../components/trainee/navbarQuiz";
import PendingListQuiz from "../../components/trainee/pendingListQuiz";


function traineePendingQuizzes() {
    const [quizNb, setQuizNb] = useState(null);
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
            <main className="flex-1 flex-col items-center">
                <div className="m-16">
                <PendingListQuiz
                    setQuizNb={setQuizNb}
                    searchQuery={searchQuery}
                    currentPage={currentPage}
                    itemsPerPage={itemsPerPage}
                />
                <Pagination
                    totalItems={quizNb}
                    itemsPerPage={itemsPerPage}
                    currentPage={currentPage}
                    onPageChange={handlePageChange}
                />
                </div>
            </main>
            <Footer />
        </div>
    );
}

export default traineePendingQuizzes;