package it.prova.gestionecentroanalisi.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.gestionecentroanalisi.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuoloDTO {

	private Long id;
	private String descrizione;
	private String codice;

	public static RuoloDTO buildRuoloDTOFromModel(Ruolo ruoloModel) {
		return new RuoloDTO(ruoloModel.getId(), ruoloModel.getDescrizione(), ruoloModel.getCodice());
	}

	public static List<RuoloDTO> createRuoloDTOListFromModelSet(Set<Ruolo> modelListInput) {
		return modelListInput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());

	}

	public static List<RuoloDTO> createRuoloDTOListFromModelList(List<Ruolo> modelListinput) {
		return modelListinput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());
	}

}
