package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.util.Collections.reverse;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    // Трудоёмкость - O(n * m)
    // Ресурсоёмкость - O(n * m)
    public static String longestCommonSubSequence(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        int[][] matrix = new int[firstLength + 1][secondLength + 1];
        for (int i = 1; i <= firstLength; i++) {
            for (int j = 1; j <= secondLength; j++) {
                if (first.charAt(i - 1) != second.charAt(j - 1)) {
                    matrix[i][j] = max(matrix[i - 1][j], matrix[i][j - 1]);
                } else {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        while (0 < firstLength && 0 < secondLength) {
            if (first.charAt(firstLength - 1) == second.charAt(secondLength - 1)) {
                builder.append(first.charAt(firstLength - 1));
                firstLength--;
                secondLength--;
            } else {
                if (matrix[firstLength - 1][secondLength] > matrix[firstLength][secondLength - 1]) {
                    firstLength--;
                } else {
                    secondLength--;
                }
            }
        }
        return builder.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоёмкость - O(n^2)
    // Ресурсоёмкость - O(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() <= 1) return list;
        List<Integer> result = new ArrayList<>();
        int[] a = new int[list.size()];
        int[] b = new int[list.size()];
        a[0] = 1;
        b[0] = -1;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) < list.get(i)) {
                    if (a[j] + 1 > a[i]) {
                        a[i] = a[j] + 1;
                        b[i] = j;
                    }
                }
            }
        }
        int index = 0;
        int length = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > length) {
                index = i;
                length = a[i];
            }
        }
        while (index != -1) {
            result.add(list.get(index));
            index = b[index];
        }
        reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
