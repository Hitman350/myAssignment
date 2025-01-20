
import java.util.*;

import javax.swing.SwingUtilities;

import com.example.firstProject.FactorialCalculator;
import com.example.firstProject.FibonacciCalculator;
public class choices{
    public static void main(String[] args) {
        while(true){
            System.out.println("\nMathematics");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Factorials");
            System.out.println("2. Fibonacci numbers");
            System.out.println("3. Armstrong numbers");
            System.out.println("4. Greatest common divisor and Least common multiple");
            System.out.println("5. Binomial coefficient");
            System.out.println("6. Primality Testing");
            System.out.println("7. Sums of multiples");
            System.out.println("8. Proper divisors");
            System.out.println("9. Perfect numbers");
            System.out.println("10. Statistical Function");
    
            System.out.println("\nDates and Times");
            System.out.println("------------------------------------------------------");
            System.out.println("11. Days of the week");
            System.out.println("12. Time zone chart");
            System.out.println("13. Time zone clock");
            System.out.println("14. Calculating duration");
            System.out.println("15. Calculating age");
            System.out.println("\nRandomization");
            System.out.println("------------------------------------------------------");
            System.out.println("16. Random doubles");
            System.out.println("17. Random items");
            System.out.println("18. Randomize items");
            System.out.println("19. Random groups");
            System.out.println("20. Random passwords");
    
            System.out.println("\nStrings");
            System.out.println("------------------------------------------------------");
            System.out.println("21. Roman numerals");
            System.out.println("22. Bytes to hex");
            System.out.println("23. Removing punctuation");
            System.out.println("24. Edit distance");
            System.out.println("25. Soundex");
            System.out.println("26. Longest common substring");
            System.out.println("27. One way");
            System.out.println("28. Permutations");
            System.out.println("29. Combinations");
            System.out.println("30. String compression");
    
            System.out.println("\nLogic");
            System.out.println("------------------------------------------------------");
            System.out.println("31. Bonus - The heavy pill");
    
            System.out.println("\n32. Exit");

            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter your choice: ");
            int n = sc.nextInt();

            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    launchFactorialCalculator();
                    break;
                case 2:
                    launchFibonacci();
                    break;
            }    
        }
    
    }

    public static void launchFactorialCalculator() {
        SwingUtilities.invokeLater(() -> {
            FactorialCalculator calculator = new FactorialCalculator();
            calculator.setVisible(true);
         
        }); 
    }

    private static void launchFibonacci(){
        FibonacciCalculator fb = new FibonacciCalculator();
        fb.setVisible(true);
    }

}

