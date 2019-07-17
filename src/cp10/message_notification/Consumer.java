package cp10.message_notification;

import cp10.message_notification.basic.Event;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

public class Consumer implements Flow.Subscriber<Event> {
    //事件的消费者
    //采用Event类型参数化的Flow.Subscriber接口。
    //将会实现接口提供的方法
    private String name;
    private Flow.Subscription subscription;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void onComplete() {
        this.showMessage("No more events");
    }

    @Override
    public void onError(Throwable throwable) {
        this.showMessage("An error has occurred");
        throwable.printStackTrace();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription=subscription;
        this.subscription.request(1); //请求第一条消息
        this.showMessage("Subscription OK");
    }

    @Override
    public void onNext(Event item) {
        //对于每个事件，调用onNext方法
        //使用request方法请求下一个事件
        this.showMessage("An event has arrived: "+item.getSource()+":"+item.getDate()+": "+item.getMsg());
        this.subscription.request(1);
        processEvent(item);
    }

    private void processEvent(Event item){
        //随机等待0-3秒，模拟处理事件
        var random=new Random();
        int number=random.nextInt();
        try{
            TimeUnit.SECONDS.sleep(number);
        }catch (InterruptedException ignore){}
    }

    private void showMessage(String txt) {
        System.out.println(Thread.currentThread().getName() + ":" + this
                .name + ":" + txt);
    }
}
