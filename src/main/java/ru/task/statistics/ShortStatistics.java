package ru.task.statistics;

import ru.task.type.DataType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Класс, необходимый для ведения краткой статистики
 */
public class ShortStatistics implements Statistics {

    private final Map<DataType, Integer> dataTypeStatisticsMap;

    public ShortStatistics() {
        dataTypeStatisticsMap = new EnumMap<>(DataType.class);
        dataTypeStatisticsMap.put(DataType.INTEGER, 0);
        dataTypeStatisticsMap.put(DataType.DECIMAL, 0);
        dataTypeStatisticsMap.put(DataType.STRING, 0);
    }

    @Override
    public void addData(String data, DataType dataType) {
        dataTypeStatisticsMap.put(dataType, dataTypeStatisticsMap.get(dataType) + 1);
    }

    /**
     * Вывод краткой статистики в консоль
     */
    @Override
    public void printStatistics() {
        StringBuilder statisticsString = new StringBuilder();

        statisticsString.append("Короткая статистика: \n");
        for (Map.Entry<DataType, Integer> entry : dataTypeStatisticsMap.entrySet()) {
            statisticsString.append("Тип данных: ")
                    .append(entry.getKey())
                    .append(", Количество: ")
                    .append(entry.getValue())
                    .append("\n");
        }

        System.out.println(statisticsString);
    }
}
