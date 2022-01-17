package com.softwareone.postacademy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aenderung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long aenderungId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date datum;

    private String aktion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="NUTZER_Fk")
    private Nutzer nutzer;
}