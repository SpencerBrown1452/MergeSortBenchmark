/**
 * File: BenchmarkSorts.java
 * Date: 05 February 2021
 * Author: Spencer Brown
 * Purpose: Benchmarks the efficiency of the chosen sorting algoritm
 */
//Import Statements
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class BenchmarkSorts {
    //JVM warmup for benchmarking
    //Baeldung.com recommends at least 100,000 runs to properly warmup the JVM
    static{
       final int WARMUP = 100000;
       ArrayList<String> list = new ArrayList<>();
       long startTime = System.nanoTime();
       //Performs an append operation to the ArrayList 100000 times
       for(int i = 0; i < WARMUP; i++){
           list.add(Integer.toString(i));
       }
       long endTime = System.nanoTime();
       //Time the warmup took to complete
       System.out.println("\nWarmup time: " + ((endTime - startTime)) + " nanoseconds");
    }//end warmup


    //Variable Declarations
    //How large the sets will be
    private int[] dataSetSizes;
    //How many times the random data will be created and sorted
    private final int numberOfRuns = 50;
    //Stores the count and time data for the recursion/iteration runs
    private long [][][] recursiveData;
    private long[][][] iterativeData;
    //Stores the processed means and coefficient data for each set
    private double[][] recursiveProcessedData;
    private double[][] iterativeProcessedData;
    //Creates an instance of MergeSort, to do the sorting
    private MergeSort mergeSort;
    //File object to store the output of the recursive/iterative data
    private File recursiveFile;
    private File iterativeFile;

    /**
     * Creates a new instance of BenchmarkSorts with the user's specified set sizes
     * @param sizes
     */
    public BenchmarkSorts(int[] sizes){
        this.dataSetSizes = sizes; //Sets the sizes of the datasets
        mergeSort = new MergeSort(); //Initializes the MergeSort object
    }

    /**
     * Performs the iterative and recursive sorts on the randomized n values
     */
    public void performSorts(){
        // Initializes the files to store the input data for the Benchmark report app.
        recursiveFile = new File("RecursiveData.txt");
        iterativeFile = new File("IterativeData.txt");
        //Defines the values to be sorted recursively/iteratively, respectfully
        recursiveData = new long[dataSetSizes.length][2][numberOfRuns];
        iterativeData = new long[dataSetSizes.length][2][numberOfRuns];

        // Checks to see if the files already exist, if they do, deletes them.  Ensures the files aren't just
        // appended after every run of the program
        if (recursiveFile.delete()) {
            System.out.println("Old RecursiveData.txt file deleted and new version created.\n");
        } else {
            System.out.println("RecursiveData.txt file created.\n");
        }
        if (iterativeFile.delete()) {
            System.out.println("Old IterativeData.txt file deleted and new version created.\n");
        } else {
            System.out.println("IterativeData.txt file created.\n");
        }

        // Outer loop for each set's size
        for (int i = 0; i < dataSetSizes.length; i++) {
            // Inner loop to run 50 sorts for each data set.
            for (int j = 0; j < numberOfRuns; j++) {

                //Generates the random values to be sorted for the size of the data set
                int[] dataSet = generateArray(dataSetSizes[i]);
                //Defines the recursive and iterative sets independently
                int[] recursiveSets = new int[dataSet.length];
                int[] iterativeSets = new int[dataSet.length];
                System.arraycopy(dataSet, 0, recursiveSets, 0, dataSet.length);
                System.arraycopy(dataSet, 0, iterativeSets, 0, dataSet.length);

                try {
                    //Sort the values in the recursive array, recursively
                    mergeSort.recursiveSort(recursiveSets);
                    //Get and store the count for each sort
                    recursiveData[i][0][j] = mergeSort.getCount();
                    //Get and store the time for each sort
                    recursiveData[i][1][j] = mergeSort.getTime();

                    //Sort the values in the iterative array, iteratively
                    mergeSort.iterativeSort(iterativeSets);
                    //Get and store the count for each sort
                    iterativeData[i][0][j] = mergeSort.getCount();
                    //Get and store the time for each sort
                    iterativeData[i][1][j] = mergeSort.getTime();
                } catch (UnsortedException e) {
                    //Notify the user the values could not be sorted via the console
                    System.out.println("Array Not Sorted: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Generates the array with random numbers within a set range specified by size parameter.
     * @param size the user input size of the arrays
     * @return randomized values
     */
    private int[] generateArray(int size) {

        int[] dataSet = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            dataSet[i] = rand.nextInt(size);
        }
        return dataSet;
    }

    /**
     * Calculates the data to be output to the recursive/iterative files
     */
    void calculateData() {

        // Stores the sums.
        long[] sums = new long[4];
        // Stores the mean data.
        double[] means = new double[4];
        // Used to calculate and store the standard deviation.
        double[] deviations = new double[4];
        // Used to convert time to milliseconds.
        double[] time = new double[2];
        recursiveProcessedData = new double[dataSetSizes.length][4];
        iterativeProcessedData = new double[dataSetSizes.length][4];

        // Get totals for coefficients of variance
        for (int i = 0; i < dataSetSizes.length; i++) {
            for(int j = 0; j < 4; j++){
                sums[j] = 0;
                deviations[j] = 0;
            }
            for (int j = 0; j < numberOfRuns; j++) {
                sums[0] += recursiveData[i][0][j];
                sums[1] += recursiveData[i][1][j];
                sums[2] += iterativeData[i][0][j];
                sums[3] += iterativeData[i][1][j];
            }
            // Calculate and store the mean for recursive and iterative count and time.
            means[0] = (double) sums[0] / numberOfRuns;
            means[1] = (double) sums[1] / numberOfRuns;
            time[0] = means[1];
            means[2]= (double) sums[2] / numberOfRuns;
            means[3] = (double) sums[3] / numberOfRuns;
            time[1] = means[3];
            recursiveProcessedData[i][0] = means[0];
            recursiveProcessedData[i][2] = time[0];
            iterativeProcessedData[i][0] = means[2];
            iterativeProcessedData[i][2] = time[1];

            // Calculate the stand deviations for both algorithms
            for (int k = 0; k < numberOfRuns; k++) {
                deviations[0] += ((recursiveData[i][0][k] - means[0]) * (recursiveData[i][0][k] - means[0]));
                deviations[1] += ((recursiveData[i][1][k] - means[1]) * (recursiveData[i][1][k] - means[1]));
                deviations[2] += ((iterativeData[i][0][k] - means[2]) * (iterativeData[i][0][k] - means[2]));
                deviations[3] += ((iterativeData[i][1][k] - means[3]) * (iterativeData[i][1][k] - means[3]));
            }

            // Finish the calculation to get the standard deviations for both algorithms
            for(int j = 0; j < 4; j++) deviations[j] = Math.sqrt(deviations[j] / (numberOfRuns - 1));

            // Calculate and store the coefficient of variation for recursive and iterative count and time.
            recursiveProcessedData[i][1] = deviations[0] / means[0];
            recursiveProcessedData[i][3] = deviations[1] / means[1];
            iterativeProcessedData[i][1] = deviations[2] / means[2];
            iterativeProcessedData[i][3] = deviations[3] / means[3];
        }
    }

    /**
     * Creates the iterative and recursive reports to be displayed with the BenchmarkReport app
     */
    void generateReport() {
        try {
            FileWriter rfw = new FileWriter(recursiveFile, true);
            FileWriter ifw = new FileWriter(iterativeFile, true);
            BufferedWriter rbw = new BufferedWriter(rfw);
            BufferedWriter ibw = new BufferedWriter(ifw);

            //Write the data set sizes in the files as the first entry of every line
            for (int i = 0; i < dataSetSizes.length; i++) {
                rbw.write(Integer.toString(dataSetSizes[i]));
                ibw.write(Integer.toString(dataSetSizes[i]));
                for(int j = 0; j <= 3; j++){ //Write the calculated data to the files
                    //Writes in the output order, avg count, coef of count, avg time, coef of time
                    rbw.write(" ");
                    ibw.write(" ");
                    rbw.write(Double.toString(recursiveProcessedData[i][j]));
                    ibw.write(Double.toString(iterativeProcessedData[i][j]));
                }
                rbw.write("\n");
                ibw.write("\n");
            }
            //Close the scanners
            rbw.close();
            ibw.close();
        } catch (IOException e) { ///Notify the user that the file was not created properly
            System.out.println("File Not Found: " + e.getMessage());
        }
    }

    /**
     * Main method of the BenchmarkSorts program, defines the length of the n values
     * @param args Command line arguments (if any)
     */
    public static void main(String[] args){
        //Testing Values of n
        //int[] sizes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        //int[] sizes = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        int[] sizes = {10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000};
        //Create a new instance of BenchmarkSorts and load the specified sizes of n
        BenchmarkSorts bs = new BenchmarkSorts(sizes);
        //Sort the randomized n values
        bs.performSorts();
        //Calculate the averages and coefficients
        bs.calculateData();
        //Create the report files for input into the BenchmarkReports app
        bs.generateReport();
    }
}
