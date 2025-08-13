import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import studentService from "../services/studentService";
import disciplineService from "../services/disciplineService";

const AddStudentPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    prenume: "",
    nume: "",
    stare: "",
    note: {},
    medie: "",
  });
  const [disciplines, setDisciplines] = useState([]);

  useEffect(() => {
    const fetchDisciplines = async () => {
      try {
        const response = await disciplineService.getAll();
        const disciplinesList = response.map(
          (discipline) => discipline.denumire_program_studii
        );
        setDisciplines(disciplinesList);
      } catch (err) {
        console.error("Error fetching disciplines:", err.message);
      }
    };
    fetchDisciplines();
  }, []);

  const handleNoteChange = (discipline, value) => {
    setFormData((prevFormData) => {
      const updatedNotes = { ...prevFormData.note };
      if (value) {
        updatedNotes[discipline] = value;
      } else {
        delete updatedNotes[discipline];
      }
      return { ...prevFormData, note: updatedNotes };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const filteredNotes = Object.fromEntries(
      Object.entries(formData.note).filter(([_, value]) => value !== "")
    );
    const finalFormData = { ...formData, note: filteredNotes };
    try {
      await studentService.add(finalFormData);
      navigate("/students");
    } catch (err) {
      console.error("Error adding student:", err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", maxWidth: "400px", margin: "auto" }}>
      <label>
        First name:
        <input type="text" value={formData.prenume} onChange={(e) => setFormData({ ...formData, prenume: e.target.value })} required />
      </label>

      <label>
        Second name:
        <input type="text" value={formData.nume} onChange={(e) => setFormData({ ...formData, nume: e.target.value })} required />
      </label>

      <label>
        Stage:
        <input type="text" value={formData.stare} onChange={(e) => setFormData({ ...formData, stare: e.target.value })} required />
      </label>

      <label>
        Mean:
        <input type="number" value={formData.medie} onChange={(e) => setFormData({ ...formData, medie: e.target.value })} required />
      </label>

      <fieldset>
        <legend>Grades:</legend>
        {disciplines.length > 0 ? (
          disciplines.map((discipline) => (
            <div key={discipline} style={{ marginBottom: "10px" }}>
              <label>
                {discipline}:
                <input type="number" min="1" max="10" onChange={(e) => handleNoteChange(discipline, e.target.value)} />
              </label>
            </div>
          ))
        ) : (
          <p>Loading lectures...</p>
        )}
      </fieldset>

      <button type="submit">Add Student</button>
    </form>
  );
};

export default AddStudentPage;
