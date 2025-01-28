package ru.task;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ru.task.args.CmdArgs;
import ru.task.file.FileHandler;
import ru.task.file.FileWriterManager;
import ru.task.service.DataTypeIdentifier;
import ru.task.statistics.Statistics;
import ru.task.statistics.StatisticsFactory;

public class Main {
    public static void main(String[] args) {
        CmdArgs cmdArgs = new CmdArgs();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(cmdArgs)
                .build();
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            throw new ParameterException("Введите файлы для фильтрации");
        }

        FileWriterManager fileWriterManager = new FileWriterManager(cmdArgs.getPath(), cmdArgs.getPrefix(), cmdArgs.isAppendFlag());
        DataTypeIdentifier dataTypeIdentifier = new DataTypeIdentifier();
        Statistics statistics = StatisticsFactory.createStatistics(cmdArgs.getStatisticsType());

        FileHandler fileHandler = new FileHandler(fileWriterManager, dataTypeIdentifier, statistics);

        fileHandler.readFiles(cmdArgs.getInputFiles());
        statistics.printStatistics();
    }
}