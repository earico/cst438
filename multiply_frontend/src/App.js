import './App.css';
import React, {useState, useEffect} from 'react';
import History from './components/History';
import Problem from './components/Problem';



function App() {
  const [message, setMessage] = useState('');
  const [alias, setAlias] = useState('');
  const [attempt, setAttempt] = useState('');
  const [factors, setFactors] = useState({a:41, b:26});
  const [history, setHistory] = useState([]);


  useEffect( () => {
    fetchProblem();
  }, [])

  const fetchProblem = () => {
    setMessage(''); 
    fetch('http://localhost:8080/multiplication/new')
    .then(response => response.json()) 
    .then(data => {
      setFactors({a:data.factorA, b:data.factorB});
    })
    .catch(err => console.log(err));
  }


  const fetchHistory = (alias) => {
    fetch(`http://localhost:8080/result/${alias}`)
    .then(response => response.json())
    .then(data => setHistory(data))
    .catch(err => console.error(err));    
  }

  const onChangeAttempt = (e) => {
    console.log("onChangeAttempt "+e.target.value);
    setAttempt(e.target.value);
  }


  const onChangeAlias = (e) => {
    console.log("onChangeAlias "+e.target.value);
    setAlias(e.target.value);
  }


  const handleSubmit = (e) => {
    if (/^[0-9]+$/.test(attempt)) {
      postAttempt();
    } else {
      setMessage("Your answer is not a valid integer.");
    }
  }

  const handleNext = (e) => {
    fetchProblem();
  }

  const postAttempt = (attempt, alias) => {
    fetch ('http://localhost:8080/result', 
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }, 
      body: JSON.stringify({factorA:factors.a, 
                            factorB:factors.b, 
                            alias: alias, 
                            attempt:attempt})
    })
    .then(response => response.json())
    .then(data => {
      if (data.correct) {
        setMessage('Correct.');
      } else {
        setMessage('Incorrect. Try again.');
      }
      if (alias!=='') fetchHistory(alias);
    })
    .catch(err => console.error(err));
  }



  return (
    <div className="App">
      <Problem 
              factors={factors}
              message={message} 
              postAttempt={postAttempt} 
              fetchProblem={fetchProblem} />
      {(history.length > 0)?  (<History data={history}/>) : ''}
    </div>
  )
}

export default App;
