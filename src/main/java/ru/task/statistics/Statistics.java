package ru.task.statistics;

import ru.task.type.DataType;

public interface Statistics {
    void addData(String data, DataType dataType);
    void printStatistics();
}
