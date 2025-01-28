package ru.task.statistics;

import lombok.experimental.UtilityClass;
import ru.task.type.StatisticsType;

@UtilityClass
public class StatisticsFactory {

    public static Statistics createStatistics(StatisticsType statisticsType) {
        switch (statisticsType) {
            case FULL -> {
                return new FullStatistics();
            }

            case SHORT -> {
                return new ShortStatistics();
            }

            default -> {
                return new ShortStatistics();
            }
        }
    }
}
