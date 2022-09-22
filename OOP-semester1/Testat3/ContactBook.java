import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ContactBook {
	private final Map<String, Contact> contactBook = new HashMap<>();

	public void load() throws ContactBookException {
		try (var stream = new ObjectInputStream(new FileInputStream("contactBook.bin"))) {
			while (true) {
				Contact contact = (Contact) stream.readObject();
				contactBook.put(contact.getName(), contact);
			}
		} catch (EOFException e) {
			// End of stream, this is fine and expected
		} catch (FileNotFoundException e) {
			throw new ContactBookException("File \"contactBook.bin\" not found!");
		} catch (IOException e) {
			throw new ContactBookException("Error creating inputstream!");
		} catch (ClassNotFoundException e) {
			throw new ContactBookException("Error finding class from File. Are those objects from class Contact?");
		}
	}

	public void save() throws ContactBookException {
		try (var stream = new ObjectOutputStream(new FileOutputStream("contactBook.bin"))) {
			contactBook.forEach((k, v) -> {
				try {
					stream.writeObject(v);
				} catch (IOException e) {
					System.out.println(("Error writing Contact " + v.getName() + ". " + e.getMessage()));
				}
			});
		} catch (IOException e) {
			throw new ContactBookException("Error creating outputstream! " + e.getMessage());
		}
	}

	public void addContact(String name, String address) {
		if (contactBook.containsKey(name)) {
			throw new IllegalArgumentException("Name already exists");
		}
		contactBook.put(name, new Contact(name, address));
	}

	public void addNumber(String name, String number, String description) {
		if (!contactBook.containsKey(name)) {
			throw new IllegalArgumentException("Name does not exist");
		}
		Contact contact = contactBook.get(name);
		contact.addPhoneEntry(new PhoneEntry(number, description));
	}

	public Contact findContact(String name) {
		return contactBook.get(name);
	}
}