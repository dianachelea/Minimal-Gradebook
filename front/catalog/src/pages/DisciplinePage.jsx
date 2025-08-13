import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import disciplineService from "../services/disciplineService";

const DisciplinePage = ({ role }) => {
  const navigate = useNavigate();
  const [disciplines, setDisciplines] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDisciplines = async () => {
      try {
        const response = await disciplineService.getAll();
        setDisciplines(response);
      } catch (err) {
        setError("Could not fetch lectures.");
      }
    };
    fetchDisciplines();
  }, []);

  const handleDelete = async (id) => {
    try {
      await disciplineService.delete(id);
      setDisciplines(disciplines.filter((d) => d._links.self.href.split("/").pop() !== id));
    } catch (err) {
      setError("Could not delete lectures.");
    }
  };

  if (error) {
    return <div style={{ color: "red" }}>{error}</div>;
  }

  return (
    <div>
      <h1>Disciplines</h1>
      {role === "profesor" && (
        <button onClick={() => navigate("/disciplines/add")}>Add Discipline</button>
      )}
      <ul>
        {disciplines.map((discipline) => {
          const id = discipline._links.self.href.split("/").pop();
          return (
            <li key={id}>
              {discipline.denumire_program_studii}
              <button onClick={() => navigate(`/disciplines/details/${id}`)}>Details</button>
              {role === "profesor" && (
                <>
                  <button onClick={() => navigate(`/disciplines/edit/${id}`)}>Edit</button>
                  <button onClick={() => handleDelete(id)}>Delete</button>
                </>
              )}
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default DisciplinePage;
