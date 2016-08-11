package app.region.entity;

public class NodeState {
	private boolean selected;
	private boolean expanded;
	private boolean disabled;

	public NodeState() {
		selected = false;
		expanded = false;
		disabled = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
