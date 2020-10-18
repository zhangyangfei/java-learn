package com.zyf.javabase.thread.producer;

public class PcTest {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        ProducerConsumer.Producer pp = producerConsumer.new Producer();
        pp.producer();
        ProducerConsumer.Consumer pc = producerConsumer.new Consumer();
        pc.consumer();
    }
}
