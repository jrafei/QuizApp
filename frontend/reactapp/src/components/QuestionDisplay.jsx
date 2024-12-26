import { useState } from "react";

class QuestionDisplay extends React.Component {
    //let [idQuestion, setIdQuestion] = useState(props.id);
  
    constructor(props, questions) {
        super(props);
        this.state = {questionId : 0, selectedAnswer: null}
    }



    handleSubmit(e) {
        e.preventDefault();
        if (selectedAnswer === null) {
            return;
        }
        const isCorrect = true; //TODO : verification isCorrect
        /* TODO : implémenter l'incrémenteur de score*/
    };

    render() {
        return (
            <div>
                <h1>Quiz</h1>
                <p>{question.text}</p>
                <form onSubmit={handleSubmit}>
                    {question.options.map((option) => (
                        <div key={option.id}>
                            <label>
                                <input
                                    type="radio"
                                    name="answer"
                                    value={option.id}
                                    checked={selectedAnswer === option.id}
                                    onChange={() => setSelectedAnswer(option.id)}
                                />
                                {option.text}
                            </label>
                        </div>
                    ))}
                    <button type="submit" style={{ marginTop: '20px' }}>Validate</button>
                </form>
            </div>
        );
    }


}