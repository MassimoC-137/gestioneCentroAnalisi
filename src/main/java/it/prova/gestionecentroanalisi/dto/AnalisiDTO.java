package it.prova.gestionecentroanalisi.dto;

import java.time.LocalDateTime;

import it.prova.gestionecentroanalisi.model.Analisi;
import it.prova.gestionecentroanalisi.model.TipoAnalisi;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisiDTO {

	private Long id; 
	@NotNull(message="esito_buono.notnull")
	private Boolean esitoBuono; 
	@NotNull(message="tipo.notnull")
	private TipoAnalisi tipo; 
	@NotNull(message="data_ora.notnull")
	private LocalDateTime dataOra; 
	@NotNull(message="paziente.notnull")
	private UtenteDTO paziente; 
	
	public Analisi buildAnalisiModel() {
		return new Analisi(this.id, this.esitoBuono, this.tipo, this.dataOra, null);
	}
	
	public static AnalisiDTO buildAnalisiDTOFromModel(Analisi model) {
		return new AnalisiDTO(model.getId(), model.getEsitoBuono(), model.getTipo(), model.getDataOra(), UtenteDTO.buildUtenteDTOFromModel(model.getPaziente()));
	}
}
