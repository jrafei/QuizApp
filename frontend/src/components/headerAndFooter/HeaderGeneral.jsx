import React from 'react';
import logo from '../../assets/logo.png';

const HeaderGeneral = () => {
    return (
        <header className="bg-black text-white flex justify-between items-center py-4 px-6">
            <div className="flex gap-4"> </div>   
            <div>
                <img src={logo} alt="Logo" className="h-10" />
            </div>
        </header>
    );
}

export default HeaderGeneral;
