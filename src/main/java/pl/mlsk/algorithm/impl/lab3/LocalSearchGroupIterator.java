package pl.mlsk.algorithm.impl.lab3;

import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.InterRouteIterator;
import pl.mlsk.common.Solution;
import pl.mlsk.utils.RandomUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public abstract class LocalSearchGroupIterator implements Iterator<Solution> {

    private final InterRouteIterator interRouteIterator;
    private final LocalSearchIterator basicIterator;

    @Override
    public boolean hasNext() {
        return basicIterator.hasNext() || interRouteIterator.hasNext();
    }

    @Override
    public Solution next() {
        if (!hasNext()) throw new NoSuchElementException();

        if (!basicIterator.hasNext()) return interRouteIterator.next();
        if (!interRouteIterator.hasNext()) return basicIterator.next();

        return RandomUtils.nextBoolean() ? interRouteIterator.next() : basicIterator.next();
    }
}
