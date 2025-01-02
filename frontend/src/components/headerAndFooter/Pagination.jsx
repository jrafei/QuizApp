// Pagination.js
import React from 'react';

const Pagination = ({ totalItems, itemsPerPage, currentPage, onPageChange }) => {
    totalItems = localStorage.getItem("recordNb") // weird fix of bad synchronisation
    const totalPages = Math.ceil(totalItems / itemsPerPage);

    const handleClick = (pageNumber) => {
        if (pageNumber < 1 || pageNumber > totalPages) return;
        onPageChange(pageNumber);
    };

    const pageNumbers = [];
    for (let i = 1; i <= totalPages; i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="flex justify-center space-x-4 mt-4">
            <button
                className="px-4 py-2 bg-gray-500 text-white rounded disabled:opacity-50"
                disabled={currentPage === 1}
                onClick={() => handleClick(currentPage - 1)}
            >
                Prev
            </button>
            {pageNumbers.map((number) => (
                <button
                    key={number}
                    className={`px-4 py-2 rounded ${currentPage === number ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}
                    onClick={() => handleClick(number)}
                >
                    {number}
                </button>
            ))}
            <button
                className="px-4 py-2 bg-gray-500 text-white rounded disabled:opacity-50"
                disabled={currentPage === totalPages}
                onClick={() => handleClick(currentPage + 1)}
            >
                Next
            </button>
        </div>
    );
};

export default Pagination;
