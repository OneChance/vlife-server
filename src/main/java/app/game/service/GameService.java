package app.game.service;

import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import app.account.service.AccountRepository;
import app.account.service.AccountService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import app.account.entity.Account;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;


@Service
public class GameService {

	public Long reincarnation(Integer soul) {
		Random random = new Random();
		Integer max = getRandomMax();
		Integer randomValue = Math.min(random.nextInt(max) % (max - 1 + 1) + 1
				+ soul, max);
		return getSpeiceByRandomValue(randomValue);
	}


	public Integer getRandomMax() {
		return speiceService.getRandomMax();
	}

	public Long getSpeiceByRandomValue(Integer randomValue) {
		return speiceService.getSpeiceByRandomValue(randomValue);
	}

	public void initAccount(Account account) throws Exception {
		Long speciesId = reincarnation(account.getSoul());
		account.setSpeciesId(speciesId);
		account.setLevel(1);
		account.setExp(0);
		Date bornDate = new Date();
		account.setReincarnateTime(bornDate);
		account.setVigor(100);
		account.setSatiety(100);
		account.setHp(speiceService.getSpeciesInfo().get(speciesId).getBaseHp());
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
		Species newSpecies = speiceService.getById(account.getSpeciesId());
		account.setSoul(sumSoul - newSpecies.getSoul());
		assetConvert(account);
		accountRepository.save(account);
		
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

		accountRepository.save(account);

		return "";
	}

	public void assetConvert(Account account) {

	}

	@Resource
	SpeiceService speiceService;
	@Resource
	AccountRepository accountRepository;
}
