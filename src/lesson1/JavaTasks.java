package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Collections.*;

public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // Трудоёмкость - O(n*log2(n))
    // Ресурсоёмкость - O(n)
    static public void sortTimes(String inputName, String outputName) throws IOException {
        FileReader fileReader = new FileReader(new File(inputName));
        BufferedReader reader = new BufferedReader(fileReader);
        String str;
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> listAM = new ArrayList<>();
        List<Integer> listPM = new ArrayList<>();
        while ((str = reader.readLine()) != null) {
            if (!str.matches("(0\\d|1[0-2]):[0-5]\\d:[0-5]\\d\\s(AM|PM)")) {
                throw new IllegalArgumentException();
            }
            String[] strings = str.split(":");
            String[] AMOrPM = strings[2].split(" ");
            int time = Integer.parseInt(strings[0]) * 3600 + Integer.parseInt(strings[1]) * 60 + Integer.parseInt(AMOrPM[0]);
            if (AMOrPM[1].equals("AM")) {
                if (time / 3600 == 12) {
                    list1.add(time);
                } else {
                    listAM.add(time);
                }
            } else {
                if (time / 3600 == 12) {
                    list2.add(time + 100000);
                } else {
                    listPM.add(time + 100000);
                }
            }
        }
        sort(listAM);
        sort(listPM);
        sort(list1);
        sort(list2);
        list1.addAll(listAM);
        list1.addAll(list2);
        list1.addAll(listPM);
        FileWriter writer = new FileWriter(new File(outputName));
        for (Integer element : list1) {
            if (element > 100000) {
                element -= 100000;
                writer.write(String.format("%02d:%02d:%02d", element / 3600, (element % 3600) / 60, element % 60) + " PM" + "\n");
            } else {
                writer.write(String.format("%02d:%02d:%02d", element / 3600, (element % 3600) / 60, element % 60) + " AM" + "\n");
            }
        }
        fileReader.close();
        writer.close();
        reader.close();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        FileReader fileReader = new FileReader(new File(inputName), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        List<String> listNames = new ArrayList<>();
        Map<String, List<String>> map = new TreeMap<>((s1, s2) -> {
            String[] split1 = s1.split(" ");
            String[] split2 = s2.split(" ");
            if (split1[0].equals(split2[0])) {
                int firstNumber = Integer.parseInt(split1[1]);
                int secondNumber = Integer.parseInt(split2[1]);
                return Integer.compare(firstNumber, secondNumber);
            } else {
                return split1[0].compareTo(split2[0]);
            }
        });
        String str;
        while ((str = reader.readLine()) != null) {
            if (!str.matches("[А-ЯЁа-яёPa-]+\\s[А-ЯЁа-яё-]+\\s-\\s[А-ЯЁа-яё-]+\\s\\d+")) {
                throw new IllegalArgumentException();
            }
            list.add(str);
            String[] split = str.split(" - ");
            map.put(split[1], null);
        }
        for (String element : list) {
            String[] nameOrStreet = element.split(" - ");
            if (map.get(nameOrStreet[1]) != null) {
                listNames = map.get(nameOrStreet[1]);
            }
            listNames.add(nameOrStreet[0]);
            sort(listNames);
            map.put(nameOrStreet[1], new ArrayList<>(listNames));
            listNames.clear();
        }
        FileWriter writer = new FileWriter(new File(outputName), StandardCharsets.UTF_8);
        for (Map.Entry<String, List<String>> element : map.entrySet()) {
            String string = "";
            for (String listElement : element.getValue()) {
                string += listElement + ", ";
            }
            writer.write(element.getKey() + " - " + string.substring(0, string.length() - 2) + "\n");
        }
        fileReader.close();
        writer.close();
        reader.close();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    // Трудоёмкость - O(n)
    // Ресурсоёмкость - O(n)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        FileReader fileReader = new FileReader(new File(inputName), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(fileReader);
        ArrayList<Double> list = new ArrayList<>();
        String str;
        while ((str = reader.readLine()) != null) {
            list.add(Double.valueOf(str));
        }
        sort(list);
        FileWriter writer = new FileWriter(new File(outputName), StandardCharsets.UTF_8);
        for (Double element : list) {
            writer.write(element.toString() + "\n");
        }
        fileReader.close();
        writer.close();
        reader.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
