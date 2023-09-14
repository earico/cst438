import React, {useState} from 'react';


function Problem(props) {
  const [alias, setAlias] = useState('');
  const [attempt, setAttempt] = useState('');
  const [message, setMessage] = useState('');


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
      props.postAttempt(attempt, alias);
    } else {
      setMessage("Your answer is not a valid integer.");
    }
  }


  const handleNext = (e) => {
    props.fetchProblem();
}


const msg = (props.message!=='') ? props.message : message;
const {a, b} = props.factors;
return (
  <div className="App">
   <h3>The problem is </h3>
   <h1>{a} x {b}</h1>


  <table>
    <tbody>
    <tr><td>
   <label htmlFor="attempt">Your answer</label>
   </td><td>
   <input type="text" name="attempt" value={attempt} 
          onChange={onChangeAttempt} />
   </td></tr>
   <tr><td>
   <label htmlFor="alias">Your alias name</label>
   </td><td>
 <input type="text" name="alias" value={alias} onChange={onChangeAlias} />
   </td></tr>
   </tbody>
   </table>
   
   <br/>
   <button onClick={handleSubmit}>Submit</button>
   <br/>
   <button onClick={handleNext}>Next Problem</button>
   
   <h3>{msg}</h3>
  </div>
)
}
export default Problem;
