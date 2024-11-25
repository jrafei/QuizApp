package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name = "THEME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label", nullable = false)
    private String label;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes; // liste des quizs de theme : (orphanRemoval = true)  si un quiz est supprimé de la liste il sera supprimé dans toute la base de données
                                //  cascade : si un thème est supprimé, ses quizs associés seront supprimées également

}
