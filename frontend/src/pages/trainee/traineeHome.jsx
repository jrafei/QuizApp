import React, { useState, useEffect } from "react";
import HeaderTrainee from "../../components/headerAndFooter/headerTrainee";
import Footer from "../../components/headerAndFooter/footer";
import Pagination from "../../components/headerAndFooter/Pagination";

import NavbarQuiz from "../../components/trainee/navbarQuiz";
import ListQuiz from "../../components/trainee/listQuiz";

import axios from 'axios';


function TraineeHome() {
    const [quizNb, setQuizNb] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const [userProfile, setUserProfile] = useState({});

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("authToken");

    const fetchUser = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/users/${userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setUserProfile(res.data);
        } catch (error) {
            console.log("Error fetching rankings:", error);
        }
    };

    useEffect(() => {
        if (userId && token) {
            fetchUser();
        }

    }, [userId, token]);
    
    // Handle page changes
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderTrainee /> </header>
            <NavbarQuiz searchQuery={searchQuery} setSearchQuery={setSearchQuery}/>
            <h1 className="text-3xl font-bold text-gray-800 mt-6 mb-8 text-center">
                Hello {userProfile.firstname} ! 
            </h1>
            <main className="flex-1 flex-col items-center">
                <div className="m-16">
                <ListQuiz
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

export default TraineeHome;