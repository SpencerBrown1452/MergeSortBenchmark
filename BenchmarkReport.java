/**
 * File: BenchmarkReport.java
 * Date: 7 February 2021
 * Author: Spencer Brown
 * Purpose: To display in a JFrame the data collected from calculating the efficiency
 * of the mergeSort algorithm, both recursive and iterative implementations
 */

//Import statements
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BenchmarkReport {
    //Holds the user's file choice
    private File file;
    //Parses the file contents
    private Scanner scanner;

    /**
     * Display's the report in the a beautiful JTable displayed in a window
     */
    public void displayBenchmarkReport(){
        //Creates a JFrame to hold the benchmark data
        JFrame frame = new JFrame();
        frame.setTitle("Benchmark Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,225);

        //Holds the data in the file for transfer to the JTable
        String[][] data = new String[10][5];
        //Allows the user to select the input file
        JFileChooser chooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".txt Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        //If the file is accepted, load the file and parse the contents
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            try{
                //Initiate the scanner on the file
                scanner = new Scanner(file);
                while(scanner.hasNext()){
                    //For every row of the JTable
                    for(int i = 0; i < 10; i++){
                        //For every column in the row
                        for(int j = 0; j < 5; j++){
                            if(j == 2 || j == 4){// Converts the coefficients to a percent format
                                //Print the coeffients to the JTable data
                                data[i][j] = String.format("%,.2f", scanner.nextDouble() * 100) + "%";
                            }else{
                                //Print the n count, avg count, and avg time to the JTable data
                                data[i][j] = scanner.next();
                            }
                        }
                    }
                }
            }catch (FileNotFoundException e){ //If the file is not located
                JOptionPane.showMessageDialog(null, "File not found");
            }
        }
        else{
            System.exit(0);
        }
        String[] columnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
        JTable benchmarkTable = new JTable(data, columnNames);
        JScrollPane benchmarkScroller = new JScrollPane(benchmarkTable);

        //Inserts the data into the frame and displays the frame
        frame.getContentPane().add(BorderLayout.CENTER, benchmarkScroller);
        frame.setVisible(true);
    }
}
