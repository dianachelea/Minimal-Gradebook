import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import studentService from "../services/studentService";
import disciplineService from "../services/disciplineService"; 

const EditStudentPage = () => {
  const { id } = useParams();
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
    const fetchStudentAndDisciplines = async () => {
      try {
        const studentData = await studentService.getById(id);
        setFormData(studentData.student);

        const disciplineData = await disciplineService.getAll();
        const disciplineList = disciplineData.map(
          (discipline) => discipline.denumire_program_studii
        );
        setDisciplines(disciplineList);
      } catch (err) {
        console.error("Error fetching data:", err);
      }
    };

    fetchStudentAndDisciplines();
  }, [id]);

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
      await studentService.update(id, finalFormData);
      navigate("/students");
    } catch (err) {
      console.error("Error updating student:", err);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "15px",
        maxWidth: "400px",
        margin: "auto",
      }}
    >
      <h1>Edit Student</h1>

      <label>
        First name:
        <input
          type="text"
          value={formData.prenume}
          onChange={(e) =>
            setFormData({ ...formData, prenume: e.target.value })
          }
          required
        />
      </label>

      <label>
        Second name:
        <input
          type="text"
          value={formData.nume}
          onChange={(e) => setFormData({ ...formData, nume: e.target.value })}
          required
        />
      </label>

      <label>
        Stage:
        <input
          type="text"
          value={formData.stare}
          onChange={(e) => setFormData({ ...formData, stare: e.target.value })}
          required
        />
      </label>

      <label>
        Media:
        <input
          type="number"
          value={formData.medie}
          onChange={(e) => setFormData({ ...formData, medie: e.target.value })}
          required
        />
      </label>

      <fieldset>
        <legend>Grades:</legend>
        {disciplines.length > 0 ? (
          disciplines.map((discipline) => (
            <div key={discipline} style={{ marginBottom: "10px" }}>
              <label>
                {discipline}:
                <input
                  type="number"
                  min="1"
                  max="10"
                  value={formData.note[discipline] || ""}
                  onChange={(e) =>
                    handleNoteChange(discipline, e.target.value)
                  }
                />
              </label>
            </div>
          ))
        ) : (
          <p>Loading lectures...</p>
        )}
      </fieldset>

      <button type="submit">Save</button>
    </form>
  );
};

export default EditStudentPage;
