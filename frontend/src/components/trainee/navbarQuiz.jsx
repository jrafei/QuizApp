import React, { useState } from 'react';

const NavbarQuiz = ({ searchQuery, setSearchQuery }) => {

    
    return (
        <header className="bg-gray-600 text-white flex justify-end items-center py-3 px-6">
            <div className="flex gap-4 italic mr-8">
                Search by : 
            </div>

             {/* Search Bar */}
             <input
                type="text"
                placeholder="Rechercher un quiz..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                style={{ marginBottom: '20px', padding: '10px', width: '100%' }}
                className="px-4 py-2 w-64 bg-gray-700 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />

        </header>
    );
}

export default NavbarQuiz;
