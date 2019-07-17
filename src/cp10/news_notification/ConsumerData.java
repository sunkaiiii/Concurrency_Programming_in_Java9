package cp10.news_notification;

import java.util.concurrent.Flow;

public class ConsumerData {

    private Flow.Subscriber<News> consumer;
    private MySubscription subscription;
    /**
     * @return the consumer
     */
    public Flow.Subscriber<News> getConsumer() {
        return consumer;
    }
    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(Flow.Subscriber<News> consumer) {
        this.consumer = consumer;
    }
    /**
     * @return the subscription
     */
    public MySubscription getSubscription() {
        return subscription;
    }
    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(MySubscription subscription) {
        this.subscription = subscription;
    }



}

