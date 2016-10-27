package app.speice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "species")
public class Species {
	
	@Id
	private Long id;
	private String name;
	private Integer lifetime;
	@Column(name="basepow")
	private Integer basePow;
	@Column(name="basedef")
	private Integer baseDef;
	@Column(name="basedex")
	private Integer baseDex;
	@Column(name="basehp")
	private Integer baseHp;
	@Column(name="baseint")
	private Integer baseInt;
	private Integer soul;
	@Column(name="ratiostart")
	private Integer ratioStart;
	@Column(name="ratioend")
	private Integer ratioEnd;
	private String actions;
	@Column(name="sleeptime")
	private Integer sleepTime;
	@Column(name="foragetime")
	private Integer forageTime;
	private String foods;
	@Column(name="hprecover")
	private Integer hpRecover;
	@Column(name="vigorrecover")
	private Integer vigorRecover;
	@Column(name="satietyrecover")
	private Integer satietyRecover;

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

	public Integer getLifetime() {
		return lifetime;
	}

	public void setLifetime(Integer lifetime) {
		this.lifetime = lifetime;
	}

	public Integer getBasePow() {
		return basePow;
	}

	public void setBasePow(Integer basePow) {
		this.basePow = basePow;
	}

	public Integer getBaseDef() {
		return baseDef;
	}

	public void setBaseDef(Integer baseDef) {
		this.baseDef = baseDef;
	}

	public Integer getBaseDex() {
		return baseDex;
	}

	public void setBaseDex(Integer baseDex) {
		this.baseDex = baseDex;
	}

	public Integer getBaseHp() {
		return baseHp;
	}

	public void setBaseHp(Integer baseHp) {
		this.baseHp = baseHp;
	}

	public Integer getBaseInt() {
		return baseInt;
	}

	public void setBaseInt(Integer baseInt) {
		this.baseInt = baseInt;
	}

	public Integer getSoul() {
		return soul;
	}

	public void setSoul(Integer soul) {
		this.soul = soul;
	}

	public Integer getRatioStart() {
		return ratioStart;
	}

	public void setRatioStart(Integer ratioStart) {
		this.ratioStart = ratioStart;
	}

	public Integer getRatioEnd() {
		return ratioEnd;
	}

	public void setRatioEnd(Integer ratioEnd) {
		this.ratioEnd = ratioEnd;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public Integer getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Integer getForageTime() {
		return forageTime;
	}

	public void setForageTime(Integer forageTime) {
		this.forageTime = forageTime;
	}

	public String getFoods() {
		return foods;
	}

	public void setFoods(String foods) {
		this.foods = foods;
	}

	public Integer getHpRecover() {
		return hpRecover;
	}

	public void setHpRecover(Integer hpRecover) {
		this.hpRecover = hpRecover;
	}

	public Integer getVigorRecover() {
		return vigorRecover;
	}

	public void setVigorRecover(Integer vigorRecover) {
		this.vigorRecover = vigorRecover;
	}

	public Integer getSatietyRecover() {
		return satietyRecover;
	}

	public void setSatietyRecover(Integer satietyRecover) {
		this.satietyRecover = satietyRecover;
	}

}
