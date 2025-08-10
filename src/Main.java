import StreamModel.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("adb");
        words.add("Level");
        words.add("aba");
        words.add("baa");

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(4);
        nums.add(3);
        nums.add(3);

        List<Integer> nums2 = new ArrayList<>();
        nums2.add(6);
        nums2.add(7);
        nums2.add(4);
        nums2.add(8);

        List<Person> people = new ArrayList<>();
        people.add(new Person("name2", "email2", 12));
        people.add(new Person("name3", "email3", 22));
        people.add(new Person("name4", "email4", 50));
        people.add(new Person("name6", "email5", 42));
        people.add(new Person("name5", "email26", 38));

        List<TransactionN> tr = new ArrayList<>();
        tr.add(new TransactionN("food", 10));
        tr.add(new TransactionN("tech", 5));
        tr.add(new TransactionN("food", 7));

        List<Student> students = new ArrayList<>();
        students.add(new Student("Ann", 80));
        students.add(new Student("Bob", 40));
        students.add(new Student("Cat", 55));

        List<Employee> employees = List.of(
                new Employee("John", "IT", 2000.0),
                new Employee("Anna", "HR", 1500.0),
                new Employee("Mike", "IT", 2500.0),
                new Employee("Sara", "Finance", 3000.0)
        );

        List<List<Integer>> list = List.of(
                List.of(1, 2),
                List.of(3, 4),
                List.of(5)
        );

        List<Product> products = List.of(
                new Product("Phone", "Electronics", 500),
                new Product("Laptop", "Electronics", 1000),
                new Product("Chair", "Furniture", 200),
                new Product("Table", "Furniture", 400)
        );

        List<Order> orders = List.of(
                new Order("Alice", 120.50),
                new Order("Bob", 200.00),
                new Order("Alice", 99.99),
                new Order("Bob", 50.25),
                new Order("Charlie", 75.00)
        );

        List<Employee2> employees2 = List.of(
                new Employee2("John", List.of("Java", "Spring", "SQL")),
                new Employee2("Anna", List.of("Python", "Django", "SQL")),
                new Employee2("Mike", List.of("Java", "Kotlin", "Spring"))
        );

        List<Author> authors = List.of(
                new Author("Tolkien", List.of("Hobbit", "Lord of the Rings")),
                new Author("Rowling", List.of("Harry Potter 1", "Harry Potter 2")),
                new Author("Martin", List.of("Game of Thrones", "Clash of Kings"))
        );

        List<User> users = List.of(
                new User("Alice", List.of(new Transaction(1, 120.5), new Transaction(2, 75.0))),
                new User("Bob", List.of(new Transaction(3, 200.0))),
                new User("Charlie", List.of(new Transaction(4, 50.0), new Transaction(5, 300.0)))
        );

        System.out.println(allTransactionAllUsers(users));
    }

    //Все транзакции всех пользователей
    public static List<Double> allTransactionAllUsers(List<User> users){
        return users.stream()
                .flatMap(user -> user.transactions().stream())
                .map(Transaction::amount)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    //Уникальные слова в списке предложений, разделить массив
    public static List<String> distinctWords(List<String> words){
        return words.stream()
                .flatMap(s -> Arrays.stream(s.split("\\W+")))
                .map(String::toLowerCase)
                .filter(w -> !w.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    //Все книги всех авторов
    public static List<String> allBooksAllAuthors(List<Author> authors){
        return authors.stream()
                .flatMap(author -> author.books().stream())
                .sorted()
                .toList();
    }

    // Все уникальные навыки сотрудников вложенный массив
    public static List<String> distinctSkills(List<Employee2> employee2s){
        return employee2s.stream()
                .flatMap(e -> e.skills().stream())
                .map(String::toLowerCase)
                .distinct()
                .toList();
    }

    //Сумма зарплат по департаментам
    public static Map<String, Double> sumSalaryByDepartment(List<Employee> employees){
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.summingDouble(Employee::salary)
                ));
    }

    //Сумма всех заказов клиента
    public static Map<String, Double> averageOrderByClient(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::customer,
                        Collectors.summingDouble(Order::amount)
                ));
    }

    //Самый дешевый товар по категории
    public static Map<String, Product> cheapProductByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.collectingAndThen(
                                Collectors.minBy(
                                        Comparator.comparingDouble(Product::price)),
                                Optional::get
                        )
                ));
    }

    //Самый дешевый товар по категории 2
    public static Map<String, Product> cheapProductByCategory2(List<Product> products) {
        return products.stream()
                .collect(toMap(Product::category,
                        Function.identity(),
                        BinaryOperator.minBy(
                                Comparator.comparingDouble(Product::price))));
    }


    //Средний бал между студентов
    public static Map<String, Double> averagePriceByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        averagingDouble(Product::price)
                ));
    }

    //FlatMap
    public static List<Integer> flatMap(List<List<Integer>> numbers) {
        return numbers.stream()
                .flatMap(List::stream)
                .toList();
    }

    //Объединение строк
    public static String joinLine(List<String> words) {
        return String.join(", ", words);
    }

    //Группировка сотрудников
    public static Map<String, Long> groupEmployees(List<Employee> empl) {
        return empl.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));
    }

    //Сортировка по длине и алфавиту
    public static List<String> sortByLength(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(String::compareToIgnoreCase))
                .toList();

    }

    //Partitioning
    public static Map<Boolean, List<String>> partitionByPalindrome2(List<String> words) {
        return words.stream()
                .collect(Collectors.partitioningBy(
                        w -> w.equalsIgnoreCase(new StringBuilder(w)
                                .reverse()
                                .toString())
                ));
    }

    //Top N элементов
    public static List<Integer> topThreeElements(List<Integer> nums) {
        return nums.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
    }

    //Первый уникальный символ
    public static Optional<Character> firstDistinctLetter(String word) {
        return word.chars()
                .map(Character::toLowerCase)
                .mapToObj(w -> (char) w)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )).entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    //Частота слов
    public static Map<String, Long> mapWords(String words) {
        return Arrays.stream(
                        words.split("\\W+"))
                .map(String::toLowerCase)
                .filter(s -> !s.isBlank())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    //Уникальные элементы
    public static List<Integer> distinctNumber(List<Integer> nums) {
        return nums.stream()
                .distinct()
                .sorted()
                .toList();
    }

    //Сумма цен по категориям
    public static Map<String, Double> sumPriceByTransactionN(List<TransactionN> transactionNS) {
        return transactionNS.stream()
                .collect(Collectors.groupingBy(
                        TransactionN::category,
                        Collectors.summingDouble(TransactionN::amount)
                ));
    }

    //Максимальное число
    public static Optional<Integer> maxNumber(List<Integer> nums) {
        return nums.stream().max(Integer::compareTo);
    }

    //Максимальное число 2
    public static Optional<Integer> maxNumber2(List<Integer> nums) {
        return nums.stream()
                .sorted(Comparator.reverseOrder())
                .findFirst();
    }

    //Группировка по первой букве
    public static Map<Character, List<String>> groupFirstLetter(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(
                        w -> w.charAt(0)
                ));
    }

    //Фильтрация и сортировка
    public static List<String> filterAndSort(List<String> words) {
        return words.stream()
                .filter(w -> w.startsWith("a"))
                .sorted()
                .toList();
    }

    //Map слово → длина
    public static Map<String, Long> mapLengthWord(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    //Первые N чётных чисел
    public static List<Integer> evenNumbers(int number) {
        return IntStream.range(0, number)
                .map(i -> i * 2)
                .boxed()
                .toList();
    }

    //Подсчет строк длиннее 5 символов
    public static long sizeLineMoreFive(List<String> words) {
        return words.stream()
                .filter(w -> w.length() > 5)
                .count();
    }

    //Сумма всех чисел
    public static int sumNumber(List<Integer> nums) {
        return nums.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    //Список строк в верхнем регистре
    public static List<String> toLowerCaseWords(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .toList();
    }

    //Подсчёт количества слов в каждой группе по длине
    public static Map<Integer, Long> countWordsByLength(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.counting()
                ));
    }

    //Группировка людей по возрастной категории
    public static Map<String, List<Person>> groupByAgeCategory(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(p -> {
                    int age = p.age();
                    if (age < 18) return "child";
                    else if (age < 30) return "young";
                    else return "adult";
                }));
    }

    //Группировка слов по первой букве (lowerCase)
    public static Map<Character, List<String>> groupByFirstLetter2(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(
                        w -> w.charAt(0))
                );
    }

    //Сортировка по убыванию суммы покупок
    public static List<Student> sortByTotalSpentDesc(List<Student> customers) {
        return customers.stream()
                .sorted(Comparator.comparingDouble(Student::score))
                .toList().reversed();
    }

    //Сортировка по длине строки, при равенстве — по алфавиту
    public static List<String> sortByLengthThenAlpha(List<String> words) {
        return words.stream()
                .sorted(
                        Comparator.comparingInt(String::length)
                                .thenComparing(String::compareToIgnoreCase))
                .toList();
    }

    // Отсортировать людей по возрасту, затем имени
    public static List<Student> sortByAgeThenName(List<Student> people) {
        return people.stream()
                .sorted(Comparator.comparing(Student::score)
                        .thenComparing(Student::name))
                .toList();
    }

    // Разделить слова на палиндромы и не палиндромы
    public static Map<Boolean, List<String>> partitionByPalindrome(List<String> words) {
        return words.stream()
                .collect(Collectors.partitioningBy(
                        w -> w.equalsIgnoreCase(new StringBuilder(w)
                                .reverse()
                                .toString())
                ));
    }

    //Разделить числа на простые и составные
    public static Map<Boolean, List<Integer>> partitionByPrime(List<Integer> nums) {
        return nums.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }

    //Первые N чётных чисел, начиная с 0
    public static List<Integer> firstNEvens(int n) {
        return IntStream.range(0, n).map(i -> i * 2).boxed().toList();
    }

    //Разделить студентов на сдавших/не сдавших экзамен
    public static Map<Boolean, List<Student>> partitionByPassing(List<Student> students) {
        return students.stream()
                .collect(Collectors.partitioningBy(s -> s.score() > 50));
    }

    //Map слово→длина
    public static Map<String, Integer> wordLengthMap(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        String::length,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    //Уникальные числа из двух списков (объединение)
    public static List<Integer> unionDistinctSorted(List<Integer> a, List<Integer> b) {
        return Stream.concat(a.stream(), b.stream())
                .distinct().toList();
    }

    // Самая длинная строка
    public static Optional<String> longest(List<String> words) {
        return words.stream().max(Comparator.comparing(String::length));
    }

    //Сумма amount по категории
    public static Map<String, Integer> sumByCategory(List<TransactionN> tx) {
        return tx.stream()
                .collect(Collectors.groupingBy(TransactionN::category,
                        Collectors.summingInt(TransactionN::amount)));
    }

    // Уникальные символы (регистр игнорировать)
    public static List<Character> uniqueCharsSorted(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                .distinct()
                .sorted()
                .toList();
    }

    //Конкатенация строк через “, ”
    public static String joinWithCommaSpace(List<String> parts) {
        return parts.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }

    // Второй по величине элемент
    public static Optional<Integer> secondMax(List<Integer> nums) {
        return nums.stream()
                .sorted(Collections.reverseOrder())
                .skip(1)
                .findFirst();
    }

    // Средняя длина слова
    public static double averageWordLength(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .mapToInt(String::length)
                .average()
                .orElse(0.0);

    }

    // Первый неповторяющийся символ
    public static Optional<Character> firstUniqueChar(String s) {
        if (s == null || s.isEmpty()) return Optional.empty();

        return s.chars()
                .map(Character::toLowerCase)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new, Collectors.counting()
                )).entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    // Разделить список чисел на четные и нечетные
    public static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> nums) {
        if (nums == null) return Map.of();
        return nums.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0
                ));
    }

    //Сумма квадратов чётных чисел
    public static int sumSquaresOfEven(List<Integer> nums) {
        if (nums == null) return 0;
        return nums.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
    }

    // Группировка слов по первой букве
    public static Map<Character, List<String>> groupByFirstLetter(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .filter(w -> !w.isBlank())
                .map(String::toLowerCase)
                .collect(
                        Collectors.groupingBy(word -> word.charAt(0))
                );
    }

    // Сортировка StreamModel.Person по
    public static List<Person2> sortPeople(List<Person2> people) {
        if (people == null) return List.of();
        return people.stream()
                .sorted(
                        Comparator.comparing(Person2::lastName,
                                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                                .thenComparing(Person2::firstName,
                                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                                .thenComparingInt(Person2::age))
                .collect(toList());
    }

    // Топ-3 самых частых слов (самые частые слова)
    public static List<String> top3Words(List<String> words) {
        if (words == null) return List.of();
        return words.stream()
                .filter(Objects::nonNull)
                .filter(w -> !w.isBlank())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    // сумма букв в словах которые начинаются на а
    public static int sumLenStartsWithA(List<String> words) {
        if (words == null) return 0;
        return words.stream()
                .filter(Objects::nonNull)
                .filter(w -> !w.isBlank())
                .filter(w -> Character.toLowerCase(w.charAt(0)) == 'a')
                .mapToInt(String::length)
                .sum();
    }

    // Уникальные по email в model
    public static List<Person> uniqueByEmail(List<Person> people) {
        if (people == null) return List.of();
        return people.stream()
                .filter(p -> p.email() != null && !p.email().isBlank())
                .collect(Collectors.toMap(
                        p -> p.email().toLowerCase(Locale.ROOT),
                        Function.identity(),
                        (first, dup) -> first,
                        LinkedHashMap::new
                ))
                .values().stream().toList();
    }

    // частота букв в предложении
    public static Map<Character, Long> charFreq(String s) {
        return s.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()));
    }

    // частота слов (символов) в спике
    public static void frequencies(List<String> words) {
        Map<String, Long> count = words.stream()
                .collect(Collectors.groupingBy(
                        w -> w,
                        Collectors.counting()));
        System.out.println(count);
    }

    // посчитать кол-во слов в строке
    public static void fifthTask(String st) {
        String[] words = st.split(" ");
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (map.containsKey(words[i])) {
                int counter = map.get(words[i]);
                map.put(words[i], ++counter);
            } else {
                map.put(words[i], 1);
            }
        }
        System.out.println(map);
    }

    // Палиндром на список String
    public static List<String> palindromes(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .filter(w -> !w.isBlank())
                .filter(w -> w.equalsIgnoreCase(new StringBuilder(w).reverse().toString()))
                .toList();
    }

    // найти палиндром циклом while
    public static void palindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        boolean answer = true;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                answer = false;
            }
            left++;
            right--;
        }
        System.out.println(answer);
    }

    //поиск палиндрома stream
    public static boolean isPalindrome(String text) {
        return text.replaceAll("//W", "")
                .equalsIgnoreCase(new StringBuilder(text.replaceAll("//W", ""))
                        .reverse().toString());
    }

    // числа фибоначи
    public static void fibonacci(int number) {
        int x = 0, y = 0, z = 1;
        for (int i = 0; i < number; i++) {
            x = y;
            y = z;
            z = x + y;
            System.out.println(x + " ");
        }
    }

    // Вывести повторяющиеся символы
    public static void repeatChar(String s) {
        int count = 0;
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (c[i] == c[j]) {
                    System.out.println(c[i]);
                    count++;
                    break;
                }
            }
        }
    }


}