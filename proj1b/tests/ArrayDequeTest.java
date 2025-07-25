import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    @DisplayName("ArrayDeque has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void testAddFirstAndToList() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        assertThat(deque.toList()).containsExactly(4, 3, 2, 1).inOrder();
    }


    @Test
    public void testAddLastAndToList() {
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");
        assertThat(deque.toList()).containsExactly("A", "B", "C").inOrder();
    }

    @Test
    public void testIsEmptyAndSize() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);

        deque.addFirst(1);
        deque.addLast(2);

        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    public void testRemoveFirstAndLast() {
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.removeFirst()).isEqualTo("A");  // removes from front
        assertThat(deque.removeLast()).isEqualTo("C");   // removes from back
        assertThat(deque.removeFirst()).isEqualTo("B");
        assertThat(deque.removeFirst()).isNull();        // empty
    }

    @Test
    public void testGetAndGetRecursive() {
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("X");
        deque.addLast("Y");
        deque.addLast("Z");

        assertThat(deque.get(0)).isEqualTo("X");
        assertThat(deque.get(1)).isEqualTo("Y");
        assertThat(deque.get(2)).isEqualTo("Z");
        assertThat(deque.get(3)).isNull();  // out of bounds
    }

    @Test
    public void testMixedOperations() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(2); // [2]
        deque.addLast(3);  // [2, 3]
        deque.addFirst(1); // [1, 2, 3]
        deque.removeLast(); // [1, 2]
        deque.removeFirst(); // [2]

        List<Integer> list = deque.toList();
        assertThat(list).containsExactly(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void resize(){
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        assertThat(deque.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
    }
}
