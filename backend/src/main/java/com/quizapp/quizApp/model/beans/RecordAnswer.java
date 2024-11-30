//package com.quizapp.quizApp.model.beans;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class RecordAnswer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "record_id", nullable = false)
//    private Record record;
//
//    @ManyToOne
//    @JoinColumn(name = "answer_id", nullable = false)
//    private Answer answerId;
//}
