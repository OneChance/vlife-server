package app.role.service;

import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;

import app.account.service.AccountRepository;
import app.base.NetMessage;
import org.springframework.stereotype.Service;
import app.account.entity.Account;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;
import sun.nio.ch.Net;


@Service
public class RoleService {

    /**
     * Description:转生成为的新物种
     * @param soul:魂值
     * @return
     */
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
        Long totalTime = species.getLifeTime() * 24 * 60 * 60 * 1000l;

        return Math.max(0, totalTime - passTime);
    }

    public Integer calSoulGet(Account account) {

        Integer specieSoul = account.getSpecies().getSoul();
        Integer specisLevel = account.getLevel();

        return specieSoul * specisLevel;
    }

    public NetMessage reincarnate(Account account) throws Exception {

        NetMessage netMessage = new NetMessage();

        Species species = speiceService.getSpeices(account);
        if (this.getRemainTime(account, species) > 0) {
            netMessage.setType(NetMessage.DANGER);
            netMessage.setContent("still_remain_time");
            return netMessage;
        }
        Integer soul = this.calSoulGet(account);
        Integer sumSoul = account.getSoul() + soul;
        account.setSoul(sumSoul);
        initAccount(account);
        Species newSpecies = speiceService.getById(account.getSpeciesId());
        account.setSoul(sumSoul - newSpecies.getSoul());
        assetConvert(account);
        accountRepository.save(account);
        netMessage.setType(NetMessage.SUCCESS);
        netMessage.setContent("reincarnate_completed");
        return netMessage;
    }

    public NetMessage changeProp(Account loginAccount, Account propAccount) throws Exception {

        NetMessage netMessage = new NetMessage();

        if (loginAccount.getId() == null) {
            return loginAccount;
        }

        if (loginAccount.getRemainTime() < 24 * 60 * 60 * 1000) {
            netMessage.setMessage(NetMessage.DANGER, "exceeded_deadline");
            return netMessage;
        }

        if (propAccount.getAddPow() < 0 || propAccount.getAddDef() < 0 ||
                propAccount.getAddDef() < 0 || propAccount.getAddDef() < 0 || propAccount.getAddDef() < 0) {
            netMessage.setMessage(NetMessage.DANGER, "data_error");
            return netMessage;
        }

        Integer addPow = propAccount.getAddPow() - loginAccount.getAddPow();
        Integer addDef = propAccount.getAddDef() - loginAccount.getAddDef();
        Integer addDex = propAccount.getAddDex() - loginAccount.getAddDex();
        Integer addInt = propAccount.getAddInt() - loginAccount.getAddInt();
        Integer addHp = propAccount.getAddHp() - loginAccount.getAddHp();

        if (addPow + addDef + addDex + addInt + addHp > loginAccount.getSoul()) {
            netMessage.setMessage(NetMessage.DANGER, "data_error");
            return netMessage;
        }

        loginAccount.setAddPow(propAccount.getAddPow());
        loginAccount.setAddDef(propAccount.getAddDef());
        loginAccount.setAddDex(propAccount.getAddDex());
        loginAccount.setAddInt(propAccount.getAddInt());
        loginAccount.setAddHp(propAccount.getAddHp());
        loginAccount.setSoul(loginAccount.getSoul()
                - (addPow + addDef + addDex + addInt + addHp));

        loginAccount.setHp(Math.min(loginAccount.getHp(), loginAccount.getAddHp() + loginAccount.getSpecies().getBaseHp()));

        accountRepository.save(loginAccount);

        netMessage.setMessage(NetMessage.SUCCESS, "change_success");

        return netMessage;
    }

    public void assetConvert(Account account) {

    }

    @Resource
    SpeiceService speiceService;
    @Resource
    AccountRepository accountRepository;
}
