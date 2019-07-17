package cp3;

import cp3.model.Sample;

public interface Classifier {
    String classify(Sample example) throws Exception;

    default void destroy() {
    }
}
