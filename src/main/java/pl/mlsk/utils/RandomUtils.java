package pl.mlsk.utils;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    private static Random getRandom() {
        return context.getBean(Random.class);
    }

    public static int nextInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    public static int nextInt(int max) {
        return getRandom().nextInt(max);
    }

    @Override
    public synchronized void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
