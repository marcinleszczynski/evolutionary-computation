package pl.mlsk.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ListUtils {

    public <T> List<T> listWithElementAtIndex(List<T> origList, T element, int pos) {
        ArrayList<T> result = new ArrayList<>(origList);
        result.addAll(origList);
        result.add(pos, element);
        return result;
    }
}
