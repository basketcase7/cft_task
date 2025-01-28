package ru.task.args;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import ru.task.type.StatisticsType;

import java.util.List;

/**
 * Поддерживаемые аргументы командной строки
 */
public class CmdArgs {

    @Getter
    @Parameter(required = true)
    private List<String> inputFiles;

    @Getter
    @Parameter(names = "-o")
    private String path;

    @Getter
    @Parameter(names = "-p")
    private String prefix = "";

    @Getter
    @Parameter(names = "-a")
    private boolean appendFlag;

    @Parameter(names = "-s")
    private boolean shortStatisticsFlag;

    @Parameter(names = "-f")
    private boolean fullStatisticsFlag;

    /**
     * Получение типа статистики, который будет вестись (по умолчанию - краткая)
     *
     * @return Тип статистики
     */
    public StatisticsType getStatisticsType() {
        if (fullStatisticsFlag) {
            return StatisticsType.FULL;
        } else {
            return StatisticsType.SHORT;
        }
    }
}
