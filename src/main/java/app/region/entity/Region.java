package app.region.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pid;
    private String name;
    private String type;

    @Transient
    private List<Region> subRegions;
    @Transient
    private Integer deep;
    @Transient
    private NodeState state;
    @Transient
    private String color;
    @Transient
    private Long currentRegion;

    public Region() {
        subRegions = new ArrayList<Region>();
        id = 0l;
        pid = 0l;
        name = "";
        deep = 0;
        state = new NodeState();
        color = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public Long getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Long currentRegion) {
        this.currentRegion = currentRegion;
    }
}
