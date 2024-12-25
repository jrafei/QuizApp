import React from 'react';
import logo from '../../assets/logo.png';

const HeaderVisitor = () => {
    return (
        <header className="bg-black text-white flex justify-between items-center py-4 px-6">
            <div className="flex gap-4">
                <button className="bg-black px-4 py-2 rounded hover:bg-gray-600">Home</button>
                <button className="bg-black px-4 py-2 rounded hover:bg-gray-600">Themes</button>
                <button className="bg-black px-4 py-2 rounded hover:bg-gray-600">Our clients</button>
            </div>
            <div>
                <img src={logo} alt="Logo" className="h-10" />
            </div>
        </header>
    );
}

export default HeaderVisitor;
