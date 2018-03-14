package util;

import java.util.ArrayList;
import java.util.List;

public class UniqueID {
    private static List<Integer> ids = new ArrayList<Integer>();
    private static int index = 0;
    private static final int RANGE = 10;

    static {
        for (int i = 1; i < RANGE; i++) {
            ids.add(i);
        }
    }

    private UniqueID() {
    }

    public static int getId() {
        if (index > ids.size() - 1) {
            index++;
        }
        return ids.get(index++);
    }
}