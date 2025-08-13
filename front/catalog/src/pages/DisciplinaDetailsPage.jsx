import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import disciplineService from "../services/disciplineService";

const DisciplinaDetailsPage = () => {
  const { id } = useParams();
  const [discipline, setDiscipline] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDiscipline = async () => {
      try {
        const data = await disciplineService.getById(id);
        setDiscipline(data); 
      } catch (err) {
        console.error("Error fetching lectures details:", err.message);
        setError("Could not fetch lectures details. Please try again later.");
      }
    };

    fetchDiscipline();
  }, [id]);

  if (error) {
    return <div style={{ color: "red" }}>{error}</div>;
  }

  if (!discipline) {
    return <div>Loading...</div>;
  }
  console.log(discipline);

  return (
    <div>
      <h1>Lectures Details</h1>
      <p>
        <strong>Name:</strong> {discipline.denumire_program_studii || "N/A"}
      </p>
      <p>
        <strong>Year of Study:</strong> {discipline.an_de_studiu || "N/A"}
      </p>
      <p>
        <strong>Professor:</strong> {discipline.nume_titular || "N/A"}
      </p>
    </div>
  );
};

export default DisciplinaDetailsPage;
