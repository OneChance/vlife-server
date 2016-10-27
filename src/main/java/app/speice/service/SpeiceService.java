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

	public static Map<Long, Species> speciesInfo;

	public Species getSpeices(Account account) throws Exception {
		Long speciesId = account.getSpeciesId();
		Species species = this.getSpeciesInfo().get(speciesId);
		return species;
	}
	
	public Map<Long, Species> getSpeciesInfo() {

		if (speciesInfo == null) {
			speciesInfo = new HashMap<Long, Species>();
			List<Species> speciesList = speiceRepository.findAll();
			for (Species s : speciesList) {
				speciesInfo.put(s.getId(), s);
			}
		}
		return speciesInfo;
	}

	public Species getById(Long id){
		return speiceRepository.getOne(id);
	}

	public int getRandomMax(){
		return speiceRepository.getRandomMax();
	}

	public Long getSpeiceByRandomValue(Integer randomValue){
		return speiceRepository.getSpeiceByRandomValue(randomValue);
	}

	@Resource
	SpeiceRepository speiceRepository;
}
