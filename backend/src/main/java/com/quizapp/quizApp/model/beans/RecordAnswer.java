package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_record_answer", updatable = false, nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    @JsonBackReference
    private Record record;  // L'association vers le Record

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    @JsonBackReference
    private Answer answer; // la réponse choisi
}
