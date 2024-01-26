package Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helpers {
    public Float getFloatFromString(String s) {
        return Float.parseFloat(s.replaceAll("[^0-9/.]", ""));
    }
    public List<Integer> selectRandom(Integer size, Integer count) {
        List<Integer> idx = new ArrayList<>(size);
        for(int i = 0; i< size;i++) {
            idx.add(i,i);
        }
        Collections.shuffle(idx);
        return idx.subList(0,count);
    }
}
