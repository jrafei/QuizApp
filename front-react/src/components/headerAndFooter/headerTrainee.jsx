import React from 'react';

const Header = () => {
    return (
        <header className="bg-blue-800 text-white flex justify-between items-center py-4 px-6">
            <div className="text-xl font-semibold">
                Test your skills
            </div>
            <div>
                <img
                    src="../../assets/logo.png"
                    alt="Logo"
                    className="h-10"
                />
            </div>
        </header>
    );
}

export default Header;
