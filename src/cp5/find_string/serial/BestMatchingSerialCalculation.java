package cp5.find_string.serial;

import cp5.find_string.basic.BestMatchingData;
import cp5.find_string.basic.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class BestMatchingSerialCalculation {
    public static BestMatchingData getBestMatchingWords(String word, List<String>dictionary){
        List<String> results=new ArrayList<>();
        int minDistance=Integer.MAX_VALUE;
        int distance;
        //处理字典中的所有单词
        for(String str:dictionary){
            distance= LevenshteinDistance.calculate(word,str);
            if(distance<minDistance){
                results.clear();
                minDistance=distance;
            }else if(distance==minDistance){
                results.add(str);
            }
        }
        var result=new BestMatchingData();
        result.setWords(results);
        result.setDistance(minDistance);
        return result;
    }
}
