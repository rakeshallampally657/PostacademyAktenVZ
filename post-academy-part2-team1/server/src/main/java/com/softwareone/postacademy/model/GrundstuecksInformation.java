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
public class GrundstuecksInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grundStueckId;

    private Long gemarkung;

    private Long flur;

    private String flurStueck;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date vertragsBeginn;

    private Long laufzeit;

    private String vertragsNummer;

    private String anmerkung;
}