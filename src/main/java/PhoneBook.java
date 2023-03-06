import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class PhoneBook {

    private Map<String, String> contacts = new HashMap<>();
    private static PhoneBook instance = null;

    private PhoneBook() {
    }

    public static PhoneBook get() {
        if (instance == null) {
            instance = new PhoneBook();
        }
        return instance;
    }

    public int add(String name, String number) {
        if (!contacts.containsKey(name)) {
            contacts.put(name, number);
        }
        return contacts.size();
    }

    public String findByNumber(String number) {
        Optional<String> result = contacts.entrySet()
                .stream()
                .filter(entry -> number.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        return result.orElse(null);
    }

    public String findByName(String name) {
        return null;
    }
}
