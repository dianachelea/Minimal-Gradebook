import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import studentService from "../services/studentService";

const StudentDetailsPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [student, setStudent] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStudent = async () => {
      try {
        const response = await studentService.getById(id);
        setStudent(response.student);
      } catch (err) {
        console.error("Error fetching student details:", err);
        setError("Could not fetch student details.");
        navigate("/students"); 
      }
    };
    fetchStudent();
  }, [id, navigate]);

  if (error) {
    return <p style={{ color: "red" }}>{error}</p>;
  }

  if (!student) return <p>Loading...</p>;

  return (
    <div>
      <h1>
        Details for {student.nume} {student.prenume}
      </h1>
      <p>Mean: {student.medie}</p>
      <p>Stage: {student.stare}</p>
    </div>
  );
};

export default StudentDetailsPage;
