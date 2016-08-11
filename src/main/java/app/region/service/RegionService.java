package app.region.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import app.account.entity.Account;
import app.base.DatabaseService;
import app.region.entity.Region;
import app.region.entity.RegionInfo;
import app.region.entity.RegionTree;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;

@Service
public class RegionService extends DatabaseService{
	
	public static Integer MOVECOSTMULTI_VIGOR = 8;
	public static Integer MOVECOSTMULTI_SATITEY = 4;
	
	public Map<String, String> speciesRegion = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("fish", "river,");
			put("bird", "city,forest");
		}
	};
	
	public RegionService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}
	
	public RegionTree getRegionTree(Species species, Account account,
			RequestContext context) {

		String sql = "select * from region";
		List<Region> rList = this.gets(Region.class, sql, null);

		RegionTree rTree = new RegionTree(context);
		for (Region r : rList) {
			if (species != null) {
				setAbleBySpecies(r, species);
			}
			if (r.getId().intValue() == account.getRegion().intValue()) {
				r.setColor("#2c3e50");
			}
			rTree.addRegion(r);
		}
		rTree.setDeep(rTree.getRoot(), 1);

		return rTree;
	}
	
	public void setAbleBySpecies(Region r, Species s) {
		if (r.getType() != null) {
			if (speciesRegion.get(s.getName()).contains(r.getType())) {
				r.getState().setDisabled(false);
			} else {
				r.getState().setDisabled(true);
			}
		}
	}
	
	public RegionInfo getRegionInfo(Account account, Integer regionId,
			RequestContext context) {

		RegionInfo ri = new RegionInfo();
		RegionTree rTree = this.getRegionTree(null, account, null);
		Integer moveDistance = rTree.getDistance(account.getRegion(), regionId);

		if (moveDistance < 0) {
			return null;
		} else {
			List<Account> accountList = this.gets(Account.class,
					"select * from account where region=?",
					new Integer[] { regionId });

			if (accountList != null) {
				for (Account a : accountList) {
					String key = context.getMessage(SpeiceService.speciesInfo.get(
							a.getSpeciesId()).getName());
					Integer num = 1;
					if (ri.getMemberIn().get(key) != null) {
						num = ri.getMemberIn().get(key) + 1;
					}
					ri.getMemberIn().put(key, num);
				}
			}
		}

		ri.setSatietyCost(moveDistance * MOVECOSTMULTI_SATITEY);
		ri.setVigorCost(moveDistance * MOVECOSTMULTI_VIGOR);

		return ri;
	}
	
	public String regionMove(Account account, Integer regionId)
			throws Exception {

		Species species = speiceService.getSpeices(account);
		RegionTree rTree = this.getRegionTree(species, account, null);
		Region region = rTree.getRegionById(regionId);

		if (regionId.intValue() == account.getRegion().intValue()
				|| region == null || region.getType() == null) {
			return "regioninfoerror";
		}
		if (region.getState().isDisabled()) {
			return "regionforbid";
		}

		Integer moveDistance = rTree.getDistance(account.getRegion(), regionId);

		Integer satietyCost = moveDistance * MOVECOSTMULTI_SATITEY;
		Integer vigorCost = moveDistance * MOVECOSTMULTI_VIGOR;

		if (satietyCost > account.getSatiety()) {
			return "notenoughsatiety";
		}
		if (vigorCost > account.getVigor()) {
			return "notenoughvigor";
		}

		account.setSatiety(account.getSatiety() - satietyCost);
		account.setVigor(account.getVigor() - vigorCost);
		account.setRegion(regionId);

		this.merge(account);

		return "";
	}
	
	@Resource
	SpeiceService speiceService;
}
