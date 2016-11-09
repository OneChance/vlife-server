package app.region.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import app.account.service.AccountService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import app.account.entity.Account;
import app.region.entity.Region;
import app.region.entity.RegionInfo;
import app.region.entity.RegionTree;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;

@Service
public class RegionService {

    public static Integer MOVECOSTMULTI_VIGOR = 8;
    public static Integer MOVECOSTMULTI_SATITEY = 4;

    public Map<String, String> speciesRegion = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("fish", "river,");
            put("bird", "city,forest");
        }
    };

    public RegionTree getRegionTree(Account account) {

        List<Region> rList = regionRepository.findAll();

        RegionTree rTree = new RegionTree();
        for (Region r : rList) {

            setAbleBySpecies(r, account.getSpecies());

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

    public RegionInfo getRegionInfo(Account account, Long region) {

        RegionInfo ri = new RegionInfo();
        RegionTree rTree = this.getRegionTree(account);
        Integer moveDistance = rTree.getDistance(account.getRegion(), region);

        if (moveDistance < 0) {
            return null;
        } else {
            List<Account> accountList = accountService.getByRegion(region);

            if (accountList != null) {
                for (Account a : accountList) {
                    Long key = a.getSpeciesId();
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

    public String regionMove(Account account, Long regionId)
            throws Exception {

        Species species = speiceService.getSpeices(account);
        RegionTree rTree = this.getRegionTree(account);
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

        accountService.save(account);

        return "";
    }

    @Resource
    SpeiceService speiceService;
    @Resource
    AccountService accountService;
    @Resource
    RegionRepository regionRepository;
}
