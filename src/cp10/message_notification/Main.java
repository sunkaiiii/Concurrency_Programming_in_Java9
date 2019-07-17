package cp10.message_notification;

import cp10.message_notification.basic.Event;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        startConsumerTest();
    }

    private static void startConsumerTest() {
        //一个SubmissionPublisher对象
        //五个Consumer对象，他们将接受发布者创建的所有事件，使用subscribe方法订阅消费者
        //两个producer对象，他们生成事件，使用publisher对象发送给消费者
        //由Java框架提供的生产者和消费者模式
        SubmissionPublisher<Event> publisher = new SubmissionPublisher<>();
        for (int i = 0; i < 5; i++) {
            var consumer = new Consumer("Consumer " + i);
            publisher.subscribe(consumer);
        }
        var system1 = new Producer(publisher, "System 1");
        var system2 = new Producer(publisher, "System 2");
        ForkJoinTask<?> task1 = ForkJoinPool.commonPool().submit(system1);
        ForkJoinTask<?> task2 = ForkJoinPool.commonPool().submit(system2); //通过forkJoinPool来生成任务
        do {
            System.out.println("Main: Task 1: " + task1.isDone());
            System.out.println("Main: Task 2: " + task2.isDone());
            System.out.println("Publisher: MaximunLag:" +
                    publisher.estimateMaximumLag());
            System.out.println("Publisher: Max Buffer Capacity: " +
                    publisher.getMaxBufferCapacity());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!task1.isDone()) || (!task2.isDone()) ||
                (publisher.estimateMaximumLag() > 0));
        publisher.close();
    }
}
