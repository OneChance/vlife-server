package app.speice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import app.account.entity.Account;
import app.base.DatabaseService;
import app.speice.entity.Species;

@Component
public class SpeiceService extends DatabaseService {

	public static Map<Integer, Species> speciesInfo;
	
	public SpeiceService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}

	public Species getSpeices(Account account) throws Exception {
		Integer speciesId = account.getSpeciesId();
		Species species = this.getSpeciesInfo().get(speciesId);
		return species;
	}
	
	public Map<Integer, Species> getSpeciesInfo() {

		if (speciesInfo == null) {
			speciesInfo = new HashMap<Integer, Species>();
			List<Species> speciesList = gets(Species.class);
			for (Species s : speciesList) {
				speciesInfo.put(s.getId().intValue(), s);
			}
		}
		return speciesInfo;
	}
}
