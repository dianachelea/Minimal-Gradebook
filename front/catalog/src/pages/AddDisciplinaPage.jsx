import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import disciplineService from "../services/disciplineService";

const AddDisciplinaPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    denumire_program_studii: "",
    an_de_studiu: "",
    nume_titular: "",
    grad_didactic: "",
  });
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await disciplineService.add(form);
      navigate("/disciplines");
    } catch (err) {
      setError("Could not add discipline. Please try again.");
    }
  };

  return (
    <div>
      <h1>Add Lecture</h1>
      {error && <div style={{ color: "red" }}>{error}</div>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name: </label>
          <input
            type="text"
            name="denumire_program_studii"
            value={form.denumire_program_studii}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Year of Study: </label>
          <input
            type="number"
            name="an_de_studiu"
            value={form.an_de_studiu}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Professor: </label>
          <input
            type="text"
            name="nume_titular"
            value={form.nume_titular}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Rank: </label>
          <input
            type="text"
            name="grad_didactic"
            value={form.grad_didactic}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit">Add Discipline</button>
      </form>
    </div>
  );
};

export default AddDisciplinaPage;
