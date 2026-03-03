package com.dianping.cat;

import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CatTest {
    @Before
    public void initCat() {
        Cat.initializeByDomainForce("cat");
    }

    @Test
    public void transaction() {
        Transaction transaction = Cat.newTransaction("test", "test");
        transaction.setStatus(Message.SUCCESS);
        transaction.complete();
    }

    @Test
    public void event() {
        Cat.logEvent("test", "test");
    }

    @Test
    public void metric() {
        Cat.logMetricForCount("myKey", 1);
    }

    @Test
    public void callRunnable() throws Exception {
        Cat.call("test", "callRunnable", new Cat.CatRunnable() {
            @Override
            public void run() {
                Cat.logEvent("test", "insideCallRunnable");
            }
        });
    }

    @Test
    public void callCallable() throws Exception {
        int value = Cat.call("test", "callCallable", new Cat.CatCallable<Integer>() {
            @Override
            public Integer call() {
                return 42;
            }
        });

        assertEquals(42, value);
    }

    @After
    public void waitForSend() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
    }

}
