package cp10.news_notification;

public class PublisherTask implements Runnable {
    //向消费者发送条目
    //使用变量保存消费者、相关数据、发布者间的订阅关系
    private ConsumerData consumerData;
    private News news;
    public PublisherTask(ConsumerData consumerData, News news) {
        this.consumerData = consumerData;
        this.news = news;
    }

    @Override
    public void run() {
        //检查以下三点
        //订阅有无取消
        //订阅者是否请求了更多的条目
        //News对象的类别存在于该订阅者关联的别集中
        //如果通过了这三个条件，使用onNext方法将其发送给订阅者
        var subscription=consumerData.getSubscription();
        if(!(subscription.isCancelled())&&(subscription.getRequested()>0)&&(subscription.hasCategory(news.getCategory()))){
            consumerData.getConsumer().onNext(news);
            subscription.decreaseRequested();
        }
    }
}
