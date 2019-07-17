package cp10.message_notification;

import cp10.message_notification.basic.Event;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    //该类生成事件
    //这些任务将通过SubmissionPublisher对象发送给消费者
    private SubmissionPublisher<Event> publisher;
    private String name;
    public Producer(SubmissionPublisher<Event> publisher, String name) {
        this.publisher = publisher;
        this.name = name;
    }

    @Override
    public void run() {
        //生成10个事件
        //在一个事件和下一个事件之间随即等待几秒
        Random random=new Random();
        for(int i=0;i<10;i++){
            var event=new Event();
            event.setMsg("Event number "+i);
            event.setSource(this.name);
            event.setDate(new Date());
            publisher.submit(event);
            int number=random.nextInt(10);
            try{
                TimeUnit.SECONDS.sleep(number);
            }catch (InterruptedException ignore){}
        }
    }
}
