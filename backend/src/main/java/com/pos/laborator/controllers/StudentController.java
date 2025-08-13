package com.pos.laborator.controllers;

import com.pos.laborator.data.Disciplina;
import com.pos.laborator.data.Student;
import com.pos.laborator.dto.StudentDto;
import com.pos.laborator.mapper.IStudentMapper;
import com.pos.laborator.repository.DisciplinaRepository;
import com.pos.laborator.repository.StudentRepository;
import com.pos.laborator.services.StudentService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/gradebook/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private IStudentMapper studentMapper;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentDto studentDto,@RequestHeader("Authorization") String authorization) {
        try {
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                logger.warn("Antetul Authorization lipseste sau este invalid.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
            }
            String token = authorization.substring(7);
            AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
            String validationMessage = authServiceClient.validate(token);

            String[] message= validationMessage.split(" ");
            String[] roleAndSub = message[1].split(":");
            String role= roleAndSub[0];


            if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
                logger.warn("Token-ul JWS este invalid sau expirat.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
            }
            logger.info("Token-ul JWS validat cu succes.");
            if (!"profesor".equals(role)) {
                logger.warn("Utilizatorul nu are rolul necesar: {}", role);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
            }
            logger.info("Cerere de creare a studentului primita");
            Student student = studentMapper.toStudent(studentDto);
            if (student.get_id() == null) {
                student.set_id(new ObjectId());
            }else
            {
                student.set_id(studentDto.get_id());
            }

            Student savedStudent = studentService.addStudent(student);
            logger.info("Student creat cu id-ul: {}", savedStudent.get_id());
            Link selfLink = Link.of("/api/gradebook/students/" + savedStudent.get_id()).withSelfRel();
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("student", savedStudent, "link", selfLink));
        } catch (RuntimeException e) {
            logger.error("Conflict", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id,@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }
        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
        }
        logger.info("Token-ul JWS validat cu succes.");
        if (!"profesor".equals(role) && !"student".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
        }
        logger.info("Cerere de GET a studentului dupa id: {}", id);
        Optional<Student> student = studentService.getStudentById(id);

        if (student.isPresent()) {
            Link selfLink = Link.of("/api/gradebook/students/" + id).withSelfRel();
            logger.info("Student gasit cu id-ul: {}", id);
            return ResponseEntity.ok(Map.of("student", student.get(), "link", selfLink));
        } else {
            logger.warn("Studentul nu a fost gasit: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studentul nu a fost gasit.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "students_per_page", defaultValue = "10") int studentsPerPage,
            @RequestParam(value = "name", required = false) String name,
            @RequestHeader("Authorization") String authorization) {
        logger.info("Cerere GETALL primita (page: {}, perPage: {}, name: {})", page, studentsPerPage, name);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }

        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token-ul JWS este invalid sau expirat.");
        }

        logger.info("Token-ul JWS validat cu succes.");

        if (name != null && !name.isEmpty()) {
            List<Student> studentsByName = studentService.getStudentsByName(name);
            if (studentsByName.isEmpty()) {
                logger.warn("Nici un student cu numele: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu s-a gasit nici un student.");
            }

        }

        if (page <= 0 || studentsPerPage <= 0) {
            logger.warn("Parametri de paginare invalizi.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Bad Request", "message", "Pagina si numarul de studenti pe pagina trebuie sa fie mai mari decat 0."));
        }

        Page<Student> studentsPage = studentService.getAllStudentsPaged(page, studentsPerPage);
        if (studentsPage.isEmpty()) {
            logger.warn("Nici un student gasit pe pagina specificata.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nici un student gasit pe pagina specificata.");
        }
        if (!"profesor".equals(role) && !"student".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nu ai destule drepturi.");
        }
        List<EntityModel<Student>> studentModels = studentsPage.stream()
                .map(s -> EntityModel.of(s,
                        linkTo(methodOn(StudentController.class).getStudentById(s.get_id().toString(), authorization)).withSelfRel()))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(StudentController.class).getAllStudents(page, studentsPerPage, name, authorization)).withSelfRel();

        PagedModel<EntityModel<Student>> pagedModel = PagedModel.of(studentModels, new PagedModel.PageMetadata(studentsPerPage, page, studentsPage.getTotalElements()));
        pagedModel.add(selfLink);

        logger.info("Studenti gasiti cu succes.");
        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody Student student,@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }
        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
        }
        logger.info("Token-ul JWS validat cu succes.");
        if (!"profesor".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
        }
        logger.info("Cerere de update student cu ID: {}", id);
        try {
            Optional<Student> updatedStudent = studentService.updateStudent(id, student);
            if (updatedStudent.isPresent()) {
                Link selfLink = Link.of("/api/gradebook/students/" + id).withSelfRel();
                logger.info("Studentul actualizat cu ID: {}", id);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("student", updatedStudent.get(), "link", selfLink));
            } else {
                logger.warn("Studentul nu a fost gasit : {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studentul nu a fost gasit.");
            }
        } catch (Exception e) {
            logger.error("Eroare in actualizarea studentului cu ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Nu s-a putut procesa.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id,@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }
        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
        }
        logger.info("Token-ul JWS validat cu succes.");
        if (!"profesor".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
        }
        logger.info("Cerere primita de stergere a studentului cu ID: {}", id);
        try {
            studentService.deleteStudent(id);
            logger.info("Studentul sters cu ID: {}", id);
            Link selfLink = Link.of("/api/gradebook/students/" + id).withSelfRel();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("student", getStudentById(id,authorization) , "link", selfLink));
        } catch (RuntimeException e) {
            logger.warn("Eroare de stergere a studentului cu id ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studentul nu a fost gasit.");
        }
    }

    @GetMapping("/{id}/lectures")
    public ResponseEntity<?> getLecturesForStudent(@PathVariable String id,@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }
        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
        }
        logger.info("Token-ul JWS validat cu succes.");
        if (!"profesor".equals(role) && !"student".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
        }
        logger.info("Cerere GET /{}/lectures primita.", id);
        Optional<Student> studentOpt = studentService.getStudentById(id);

        if (studentOpt.isEmpty()) {
            logger.warn("Studentul cu id-ul {} nu a fost gasit.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Studentul nu a fost gasit.");  // 404
        }

        Student student = studentOpt.get();
        List<Disciplina> discipline = disciplinaRepository.findAll();
        List<Disciplina> studentDiscipline = discipline.stream()
                .filter(d -> d.getStudenti() != null && d.getStudenti().stream()
                        .anyMatch(s -> s.get_id() != null && s.get_id().equals(student.get_id())))
                .toList();

        if (studentDiscipline.isEmpty()) {
            logger.info("Studentul cu id-ul {} nu are discipline.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Studentul nu are discipline.");  // 204
        }

        List<Map<String, Object>> disciplinesWithLinks = studentDiscipline.stream()
                .map(disciplina -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("disciplina", disciplina);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                            .getLecturesForStudent(id,authorization)).withSelfRel();
                    response.put("link", selfLink);
                    return response;
                })
                .collect(Collectors.toList());

        logger.info("Disciplinele pentru studentul cu id-ul {} returnate cu succes.", id);
        return ResponseEntity.ok(disciplinesWithLinks);  // 200
    }

    @DeleteMapping("/{id}/lectures/{lid}")
    public ResponseEntity<?> removeLectureFromStudent(@PathVariable String id, @PathVariable String lid, @RequestHeader("Authorization") String authorization ) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Antetul Authorization lipseste sau este invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Antetul Authorization este lipsa sau invalid.");
        }
        String token = authorization.substring(7);
        AuthServiceClient authServiceClient = new AuthServiceClient("localhost", 50051);
        String validationMessage = authServiceClient.validate(token);

        String[] message= validationMessage.split(" ");
        String[] roleAndSub = message[1].split(":");
        String role= roleAndSub[0];


        if ("Token invalid".equals(validationMessage)|| "Token expirat si invalidat".equals(validationMessage)||"Token invalid: este in blacklist".equals(validationMessage)) {
            logger.warn("Token-ul JWS este invalid sau expirat.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token-ul JWS este invalid sau expirat.");
        }
        logger.info("Token-ul JWS validat cu succes.");
        if (!"profesor".equals(role)) {
            logger.warn("Utilizatorul nu are rolul necesar: {}", role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a crea un student.");
        }
        logger.info("Cerere DELETE /{}/lectures/{} primita.", id, lid);

        Optional<Student> studentOpt = studentService.getStudentById(id);
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(new ObjectId(lid));

        if (studentOpt.isEmpty()) {
            logger.warn("Studentul cu id-ul {} nu a fost gasit.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Studentul nu a fost gasit.");  // 404
        }

        if (disciplinaOpt.isEmpty()) {
            logger.warn("Disciplina cu id-ul {} nu a fost gasita.", lid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Disciplina nu a fost gasita.");  // 404
        }

        Student student = studentOpt.get();
        Disciplina disciplina = disciplinaOpt.get();

        if (disciplina.getStudenti() != null) {
            disciplina.getStudenti().removeIf(s -> s.get_id().equals(student.get_id()));
            disciplinaRepository.save(disciplina);
            logger.info("Studentul cu id-ul {} a fost eliminat din disciplina cu id-ul {}.", id, lid);
        }

        if (student.getNote() != null) {
            student.getNote().remove(disciplina.getDenumire_program_studii());
            studentRepository.save(student);
        }

        logger.info("Disciplina cu id-ul {} a fost stearsa cu succes din studentul cu id-ul {}.", lid, id);
        return ResponseEntity.status(HttpStatus.OK).body("Disciplina a fost stearsa din student.");  // 200
    }

}