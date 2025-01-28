package ru.task.file;

import lombok.Getter;
import ru.task.type.DataType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumMap;
import java.util.Map;

/**
 * Класс для управления записью в выходные файлы
 */
public class FileWriterManager {

    @Getter
    private final Map<DataType, BufferedWriter> writerMap;
    private final Path outputDirectory;
    private String prefixName;
    private final boolean appendMode;

    public FileWriterManager(String outputDirectory, String prefixName, boolean appendMode) {
        this.writerMap = new EnumMap<>(DataType.class);
        this.outputDirectory = getSaveDirectory(outputDirectory);
        this.prefixName = prefixName;
        this.appendMode = appendMode;
    }

    public BufferedWriter getWriter(DataType dataType) {
        return writerMap.computeIfAbsent(dataType, this::createWriter);
    }

    /**
     * Создание BufferedWriter для одного из типов данных. Префикс сбрасывается, если с ним возникают какие-либо неполадки
     *
     * @param dataType Текущий тип данных
     * @return BufferedWriter для одного из типов данных
     */
    private BufferedWriter createWriter(DataType dataType) {
        try {
            Path filepath = switch (dataType) {
                case INTEGER -> outputDirectory.resolve(prefixName + "integers.txt");
                case DECIMAL -> outputDirectory.resolve(prefixName + "floats.txt");
                case STRING -> outputDirectory.resolve(prefixName + "strings.txt");
            };

            if (appendMode) {
                return Files.newBufferedWriter(filepath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }

            return Files.newBufferedWriter(filepath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException | InvalidPathException e) {
            if (!prefixName.isEmpty()) {
                System.out.println("Ошибка при создании файла. Сбрасываем префикс имени.");
                prefixName = "";
                return createWriter(dataType);
            } else {
                throw new RuntimeException("Ошибка при создании файла для типа данных: " + dataType);
            }
        }
    }

    /**
     * Закрытие всех активных BufferedWriter
     *
     * @param writerMap Мапа со значениями в виде BufferedWriter
     */
    public void closeWriters(Map<DataType, BufferedWriter> writerMap) {
        try {
            for (BufferedWriter writer : writerMap.values()) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии одного из файлов");
        }

    }

    /**
     * Определение директории, в которую будут сохранены файлы с результатами работы программы
     *
     * @param outputPath Желаемый путь к создаваемым файлам (при пустом пути или ошибке при создании желаемой директории - файлы будут сохранены в корневую директорию)
     * @return Путь к директории
     */
    public Path getSaveDirectory(String outputPath) {
        Path outputDir = Path.of(System.getProperty("user.dir"));
        try {
            if (outputPath != null && !outputPath.isBlank()) {
                outputDir = Path.of(outputPath);
            }
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
        } catch (IOException | InvalidPathException e) {
            System.out.println("Файлы сохранены в место по умолчанию т.к. не удалось создать директорию: " + outputPath);
            outputDir = Path.of(System.getProperty("user.dir"));
        }
        return outputDir;
    }
}
