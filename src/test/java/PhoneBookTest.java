import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class PhoneBookTest {

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("All PhoneBookTests started");
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("All PhoneBookTests completed");
    }

//    сброс синглтона, для независимости тестов друг от друга
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = PhoneBook.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @ParameterizedTest
    @MethodSource("addParametersDefinition")
    public void addTest(String name, String number, int count) throws NoSuchFieldException, IllegalAccessException {
//    arrange
        PhoneBook book = PhoneBook.get();
//    act
        int result = book.add(name, number);
//    assert
        Assertions.assertEquals(count, result);
    }

    private static Stream<Arguments> addParametersDefinition() {
        return Stream.of(
                Arguments.of("Иван", "89527812756", 1),
                Arguments.of("Ирина", "87538956759", 2),
                Arguments.of("Иван", "89529532658", 2),
                Arguments.of("Петр", "89634478956", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("findByNumberParametersDefinition")
    public void findByNumberTest(String name, String number) throws NoSuchFieldException, IllegalAccessException {
//    arrange
        resetSingleton();
        PhoneBook book = PhoneBook.get();
        book.add("Иван", "89527812756");
        book.add("Ирина", "87538956759");
//    act
        String result = book.findByNumber(number);
        resetSingleton();
//    assert
        Assertions.assertEquals(name, result);
    }

    private static Stream<Arguments> findByNumberParametersDefinition() {
        return Stream.of(
                Arguments.of("Иван", "89527812756"),
                Arguments.of("Ирина", "87538956759"),
                Arguments.of(null, "99009090900")
        );
    }

    @ParameterizedTest
    @MethodSource("findByNameParametersDefinition")
    public void findByNameTest(String name, String number) throws NoSuchFieldException, IllegalAccessException {
//    arrange
        resetSingleton();
        PhoneBook book = PhoneBook.get();
        book.add("Иван", "89527812756");
        book.add("Ирина", "87538956759");
//    act
        String result = book.findByName(name);
        resetSingleton();
//    assert
        Assertions.assertEquals(number, result);
    }

    private static Stream<Arguments> findByNameParametersDefinition() {
        return Stream.of(
                Arguments.of("Иван", "89527812756"),
                Arguments.of("Ирина", "87538956759"),
                Arguments.of("Антон", null)
        );
    }

    ByteArrayOutputStream output = new ByteArrayOutputStream();

    @ParameterizedTest
    @ValueSource(strings = "Антон\r\nИван\r\nИрина\r\nТатьяна\r\n")
    public void printAllNamesTest(String names) throws NoSuchFieldException, IllegalAccessException {
//    arrange
        resetSingleton();
        PhoneBook book = PhoneBook.get();
        book.add("Иван", "89527812756");
        book.add("Ирина", "87538956759");
        book.add("Антон", "89009090900");
        book.add("Татьяна", "88008080800");
//    act
        System.setOut(new PrintStream(output));
        book.printAllNames();
        resetSingleton();
//    assert
        Assertions.assertEquals(names, output.toString());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
