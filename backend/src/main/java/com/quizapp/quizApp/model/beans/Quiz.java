package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_quiz", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Version
    private int version;

    @NotBlank(message = "Le nom du quiz est obligatoire.")
    @Size(max = 100, message = "Le nom du quiz ne doit pas dépasser 100 caractères.")
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false; // Valeur par défaut

    @Column(name = "position", nullable = true)
    private Integer position; // Ordre du quiz dans un thème

//    //@ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne
//    @JoinColumn(name = "creator_id", nullable = false)
//    @JsonBackReference
//    private User creator; // Utilisateur qui a créé le quiz

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @JsonBackReference
    private Theme theme; // Relation avec le thème


    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Question> questions;//= new ArrayList<>(); // Liste des questions associées


    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now(); // Date de création du quiz


    public int getNbQuestion(){
       return questions.size();
    }


}
