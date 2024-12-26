import React, { useState, useEffect } from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";

import NavbarQuiz from "../../components/trainee/navbarQuiz";
import ListQuiz from "../../components/trainee/listQuiz";


function TraineeHome() {
  const [searchQuery, setSearchQuery] = useState('');
  
  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
        <header className="flex-0"> <HeaderTrainee /> </header>
        <NavbarQuiz searchQuery={searchQuery} setSearchQuery={setSearchQuery}/>
        <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
            Hello *name* ! 
        </h1>
        <main className="flex-1 flex-col items-center">
            <div className="m-16">
                <ListQuiz searchQuery={searchQuery}/>
            </div>
        </main>
        <Footer />
    </div>
  );
}

export default TraineeHome;