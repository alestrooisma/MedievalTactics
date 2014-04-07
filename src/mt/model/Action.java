package mt.model;

public abstract class Action {

	private String name;
	private String summary;

	public Action(String name, String summary) {
		this.name = name;
		this.summary = summary;
	}

	public String getName() {
		return name;
	}

	public String getSummary() {
		return summary;
	}

	public Status select(Unit unit) {
		if (isAllowed(unit)) {
			return uponSelection(unit);
		} else {
			return Status.NOT_ALLOWED;
		}
	}

	public Status target(Unit actor, Unit target) {
		if (isAllowed(actor)) {
			return uponTargeting(actor, target);
		} else {
			return Status.NOT_ALLOWED;
		}
	}
	
	public abstract Status uponSelection(Unit actor);
	
	public abstract Status uponTargeting(Unit actor, Unit target);
	
	public boolean isAllowed(Unit actor) {
		return true;
	}
	
	public boolean isAllowed(Unit actor, Unit target) {
		return isAllowed(actor);
	}
	
	public enum Status {
		NOT_ALLOWED, WAITING_FOR_TARGET, DONE;
	}
}
