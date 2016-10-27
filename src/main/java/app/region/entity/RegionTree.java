package app.region.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.RequestContext;

public class RegionTree {

	private Region root;
	private List<Region> regionList;
	private RequestContext context;

	public RegionTree(RequestContext context) {
		root = new Region();
		root.setId(-1l);
		regionList = new ArrayList<Region>();
		loadRegion(root, regionList);
		this.context = context;
		if (context != null) {
			root.setName(context.getMessage("world"));
		}
	}

	public void addRegion(Region r) {

		for (int i = 0; i < root.getSubRegions().size(); i++) {
			if (root.getSubRegions().get(i).getPid().intValue() == r.getId()
					.intValue()) {
				r.getSubRegions().add(root.getSubRegions().get(i));
				root.getSubRegions().remove(i);
				i--;
			}
		}

		Region parentR = this.getRegionById(r.getPid());

		if (parentR == null) {
			root.getSubRegions().add(r);
		} else {
			parentR.getSubRegions().add(r);
		}

		if (context != null) {
			r.setName(context.getMessage(r.getName()));
		}

		regionList.add(r);
	}

	public Region getRegionById(Long id) {

		for (Region r : regionList) {
			if (r.getId().longValue() == id.longValue()) {
				return r;
			}
		}
		return null;
	}

	public void loadRegion(Region r, List<Region> rList) {

		rList.add(r);

		if (r.getSubRegions().size() > 0) {
			for (Region rs : r.getSubRegions()) {
				loadRegion(rs, rList);
			}
		}
	}

	public void printTree(Region r, String head) {

		System.out.println(head + r.getName());

		if (r.getSubRegions().size() > 0) {
			for (Region rs : r.getSubRegions()) {
				printTree(rs, head + "--");
			}
		}
	}

	public void setDeep(Region r, Integer deep) {
		if (r.getSubRegions().size() == 0) {
			r.setDeep(deep);
		} else {

			r.setDeep(deep);

			for (Region rs : r.getSubRegions()) {
				setDeep(rs, deep + 1);
			}
		}
	}

	public String getPath(Long rid) {

		String path = "";

		Region r = this.getRegionById(rid);

		if (r != null) {
			while (r.getId() != -1) {
				path = path + r.getId() + ",";
				r = this.getRegionById(r.getPid());
			}
		}

		path = path + "-1";

		return path;
	}

	public Integer getDistance(Long fromRid, Long toRid) {

		if (fromRid == 0) {
			return 0;
		}

		String orPath = this.getPath(fromRid);
		String nrPath = this.getPath(toRid);

		String[] orPathNodes = orPath.split(",");
		String[] nrPathNodes = nrPath.split(",");

		for (int i = 0; i < orPathNodes.length; i++) {
			String on = orPathNodes[i];
			for (int j = 0; j < nrPathNodes.length; j++) {
				String nn = nrPathNodes[j];
				if (on.equals(nn)) {
					return Math.max(0, i + j - 1);
				}
			}
		}

		return -1;
	}

	public Region getRoot() {
		return root;
	}

	public void setRoot(Region root) {
		this.root = root;
	}

}
