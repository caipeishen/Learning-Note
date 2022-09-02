package cn.itcast.concurrent.test;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

public class TestDisruptor {
    public static void main(String[] args) throws InterruptedException {
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                () -> new LongEvent(), 16, Executors.newCachedThreadPool());
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            System.out.println(Thread.currentThread().getName() + " " + event);
        },(event, sequence, endOfBatch) -> {
            System.out.println(Thread.currentThread().getName() + " " + event);
        });

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            Thread.sleep(500);
        }

    }
}

class LongEvent
{
    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
