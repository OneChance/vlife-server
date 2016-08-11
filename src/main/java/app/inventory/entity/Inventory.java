package app.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long account;
	@Column(name = "typeid")
	private Long typeId;
	private String type;
	private String name;
	private Integer num;
	@Column(name = "hprecover")
	private Integer hpRecover;
	@Column(name = "vigorrecover")
	private Integer vigorRecover;
	@Column(name = "satietyrecover")
	private Integer satietyRecover;

	@Transient
	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
