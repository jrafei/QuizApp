package AI13.SpringBoot.models.beans;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L; // champ de contrôle de version pour la sérialisation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ) // auto-icrementer l'id
    private int id;
    private String firstname;
    private String lastname;
    /*
    private String email;
    private String password;
    private String company;
    private String phone;
    private Timestamp creationDate;
    */
    private Boolean isActive;


}