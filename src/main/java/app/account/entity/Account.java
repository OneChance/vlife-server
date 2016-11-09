package app.account.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import app.base.NetMessage;
import app.speice.entity.Species;

@Entity
@Table(name = "account")
public class Account extends NetMessage {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "increment")
    private Long id;

    private String account;
    private String name;
    private String password;
    private String salt;
    private String sex;

    @Column(name = "speciesid")
    private Long speciesId;
    private Integer soul;

    @Column(name = "addpow")
    private Integer addPow;
    @Column(name = "adddef")
    private Integer addDef;
    @Column(name = "addhp")
    private Integer addHp;
    @Column(name = "adddex")
    private Integer addDex;
    @Column(name = "addint")
    private Integer addInt;
    @Column(name = "reincarnatetime")
    private Date reincarnateTime;
    private Integer level;
    private Integer exp;
    private Long region;
    private Integer satiety;
    private Integer vigor;
    private Integer hp;
    @Transient
    private Species species;
    @Transient
    private String enterType;
    @Transient
    private Long remainTime;
    @Transient
    private Integer soulget;

    public Account() {
        this.speciesId = 0l;
        this.soul = 0;
        this.addPow = 0;
        this.addDef = 0;
        this.addHp = 0;
        this.addDex = 0;
        this.addInt = 0;
        this.level = 0;
        this.exp = 0;
        this.region = 0l;
        this.satiety = 100;
        this.vigor = 100;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getSoul() {
        return soul;
    }

    public void setSoul(Integer soul) {
        this.soul = soul;
    }

    public Integer getAddPow() {
        return addPow;
    }

    public void setAddPow(Integer addPow) {
        this.addPow = addPow;
    }

    public Integer getAddDef() {
        return addDef;
    }

    public void setAddDef(Integer addDef) {
        this.addDef = addDef;
    }

    public Integer getAddHp() {
        return addHp;
    }

    public void setAddHp(Integer addHp) {
        this.addHp = addHp;
    }

    public Integer getAddDex() {
        return addDex;
    }

    public void setAddDex(Integer addDex) {
        this.addDex = addDex;
    }

    public Integer getAddInt() {
        return addInt;
    }

    public void setAddInt(Integer addInt) {
        this.addInt = addInt;
    }

    public Date getReincarnateTime() {
        return reincarnateTime;
    }

    public void setReincarnateTime(Date reincarnateTime) {
        this.reincarnateTime = reincarnateTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Integer getSatiety() {
        return satiety;
    }

    public void setSatiety(Integer satiety) {
        this.satiety = satiety;
    }

    public Integer getVigor() {
        return vigor;
    }

    public void setVigor(Integer vigor) {
        this.vigor = vigor;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getEnterType() {
        return enterType;
    }

    public void setEnterType(String enterType) {
        this.enterType = enterType;
    }

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Long remainTime) {
        this.remainTime = remainTime;
    }

    public Integer getSoulget() {
        return soulget;
    }

    public void setSoulget(Integer soulget) {
        this.soulget = soulget;
    }
}
