package app.game.service;

import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import app.account.entity.Account;
import app.base.DatabaseService;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;


@Service
public class GameService extends DatabaseService {

	public GameService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}

	public Integer reincarnation(Integer soul) {
		Random random = new Random();
		Integer max = getRandomMax();
		Integer randomValue = Math.min(random.nextInt(max) % (max - 1 + 1) + 1
				+ soul, max);
		return getSpeiceByRandomValue(randomValue);
	}

	public Integer getRandomMax() {
		String sql = "select max(ratioEnd) from species";
		return getForType(sql, Integer.class);
	}

	public Integer getSpeiceByRandomValue(Integer randomValue) {
		String sql = "select id from vlife.species where ratioStart<="
				+ randomValue + " and ratioEnd>=" + randomValue;
		return getForType(sql, Integer.class);
	}

	public void initAccount(Account account) throws Exception {
		Integer speciesId = reincarnation(account.getSoul());
		account.setSpeciesId(speciesId);
		account.setLevel(1);
		account.setExp(0);
		Date bornDate = new Date();
		account.setReincarnateTime(bornDate);
		account.setVigor(100);
		account.setSatiety(100);
		account.setHp(SpeiceService.speciesInfo.get(speciesId).getBaseHp());
	}

	public Long getRemainTime(Account account, Species species) {
		Long passTime = new Date().getTime()
				- account.getReincarnateTime().getTime();
		Long totalTime = species.getLifetime() * 24 * 60 * 60 * 1000l;

		return Math.max(0, totalTime - passTime);
	}

	public Integer calSoulGet(Account account, Species species) {

		Integer specieSoul = species.getSoul();
		Integer specisLevel = account.getLevel();

		return specieSoul * specisLevel;
	}

	public String reincarnate(Account account) throws Exception {

		Species species = speiceService.getSpeices(account);
		if (this.getRemainTime(account, species) > 0) {
			return "stillremaintime";
		}
		Integer soul = this.calSoulGet(account, species);
		Integer sumSoul = account.getSoul() + soul;
		account.setSoul(sumSoul);
		initAccount(account);
		Species newSpecies = this.get(Species.class, account.getSpeciesId());
		account.setSoul(sumSoul - newSpecies.getSoul());
		assetConvert(account);
		this.merge(account);
		
		return "";
	}

	public String changeProp(Account account, Account prop) throws Exception {

		Species species = speiceService.getSpeices(account);

		if (this.getRemainTime(account, species) < 24 * 60 * 60 * 1000) {
			return "cannotchangefromtime";
		}

		Integer addPow = prop.getAddPow();
		Integer addDef = prop.getAddDef();
		Integer addDex = prop.getAddDex();
		Integer addInt = prop.getAddInt();
		Integer addHp = prop.getAddHp();

		if (addPow + addDef + addDex + addInt + addHp > account.getSoul()) {
			return "notenoughsoul";
		}

		if (addPow < (0 - account.getAddPow())
				|| addDef < (0 - account.getAddDef())
				|| addDex < (0 - account.getAddDex())
				|| addInt < (0 - account.getAddInt())
				|| addHp < (0 - account.getAddHp())) {
			return "propdataerror";
		}

		account.setAddPow(account.getAddPow() + addPow);
		account.setAddDef(account.getAddDef() + addDef);
		account.setAddDex(account.getAddDex() + addDex);
		account.setAddInt(account.getAddInt() + addInt);
		account.setAddHp(account.getAddHp() + addHp);
		account.setSoul(account.getSoul()
				- (addPow + addDef + addDex + addInt + addHp));

		account.setHp(Math.min(account.getHp(),
				account.getAddHp() + species.getBaseHp()));

		this.merge(account);

		return "";
	}

	public void assetConvert(Account account) {

	}

	@Resource
	SpeiceService speiceService;
}
