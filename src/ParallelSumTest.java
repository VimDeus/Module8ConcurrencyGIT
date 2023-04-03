import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;



public class ParallelSumTest {

    @Test
    public void testGenerateRandomArray() {
        int[] array = ParallelSum.generateRandomArray(10);
       
        // Test array length
        assertEquals(10, array.length);
      
        // Test correct array integer values
        Set<Integer> values = new HashSet<>();
        
        for (int i = 0; i < array.length; i++) {
            assertTrue(array[i] >= 1 && array[i] <= 10);
            values.add(array[i]);
        }
       
        // Test correct array integer amount values
        assertTrue(values.size() >= 2);
    }

    @Test
    public void testComputeParallelSum() {
       
    	int[] array = new int[] { 1, 2, 3, 4, 5 };
        int expectedSum = 15;
        int actualSum = ParallelSum.computeParallelSum(array, 2);
        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void testComputeSingleThreadSum() {
      
    	int[] array = new int[] { 1, 2, 3, 4, 5 };
        int expectedSum = 15;
        int actualSum = ParallelSum.computeSingleThreadSum(array);
        assertEquals(expectedSum, actualSum);
    }

}
