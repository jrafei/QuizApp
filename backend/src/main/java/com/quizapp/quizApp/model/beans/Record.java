package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_record", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "score", nullable = true)
    private int score;

    @Column(name = "duration", nullable = true)
    private int duration;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private User trainee;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;


    // Relation avec RecordAnswer (table intermédiaire)
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecordAnswer> recordAnswers; // Réponses choisies dans ce record

}
