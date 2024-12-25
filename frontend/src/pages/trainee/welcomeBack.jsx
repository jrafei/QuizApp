import React from "react";
import HeaderGeneral from "../../components/headerAndFooter/headerGeneral";
import Footer from "../../components/headerAndFooter/footer";

function WelcomeBack() {
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <header className="flex-0"> <HeaderGeneral /> </header>
            
            <main className="flex-1 flex flex-col items-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-16 text-center mt-32">
                    Welcome back! 
                </h1>
                <form className="w-full max-w-sm">

                    <h2 className="text-xl font-bold text-gray-800 mb-8 text-center mt-8">
                        Your account has been deactivated it on *date*
                    </h2>

                    <div className="text-center italic mb-4">
                        Press to activate 
                    </div>
                        
                    <div className="flex justify-center mb-8">
                        <button 
                            type="submit"
                            className="bg-blue-700 text-white py-2 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Reactivate
                        </button>
                    </div>
                    
                    <div className="text-center italic">
                        <strong>Then</strong>  enter the activation code sent to your email address
                    </div>

                    <form className="w-full max-w-sm">
                    <div className="mt-8 mb-8">
                        <label htmlFor="code" className="block text-gray-700 text-sm font-bold mb-2">Activation code</label>
                        <input 
                            type="code" 
                            id="code" 
                            name="code" 
                            placeholder="******" 
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required 
                        />
                    </div>

                    <div className="flex justify-center">
                        <button 
                            type="submit"
                            className="bg-blue-700 text-white py-2 px-6 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Submit
                        </button>
                    </div>
                </form>

                </form>
                
            </main>
            <div className="mt-auto flex justify-end w-full p-4">
                <button className="bg-blue-700 text-white text-italic px-6 py-2 rounded-lg hover:bg-blue-500">
                    Back
                </button>
            </div>
            <Footer />
        </div>
      );
}

export default WelcomeBack;