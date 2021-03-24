import java.util.ArrayList;
import java.util.List;

public class IntegerSet {
    List<Integer> nums = new ArrayList<>();

    // Requires: int
    // Modifies: this
    // Effects: Inserts integer into set unelss it's also there, in which case do nothing.
    public void insert(Integer num) {
        if (!nums.contains(num)) nums.add(num);
    }

    // Effects: Returns the size of the integer set.
    public int size() {
        return nums.size();
    }

    // Requires: int
    // Effects: Returns true if num is in set, otherwise returns false.
    public boolean contains(Integer num) {
        return nums.contains(num);
    }
}
