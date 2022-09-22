import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author Solution by Linus Flury
 *
 */
public class Test {
	public static void main(String[] args) {
		Set<Module> moduleSet = new HashSet<>();

		try (var reader = new CatalogueReader("StudyCatalogue.txt")) {
			String[] names;
			while ((names = reader.readNextLine()) != null) {
				System.out.print("Module: " + names[0] + " Prerequisite: ");
				Module currentModule = new Module(names[0]);
				for (int i = 1; i < names.length; i++) {
					System.out.print(names[i] + " ");
					currentModule.addPrerequisite(names[i]);
				}
				moduleSet.add(currentModule);
			}
			System.out.println();
		}
		catch(FileNotFoundException e1) {
			System.out.print("File 'StudyCatalogue.txt not found! Will exit now! Message:\n"+e1.getMessage());
			System.exit(1);
		}
		catch(IOException e2) {
			System.out.println("IO Exception occured while parsing StudyCatalogue.txt! Message:\n"+ e2.getMessage());
			System.exit(1);
		}

		String output = "";
		for (int i = 1; !moduleSet.isEmpty(); i++) {
			Set<String> moduleCurrentNames = new HashSet<>();
			output += "\nSemester " + i;
			for (Module module : moduleSet) {
				if (module.getPrerequisiteSet().isEmpty()) {
					moduleCurrentNames.add(module.getName());
					output += " " + module.getName();
				}
			}
			if (moduleCurrentNames.isEmpty()) {
				System.out.println("Input contains cycle in Semester " + i + "!" + output + "\nRemaining Modules:");
				for (Module module : moduleSet) {
					System.out.println("Module: " + module.getName() + "; Remaining prerequisites: "
							+ module.getPrerequisiteSet());
				}
				break;
			}
			for (String moduleName : moduleCurrentNames) {
				moduleSet.removeIf(m -> m.getName().equals(moduleName));
				for (Module module : moduleSet) {
					module.removePrerequisite(moduleName);
				}
			}
		}
		if (moduleSet.isEmpty()) {
			System.out.println(output);
		}
	}
}
