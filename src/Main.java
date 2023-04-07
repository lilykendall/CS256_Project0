import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //Creating command line options
        LongOpt[] CommandOpts = {

                new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),

                new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm'),

                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h')
        };

        Getopt opt = new Getopt("Project 0", args, "hm:v", CommandOpts);
        opt.setOpterr(true);

        //Scanning in the file
        Scanner scan = new Scanner(System.in);


        int choice; // will contain the command line options
        String mode = ""; //empty until the mode command line option is read in
        int first = scan.nextInt(); // the amount of numbers (or how many should be) in the file.
        String[] printOut = new String[3]; // will contain all the strings to print out

        while ((choice = opt.getopt()) != -1) {
            switch (choice) {
                case 'h':
                    printHelp();
                    System.exit(0);
                    break;

                case 'm':
                    mode = opt.getOptarg();
                    double sum = 0.0; // sum for calculating the average.
                    double[] nums = new double[first]; // array that contains all the numbers in the file.
                    int index = 0; // index of nums.

                    while(scan.hasNextDouble()){
                        try {
                            nums[index] = (scan.nextDouble());
                            index++;

                        }
                        catch(IndexOutOfBoundsException e){ // will print an error if there are more numbers than the first line specifies.
                            System.err.println("Error: more inputs than specified.");
                            System.exit(1);
                        }
                    }
                    Arrays.sort(nums); // sorts all the numbers in the file in numerical order

                    if(index == 0){
                        System.out.println("No data => no statistics!");
                        System.exit(0);
                    }

                    if (mode.equals("average")) {
                        printOut[2] = average(first, sum, nums);
                    }

                    if(mode.equals("median")){
                        printOut[2] = median(nums, first);
                    }

                    if (!mode.equals("average") && !mode.equals("median")) { // prints an error if there is no median or average after mode.
                        System.err.println("Error: invalid mode" + mode);
                        System.exit(1);
                    }
                    break;


                    case 'v':
                        printOut[0] = "Reading " + first + " numbers.";
                        printOut[1] = "Read " + first + " numbers.";
                        break;




                    default: // if none of the other cases match this will run.
                        System.err.println("Error: invalid option");
                        System.exit(1);
                        break;

                    } //switch

            } //while loop

            if(mode.equals("")){ // if there is no mode in the command line an error is returned.
                System.err.println("Error: no --mode in command line.");
                System.exit(1);
            }

            for(int index = 0; index < 3; index++){ // prints out verbose first, then average or median (if they are in the command line).
                if(printOut[index] == null){
                    index++;
                }
                else{
                    System.out.println(printOut[index]);
                }
            }


        } //public main

    public static String average(double size, double sum,double[] nums){
        for(int index = 0; index < size; index++){
            sum += nums[index];
        }

        sum = round((sum * 100.0) / size) / 100.0;
        return "Average: " + sum;
    }

    public static String median(double[] nums, int size){
        double med = (double)round(nums[size/2] * 1000) / 1000;
        return "Median: " + med;
    }
    public static void printHelp() {
        System.out.println("Usage: java [options] Main [-m average|median]| [-h] | [-v]");
        System.out.println("This program is an example of processing command");
        System.out.println("line arguments with Getopt.");
    }



} // Main

