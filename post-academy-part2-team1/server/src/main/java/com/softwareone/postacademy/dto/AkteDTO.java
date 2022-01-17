package com.softwareone.postacademy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softwareone.postacademy.model.Aenderung;
import com.softwareone.postacademy.model.Akte;
import com.softwareone.postacademy.model.GrundstuecksInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AkteDTO {
    private long akteId;

    private Long stadtBezirk;

    private Long kennZiffer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date aktenBeginn;

    private Long letzteHeftnummer;

    private Long neueHeftnummer;

    private boolean almosenKasten;

    private String betreff;

    private String sonstigeAnmerkungen;

    private boolean inPapierKorb;

    private List<GrundstuecksInformation> allGrundstuecksInformationen;

    private List<Aenderung> allAenderungen = null;

    public AkteDTO(Akte akte) {
        this.akteId = akte.getAkteId();
        this.stadtBezirk = akte.getStadtBezirk();
        this.kennZiffer = akte.getKennZiffer();
        this.aktenBeginn = akte.getAktenBeginn();
        this.letzteHeftnummer = akte.getLetzteHeftnummer();
        this.neueHeftnummer = akte.getNeueHeftnummer();
        this.almosenKasten = akte.isAlmosenKasten();
        this.betreff = akte.getBetreff();
        this.sonstigeAnmerkungen = akte.getSonstigeAnmerkungen();
        this.allGrundstuecksInformationen = akte.getAllGrundstuecksInformationen();
        this.allAenderungen = akte.getAllAenderungen();
        this.inPapierKorb = akte.isInPapierKorb();
    }
}