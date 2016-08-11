package app.region.entity;

import java.util.ArrayList;
import java.util.List;

public class Region {
	private Integer id;
	private Integer pid;
	private String name;
	private String type;
	private List<Region> subRegions;
	private Integer deep;
	private NodeState state;
	private String color;

	public Region() {
		subRegions = new ArrayList<Region>();
		id = 0;
		pid = 0;
		name = "";
		deep = 0;
		state = new NodeState();
		color = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getPid() {
		return pid;
	}

	public List<Region> getSubRegions() {
		return subRegions;
	}

	public void setSubRegions(List<Region> subRegions) {
		this.subRegions = subRegions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
