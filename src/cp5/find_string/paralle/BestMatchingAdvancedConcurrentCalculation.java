package cp5.find_string.paralle;

import cp5.find_string.basic.BestMatchingData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BestMatchingAdvancedConcurrentCalculation {
    //第二种并发调用。与第一种的区别就在于创建任务和对结果的处理上
    public static BestMatchingData getBestMatchingWords(String
                                                                word, List<String> dictionary) throws InterruptedException,
            ExecutionException {
        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor)
                Executors.newFixedThreadPool(numCores);
        int size = dictionary.size();
        int step = size / numCores;
        int startIndex, endIndex;
        List<BestMatchingBasicTask> tasks = new ArrayList<>();
        List<Future<BestMatchingData>> results = new ArrayList<>();
        for (int i = 0; i < numCores; i++) {
            startIndex = i * step;
            if (i == numCores - 1) {
                endIndex = dictionary.size();
            } else {
                endIndex = (i + 1) * step;
            }
            BestMatchingBasicTask task = new BestMatchingBasicTask(startIndex,
                    endIndex, dictionary, word);
            tasks.add(task);
        }
        //使用invokeAll方法
        //他会返回一个结果的列表
        results=executor.invokeAll(tasks);
        executor.shutdown();
        List<String> words = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        for (Future<BestMatchingData> future : results) {
            BestMatchingData data = future.get();
            if (data.getDistance() < minDistance) {
                words.clear();
                minDistance = data.getDistance();
                words.addAll(data.getWords());
            } else if (data.getDistance()== minDistance) {
                words.addAll(data.getWords());
            }
        }
        BestMatchingData result = new BestMatchingData();
        result.setDistance(minDistance);
        result.setWords(words);
        return result;
    }
}