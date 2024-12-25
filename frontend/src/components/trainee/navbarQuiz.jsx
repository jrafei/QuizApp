import React from 'react';

const NavbarQuiz = () => {
    return (
        <header className="bg-gray-600 text-white flex justify-end items-center py-3 px-6">
            <div className="flex gap-4 italic mr-8">
                Search by : 
            </div>

            <div className="flex gap-4">
                <button className="bg-gray-800 px-4 py-2 rounded hover:bg-gray-700">Theme</button>
                <button className="bg-gray-800 px-4 py-2 rounded hover:bg-gray-700">Name</button>
                <button className="bg-gray-800 px-4 py-2 rounded hover:bg-gray-700">Number of questions</button>
                <button className="bg-gray-800 px-4 py-2 rounded hover:bg-gray-700">Creation Date</button>
            </div>
        </header>
    );
}

export default NavbarQuiz;
