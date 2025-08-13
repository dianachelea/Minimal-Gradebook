import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import studentService from "../services/studentService";

const StudentsPage = ({ role }) => {
  const navigate = useNavigate();
  const [students, setStudents] = useState([]);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1); 
  const [pageSize] = useState(5);
  const [totalPages, setTotalPages] = useState(1);

  const fetchStudents = async () => {
    try {
      const data = await studentService.getAll(currentPage, pageSize);
      setStudents(data.students);
      setTotalPages(data.totalPages);
    } catch (err) {
      setError("Could not fetch students. Please try again later.");
    }
  };

  useEffect(() => {
    fetchStudents();
  }, [currentPage]);

  const handleDelete = async (id) => {
    try {
      await studentService.delete(id);
      fetchStudents(); 
    } catch (err) {
      console.error("Error deleting student:", err);
    }
  };

  if (error) {
    return <div style={{ color: "red" }}>{error}</div>;
  }

  return (
    <div>
      <h1>Students</h1>
      {role === "profesor" && (
        <button onClick={() => navigate("/students/add")}>Add Student</button>
      )}
      <ul>
        {students.length > 0 ? (
          students.map((student) => (
            <li key={student._links.self.href}>
              {student.nume} {student.prenume}
              <button
                onClick={() =>
                  navigate(`/students/${student._links.self.href.split("/").pop()}`)
                }
              >
                Details
              </button>
              {role === "profesor" && (
                <>
                  <button
                    onClick={() =>
                      navigate(`/students/edit/${student._links.self.href.split("/").pop()}`)
                    }
                  >
                    Edit
                  </button>
                  <button
                    onClick={() =>
                      handleDelete(student._links.self.href.split("/").pop())
                    }
                  >
                    Delete
                  </button>
                </>
              )}
            </li>
          ))
        ) : (
          <p>No students found.</p>
        )}
      </ul>
      <div>
        <button
          disabled={currentPage === 1}
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
        >
          Previous
        </button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <button
          disabled={currentPage === totalPages}
          onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default StudentsPage;
