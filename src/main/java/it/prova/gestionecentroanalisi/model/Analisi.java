package it.prova.gestionecentroanalisi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="analisi")
public class Analisi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; 
	@Column(name = "esito_buono")
	private Boolean esitoBuono; 
	@Enumerated(EnumType.STRING)
	private TipoAnalisi tipo;
	@Column(name = "data_ora")
	private LocalDateTime dataOra;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paziente_id", nullable = true)
	private Utente paziente;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = true)
    private Utente medico;
}
