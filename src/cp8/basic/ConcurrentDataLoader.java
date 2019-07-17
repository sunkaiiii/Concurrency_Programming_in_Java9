package cp8.basic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ConcurrentDataLoader {

    //读取csv，将其转换为Record对象列表。

    public static List<Record> load(Path path) throws IOException {
        System.out.println("Loading data");

        List<String> lines = Files.readAllLines(path);

        List<Record> records = lines
                .parallelStream()
                .skip(1)
                .map(l -> l.split(";"))
                .map(Record::new)
                .collect(Collectors.toList());

        return records;
    }

}
