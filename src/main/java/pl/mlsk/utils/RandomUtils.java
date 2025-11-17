package pl.mlsk.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtils {

    private static final ThreadLocal<Random> RANDOM = ThreadLocal.withInitial(() -> new Random(42));

    private static Random getRandom() {
        return RANDOM.get();
    }

    public static int nextInt(int max) {
        return getRandom().nextInt(max);
    }

    public static boolean nextBoolean() {
        return getRandom().nextBoolean();
    }
}
