package ru.task.file;

import lombok.RequiredArgsConstructor;
import ru.task.service.DataTypeIdentifier;
import ru.task.statistics.Statistics;
import ru.task.type.DataType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class FileHandler {

    private final FileWriterManager fileWriterManager;
    private final DataTypeIdentifier dataTypeIdentifier;
    private final Statistics statistics;

    public void readFiles(List<String> paths) {
        paths.forEach(p -> precessFile(Path.of(p)));

        fileWriterManager.closeWriters(fileWriterManager.getWriterMap());
    }

    private void precessFile(Path filePath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            bufferedReader.lines()
                    .forEach(this::processLine);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл " + filePath);
        }
    }

    private void processLine(String line) {
        if (!line.isEmpty()) {
            DataType currentDataType = dataTypeIdentifier.identifyDataType(line);
            statistics.addData(line, currentDataType);
            writeLine(line, currentDataType);
        }
    }

    /**
     * Запись строки в файл
     *
     * @param line            Текущая строка
     * @param currentDataType Тип данных, которому принадлежит текущая строка
     */
    private void writeLine(String line, DataType currentDataType) {
        try {
            BufferedWriter writer = fileWriterManager.getWriter(currentDataType);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка записи строки: " + line);
        }
    }
}
