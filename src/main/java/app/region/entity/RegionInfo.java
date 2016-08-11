package app.region.entity;

import java.util.HashMap;
import java.util.Map;

public class RegionInfo {
	private Integer vigorCost;
	private Integer satietyCost;
	private Map<String, Integer> memberIn;

	public RegionInfo() {
		vigorCost = 0;
		satietyCost = 0;
		memberIn = new HashMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
		};
	}

	public Integer getVigorCost() {
		return vigorCost;
	}

	public void setVigorCost(Integer vigorCost) {
		this.vigorCost = vigorCost;
	}

	public Integer getSatietyCost() {
		return satietyCost;
	}

	public void setSatietyCost(Integer satietyCost) {
		this.satietyCost = satietyCost;
	}

	public Map<String, Integer> getMemberIn() {
		return memberIn;
	}

	public void setMemberIn(Map<String, Integer> memberIn) {
		this.memberIn = memberIn;
	}

}
