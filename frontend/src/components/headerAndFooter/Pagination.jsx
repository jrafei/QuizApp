import React from "react";

const Pagination = ({ totalItems, itemsPerPage, currentPage, onPageChange }) => {
    const totalPages = Math.ceil(totalItems / itemsPerPage);

    const handleClick = (page) => {
        onPageChange(page);
    };

    return (
        <div className="pagination flex justify-center mt-4">
            {Array.from({ length: totalPages }, (_, index) => (
                <button
                    key={index + 1}
                    onClick={() => handleClick(index + 1)}
                    className={`mx-1 px-3 py-1 border rounded ${
                        currentPage === index + 1
                            ? "bg-blue-500 text-white"
                            : "bg-white text-blue-500"
                    } hover:bg-blue-400 hover:text-white`}
                >
                    {index + 1}
                </button>
            ))}
        </div>
    );
};

export default Pagination;
