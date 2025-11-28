package pl.mlsk.algorithm.impl.lab7.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab7.LargeNeighborhoodSearch;

@Service
public class LargeNeighborHoodSearchNoLocal extends LargeNeighborhoodSearch {
    @Override
    protected boolean shouldLocalSearch() {
        return false;
    }
}
