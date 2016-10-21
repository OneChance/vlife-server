package app.speice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import app.account.entity.Account;
import app.speice.entity.Species;

@Component
public class SpeiceService {

	public static Map<Integer, Species> speciesInfo;

	public Species getSpeices(Account account) throws Exception {
		Long speciesId = account.getSpeciesId();
		Species species = this.getSpeciesInfo().get(speciesId);
		return species;
	}
	
	public Map<Integer, Species> getSpeciesInfo() {

		if (speciesInfo == null) {
			speciesInfo = new HashMap<Integer, Species>();
			List<Species> speciesList = speiceRepository.findAll();
			for (Species s : speciesList) {
				speciesInfo.put(s.getId().intValue(), s);
			}
		}
		return speciesInfo;
	}

	public Species getById(Long id){
		return speiceRepository.getOne(id);
	}


	@Resource
	SpeiceRepository speiceRepository;
}
