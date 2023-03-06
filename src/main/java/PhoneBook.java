import java.util.HashMap;
import java.util.Map;

public class PhoneBook {

    private Map<String, String> contacts = new HashMap<>();
    private static PhoneBook instance = null;

    private PhoneBook() {}

    public static PhoneBook get() {
        if(instance ==null) {
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
}
