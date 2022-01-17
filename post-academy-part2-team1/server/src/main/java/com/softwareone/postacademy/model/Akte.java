package com.softwareone.postacademy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AKTE")
public class Akte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long akteId;

    private Long stadtBezirk;

    private Long kennZiffer ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date aktenBeginn;

    private Long letzteHeftnummer;

    private Long neueHeftnummer;

    private boolean almosenKasten;

    private String betreff;

    private String sonstigeAnmerkungen;

    private boolean inPapierKorb;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="AKTE_Fk")
    private List<GrundstuecksInformation> allGrundstuecksInformationen = null;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "AKTE_Fk")
    private List<Aenderung> allAenderungen=null;

}