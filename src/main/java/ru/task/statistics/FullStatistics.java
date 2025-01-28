package ru.task.statistics;

import ru.task.type.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

/**
 * Класс, необходимый для ведения полной статистики
 */
public class FullStatistics implements Statistics {

    private final Map<DataType, Integer> dataTypeStatisticsMap;

    private BigInteger minInt;
    private BigInteger maxInt;
    private BigInteger sumInt = BigInteger.ZERO;

    private BigDecimal minDec;
    private BigDecimal maxDec;
    private BigDecimal sumDec = BigDecimal.ZERO;

    private Long minStringLength;
    private Long maxStringLength;

    public FullStatistics() {
        dataTypeStatisticsMap = new EnumMap<>(DataType.class);
        dataTypeStatisticsMap.put(DataType.INTEGER, 0);
        dataTypeStatisticsMap.put(DataType.DECIMAL, 0);
        dataTypeStatisticsMap.put(DataType.STRING, 0);
    }

    @Override
    public void addData(String data, DataType dataType) {
        switch (dataType) {
            case INTEGER -> updateIntegerStats(data);
            case DECIMAL -> updateDecimalStats(data);
            case STRING -> updateStringStats(data);
        }
    }

    /**
     * Вывод полной статистики в консоль
     */
    @Override
    public void printStatistics() {
        System.out.println("\nПолная статистика:");
        if (dataTypeStatisticsMap.get(DataType.INTEGER) > 0) {
            String integerStatisticsString = String.format("""
                    
                        Статистика для целых чисел:
                            Количество: %d
                            Минимум: %s
                            Максимум: %s
                            Сумма: %s
                            Среднее значение: %s
                    """, dataTypeStatisticsMap.get(DataType.INTEGER), minInt, maxInt, sumInt, calculateAverageInteger(dataTypeStatisticsMap.get(DataType.INTEGER)));
            System.out.println(integerStatisticsString);
        }

        if (dataTypeStatisticsMap.get(DataType.DECIMAL) > 0) {
            String decimalStatisticsString = String.format("""
                        Статистика для вещественных чисел:
                            Количество: %d
                            Минимум: %s
                            Максимум: %s
                            Сумма: %s
                            Среднее значение: %s
                    """, dataTypeStatisticsMap.get(DataType.DECIMAL), minDec, maxDec, sumDec, calculateAverageDecimal(dataTypeStatisticsMap.get(DataType.DECIMAL)));
            System.out.println(decimalStatisticsString);
        }

        if (dataTypeStatisticsMap.get(DataType.STRING) > 0) {
            String stringStatisticsString = String.format("""
                        Статистика для строк:
                            Количество: %d
                            Длина наименьшей строки: %d
                            Длина наибольшей строки: %d
                    """, dataTypeStatisticsMap.get(DataType.STRING), minStringLength, maxStringLength);
            System.out.println(stringStatisticsString);
        }
    }

    private void updateIntegerStats(String data) {
        dataTypeStatisticsMap.put(DataType.INTEGER, dataTypeStatisticsMap.get(DataType.INTEGER) + 1);

        BigInteger currentInteger = new BigInteger(data);

        if (dataTypeStatisticsMap.get(DataType.INTEGER) == 1) {
            maxInt = currentInteger;
            minInt = currentInteger;
        }

        sumInt = sumInt.add(currentInteger);
        if (currentInteger.compareTo(minInt) < 0) minInt = currentInteger;
        if (currentInteger.compareTo(maxInt) > 0) maxInt = currentInteger;
    }

    private void updateDecimalStats(String data) {

        dataTypeStatisticsMap.put(DataType.DECIMAL, dataTypeStatisticsMap.get(DataType.DECIMAL) + 1);

        BigDecimal currentDecimal = new BigDecimal(data);

        if (dataTypeStatisticsMap.get(DataType.DECIMAL) == 1) {
            maxDec = currentDecimal;
            minDec = currentDecimal;
        }

        sumDec = sumDec.add(currentDecimal);
        if (currentDecimal.compareTo(minDec) < 0) minDec = currentDecimal;
        if (currentDecimal.compareTo(maxDec) > 0) maxDec = currentDecimal;
    }

    private void updateStringStats(String data) {
        dataTypeStatisticsMap.put(DataType.STRING, dataTypeStatisticsMap.get(DataType.STRING) + 1);

        long length = data.length();

        if (dataTypeStatisticsMap.get(DataType.STRING) == 1) {
            minStringLength = length;
            maxStringLength = length;
        }

        if (length < minStringLength) minStringLength = length;
        if (length > maxStringLength) maxStringLength = length;
    }

    private BigDecimal calculateAverageDecimal(int count) {
        return count > 0 ? sumDec.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    private BigDecimal calculateAverageInteger(int count) {
        return count > 0 ? new BigDecimal(sumInt).divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }
}
