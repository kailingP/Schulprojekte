import java.util.HashSet;
import java.util.Set;

public class Module {
	private final String name;
	private Set<String> prerequisiteSet;

	Module(String name) {
		this.name = name;
		prerequisiteSet = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public Set<String> getPrerequisiteSet() {
		return prerequisiteSet;
	}

	public void removePrerequisite(String moduleName) {
		prerequisiteSet.remove(moduleName);
	}

	public void addPrerequisite(String moduleName) {
		prerequisiteSet.add(moduleName);
	}

	// prerequisites don't matter for equals/hash
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Module other = (Module) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
