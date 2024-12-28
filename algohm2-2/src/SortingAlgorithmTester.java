// Title: SortingAlgorithmTester
// Author:UMUT UYGUR
// ID:13474078970
// Section: 1
// Assignment: 2

import java.util.Random;

// This class checks the algorithms to obtain the value which method belongs to which sorting algoritm
public class SortingAlgorithmTester {
    public static void main(String[] args) {
        //------------------------------------------------------:
        // In summary, the program's main method serves as its entry point.
        // It evaluates five distinct sorting methods on each array type and creates arrays in various orders (random, descending, and ascending).
        //  Precondition: The arrays' sizes are predetermined in this procedure.
        //  Postcondition: Outputs the time it takes for each sorting algorithm to run for each kind of array.
        //----------------------------------------------------------------

        int[] ascendingArray = generateAscendingArray(50000); // generates an ascending array
        int[] descendingArray = generateDescendingArray(10000); // generates a descending array
        int[] randomArray = generateRandomArray(50000); // generates a random array

        // Test all sorting algorithms on all generated arrays
        // Note: Using `.clone()` ensures the original arrays are not altered during sorting.
        // These applies all the algorithms to all the arrays created before
        testSortingAlgorithm("Sort1", ascendingArray.clone());
        testSortingAlgorithm("Sort1", descendingArray.clone());
        testSortingAlgorithm("Sort1", randomArray.clone());
        System.out.println(); // to make it look neat

        testSortingAlgorithm("Sort2", ascendingArray.clone());
        testSortingAlgorithm("Sort2", descendingArray.clone());
        testSortingAlgorithm("Sort2", randomArray.clone());
        System.out.println();// Print a blank line to separate results for readability

        testSortingAlgorithm("Sort3", ascendingArray.clone());
        testSortingAlgorithm("Sort3", descendingArray.clone());
        testSortingAlgorithm("Sort3", randomArray.clone());
        System.out.println();

        testSortingAlgorithm("Sort4", ascendingArray.clone());
        testSortingAlgorithm("Sort4", descendingArray.clone());
        testSortingAlgorithm("Sort4", randomArray.clone());
        System.out.println();

        testSortingAlgorithm("Sort5", ascendingArray.clone());
        testSortingAlgorithm("Sort5", descendingArray.clone());
        testSortingAlgorithm("Sort5", randomArray.clone());
        System.out.println();
    }

    //--------------------------------------------------------
    // Method: generateAscendingArray
    // Summary: Generates an array in ascending order.
    // Precondition: The input parameter `size` specifies the size of the array
    // Postcondition: Returns an array of integers starting from 0 to `size-1`.
    //--------------------------------------------------------

    private static int[] generateAscendingArray(int size) {
        int[] array = new int[size];// Initialize the array
        for (int i = 0; i < size; i++) {
            array[i] = i;// Assign values in ascending order
        }
        return array; // Return array
    }

    //--------------------------------------------------------
    // Method: generateDescendingArray
    // Summary: Generates an array in descending order.
    // Precondition: The input parameter `size` specifies the size of the array
    // Postcondition: Returns an array of integers starting from `size` down to 1.
    //--------------------------------------------------------
    private static int[] generateDescendingArray(int size) {
        int[] array = new int[size];// Initialize the array
        for (int i = 0; i < size; i++) {
            array[i] = size - i;// Assign values in descending order
        }
        return array;
    }

    //--------------------------------------------------------
    // Method: generateRandomArray
    // Summary: Generates an array of random integers.
    // Precondition: The input parameter `size` specifies the size of the array
    // Postcondition: Returns an array of random integers.
    //--------------------------------------------------------
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];// Initialize the array
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();// Assign random integers to the array
        }
        return array;
    }

    //------------------------------------------------------------------
    // Method:TestSortingAlgorithm
    // Summary: Measures the execution time of a given sorting algorithm to test it.
    // Precondition: The input array is a legitimate array of integers, the sorting algorithm is recognized by its name (`algorithmName`),
    // and the input array is a valid array of integers.
    // Postcondition: Outputs the sorting algorithm's execution time in milliseconds.
    //---------------------------------------------------------...


    private static void testSortingAlgorithm(String algorithmName, int[] array) {
        long startTime = System.currentTimeMillis();

        // to decide which method to call
        switch (algorithmName) {
            case "Sort1":
                SortingAlgorithms.sort1(array, 1347407897L);
                break;
            case "Sort2":
                SortingAlgorithms.sort2(array, 1347407897L);
                break;
            case "Sort3":
                SortingAlgorithms.sort3(array, 1347407897L);
                break;
            case "Sort4":
                SortingAlgorithms.sort4(array, 1347407897L);
                break;
            case "Sort5":
                SortingAlgorithms.sort5(array, 1347407897L);
                break;
            default:
                System.out.println("Invalid sorting algorithm name: " + algorithmName);
                return;
        }
        // to measure the time the algorithm took
        long endTime = System.currentTimeMillis();// Record the end time
        long executionTime = endTime - startTime;// Calculate execution time
        // Print the execution time for the current algorithm and array
        System.out.println(algorithmName + ": Execution time = " + executionTime + " milliseconds");
    }
}