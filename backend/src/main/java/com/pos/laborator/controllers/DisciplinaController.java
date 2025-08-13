package com.pos.laborator.controllers;

import com.pos.laborator.data.Disciplina;
import com.pos.laborator.data.Student;
import com.pos.laborator.services.DisciplinaService;
import com.pos.laborator.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/gradebook/lectures")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    @PostMapping
    public ResponseEntity<?> addDisciplina(@RequestBody Disciplina disciplina, @RequestHeader("Authorization") String authorization) {
        logger.info("S-a primit o cerere pentru a adauga o disciplina noua: {}", disciplina.getDenumire_program_studii());
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
            logger.info("Cerere de creare a disciplinei primita");
            Disciplina savedDisciplina = disciplinaService.addDisciplina(disciplina);
            logger.info("Disciplina a fost adaugata cu succes, ID: {}", savedDisciplina.get_id());
            EntityModel<Disciplina> entityModel = EntityModel.of(savedDisciplina,
                    linkTo(methodOn(DisciplinaController.class).getDisciplinaById(savedDisciplina.get_id().toString(),authorization)).withSelfRel(),
                    linkTo(methodOn(DisciplinaController.class).getAllDiscipline(authorization)).withRel("toate-disciplinele"));
            return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);  // 201
        } catch (RuntimeException e) {
            logger.error("Eroare la adaugarea disciplinei: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict.");  // 409
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDisciplinaById(@PathVariable String id,@RequestHeader("Authorization") String authorization) {
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

        logger.info("S-a primit o cerere pentru a obtine disciplina cu ID-ul: {}", id);
        Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(id);
        return disciplina.map(d -> {
            logger.info("Disciplina a fost gasita: {}", d.getDenumire_program_studii());
            EntityModel<Disciplina> entityModel = EntityModel.of(d,
                    linkTo(methodOn(DisciplinaController.class).getAllDiscipline(authorization)).withRel("toate-disciplinele"));
            return ResponseEntity.ok(entityModel);
        }).orElseGet(() -> {
            logger.warn("Disciplina cu ID-ul {} nu a fost gasita", id);

            Disciplina errorDisciplina = new Disciplina();
            errorDisciplina.setDenumire_program_studii("Disciplina nu a fost gasita.");
            EntityModel<Disciplina> errorModel = EntityModel.of(errorDisciplina);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorModel);  // 404
        });
    }

    @GetMapping
    public ResponseEntity<?> getAllDiscipline(@RequestHeader("Authorization") String authorization) {
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizatorul nu are dreptul de a vedea disciplinele");
        }
        logger.info("S-a primit o cerere pentru a obtine toate disciplinele");
        List<Disciplina> discipline = disciplinaService.getAllDiscipline();
        if (discipline.isEmpty()) {
            logger.warn("Nu s-au gasita discipline");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404
        }
        logger.info("Se returneaza {} discipline", discipline.size());

        List<EntityModel<Disciplina>> disciplinaModels = discipline.stream()
                .map(d -> EntityModel.of(d,
                        linkTo(methodOn(DisciplinaController.class).getDisciplinaById(d.get_id().toString(),authorization)).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Disciplina>> collectionModel = CollectionModel.of(disciplinaModels,
                linkTo(methodOn(DisciplinaController.class).getAllDiscipline(authorization)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDisciplina(@PathVariable String id, @RequestBody Disciplina disciplina,@RequestHeader("Authorization") String authorization) {
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
        logger.info("S-a primit o cerere pentru actualizarea disciplinei cu ID-ul: {}", id);
        try {
            Optional<Disciplina> updatedDisciplina = disciplinaService.updateDisciplina(id, disciplina);
            if (updatedDisciplina.isPresent()) {
                logger.info("Disciplina cu ID-ul {} a fost actualizata cu succes", id);
                EntityModel<Disciplina> entityModel = EntityModel.of(updatedDisciplina.get(),
                        linkTo(methodOn(DisciplinaController.class).getDisciplinaById(id,authorization)).withSelfRel(),
                        linkTo(methodOn(DisciplinaController.class).getAllDiscipline(authorization)).withRel("toate-disciplinele"));
                return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);  // 201
            } else {
                logger.warn("Disciplina cu ID-ul {} nu a fost gasita pentru actualizare", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina nu a fost gasita.");  // 404
            }
        } catch (RuntimeException e) {
            logger.error("Eroare la actualizarea disciplinei: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict.");  // 409
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable String id,@RequestHeader("Authorization") String authorization) {
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
        logger.info("S-a primit o cerere pentru stergerea disciplinei cu ID-ul: {}", id);
        boolean deleted = disciplinaService.deleteDisciplina(id);
        if (deleted) {
            logger.info("Disciplina cu ID-ul {} a fost stearsa cu succes", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204
        } else {
            logger.warn("Disciplina cu ID-ul {} nu a fost gasita pentru stergere", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina nu a fost gasita.");  // 404
        }
    }
}
