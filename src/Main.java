import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static class Employee {
        private final String name;
        private final int age;
        private final Posts post;

        public Employee(String namt, int age, Posts post) {
            this.name = namt;
            this.age = age;
            this.post = post;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Posts getPost() {
            return post;
        }
    }

    public static void main(String[] args) {
        //-------------------------------------------------------
        // Удаление из List-а всех дубликатов
        System.out.println("-------------------------------------------------------");
        System.out.println("Удаление из List-а всех дубликатов");
        // 1. Удаление из List-а всех дубликатов с помощью Stream-ового distinct-а:
        List<String> persons = Arrays.asList("Вася", "Саня", "Коля", "Миша", "Вася", "Миша", "Коля", "Коля", "Саня");
        System.out.println(persons);

        // Используем Stream для фильтрации уникальных элементов
        List<String> distinctPersons = persons.stream()
                .distinct() // Удаляем дубликаты
                .collect(Collectors.toList()); // Собираем результат в список

        System.out.println("Список без дубликатов с помощью distinct-а: " + distinctPersons);

        // 2. Удаление из List-а всех дубликатов с помощью HashSet:
        // Удаляем дубликаты с помощью HashSet
        HashSet<String> uniquePersonsHash = new HashSet<>(persons);

        // Преобразуем HashSet обратно в список
        List<String> uniquePerson = new ArrayList<>(uniquePersonsHash);

        System.out.println("Список без дубликатов с помощью HashSet-а: " + uniquePerson);

        //-------------------------------------------------------
        // Найти в списке целых чисел 3-е наибольшее число :
        System.out.println("-------------------------------------------------------");
        System.out.println("Найти в списке целых чисел 3-е наибольшее число");
        List<Integer> numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        System.out.println(numbers);
        Integer thirdLargest = numbers.stream()
                .sorted(Comparator.reverseOrder())
                //.skip(2)
                .findFirst()
                .orElse(null);

        System.out.println("Третье максимальное число: " + thirdLargest);


        //-------------------------------------------------------
        // Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста :
        System.out.println("-------------------------------------------------------");
        System.out.println("Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста :");

        List<Employee> employeers = Arrays.asList( new Employee("Михаил Иванович 1", 35, Posts.MANAGER),
                                                   new Employee("Степан Петрович 3", 44, Posts.ENGINEER),
                                                   new Employee("Михаил Иванович 2", 25, Posts.ENGINEER),
                                                   new Employee("Степан Петрович 1", 44, Posts.ENGINEER),
                                                   new Employee("Степан Петрович 2", 50, Posts.MANAGER),
                                                   new Employee("Полиграф Полиграфович 1", 27, Posts.ENGINEER),
                                                   new Employee("Преображенский Филипп Филипович", 65, Posts.DIRECTOR) );

        System.out.println("Вариант с List<String> -----------------");
        List<String> employeerNames = employeers.stream()
                .filter(employeer -> employeer.getPost() == Posts.ENGINEER)
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .map(Employee::getName)
                .limit(3)
                .collect(Collectors.toList());
        employeerNames.forEach(System.out::println);

        System.out.println("Вариант с Map<String, Integer> -----------------");
        Map<String, Integer> employeersMap = employeers.stream()
                .filter(employeer -> employeer.getPost() == Posts.ENGINEER)
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(3)
                .collect(Collectors.toMap(Employee::getName, Employee::getAge));

        employeersMap.forEach((name, age) -> System.out.println(name + ": " + age));

        //-------------------------------------------------------
        // Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер» :
        System.out.println("-------------------------------------------------------");
        System.out.println("Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер» :");

        double averageAge = employeers.stream()
                .filter(employeer -> employeer.getPost() == Posts.ENGINEER)
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);

        System.out.println("Средний возраст инженеров : " + averageAge);

        System.out.println("Вариант с группировкой по должностям -----------------");

        Map<Posts, Double> averageAgeByPosition = employeers.stream()
                .collect(Collectors.groupingBy(Employee::getPost,
                        Collectors.averagingInt(Employee::getAge)));

        averageAgeByPosition.forEach((position, avgAge) ->
            System.out.println("Должность: " + position + ", средний возраст: " + avgAge));

        //-------------------------------------------------------
        // Найти в списке целых чисел 3-е наибольшее "уникальное" число :
        System.out.println("-------------------------------------------------------");
        System.out.println("Найти в списке целых чисел 3-е наибольшее \"уникальное\" число");
        System.out.println(numbers);
        Integer thirdLargestUnic = numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);

        System.out.println("Третье максимальное уникальное число: " + thirdLargestUnic);

        //-------------------------------------------------------
        // Найдите в списке слов самое длинное
        System.out.println("-------------------------------------------------------");
        System.out.println("Найдите в списке слов самое длинное");
        List<String> words = Arrays.asList("Перфекционизм", "Эвфемизм", "Коллаборация", "Экзистенциальный", "Трансценденция", "Шикарно");
        System.out.println(words);
        String longestWord = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Самое длинное слово: " + longestWord);


        //-------------------------------------------------------
        // Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        // Постройте HashMap в котором будут храниться пары: слово - сколько раз оно встречается во входной строке.
        System.out.println("-------------------------------------------------------");
        System.out.println("Постройте HashMap в котором будут храниться пары: слово - сколько раз оно встречается во входной строке.");
        String input = "да тут нельзя слово сказать тут нельзя слово сказать нельзя слово сказать слово сказать сказать";
        System.out.println("да тут нельзя слово сказать тут нельзя слово сказать нельзя слово сказать слово сказать сказать");

        Map<String, Long> wordCount = Stream.of(input.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        wordCount.forEach((word, count) -> System.out.println(word + ": " + count));

        //-------------------------------------------------------
        // Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        // Отпечатать в консоль строки в порядке увеличения длины слова, если слова имеют одинаковую длину, то должен быть сохранен алфавитный порядок.
        System.out.println("-------------------------------------------------------");
        System.out.println("Отпечатать в консоль строки в порядке увеличения длины слова, если слова имеют одинаковую длину, то должен быть сохранен алфавитный порядок.");
        List<String> sortWords = Arrays.asList("аааа", "бббб", "вввв", "гггг", "ггггггг", "аааа", "дддд", "ееее", "ееееееееееее", "ёёёё");
        System.out.println(sortWords);

        sortWords.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);

        //-------------------------------------------------------
        // Имеется массив строк, в каждом из которых лежит набор из 5 строк, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них.
        System.out.println("-------------------------------------------------------");
        System.out.println("Имеется массив строк, в каждом из которых лежит набор из 5 строк, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них.");

        String[] strings = {
                "Общество отрезвилось. Это зрелище поголовного",
                "освобождения от лишних мыслей, лишних",
                "чувств и лишней совести до",
                "такой степени умилительно, что даже",
                "клеветники и человеконенавистники на время"
        };
        System.out.println(strings);

        String longestFlatWord = Arrays.stream(strings)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .reduce("", (a, b) -> a.length() >= b.length() ? a : b);

        System.out.println("Самое длинное слово: " + longestFlatWord);
    }
}