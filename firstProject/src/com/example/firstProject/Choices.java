package com.example.firstProject;

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Choices {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nMenu\n");
            System.out.print("-----------------------\n");

            // Mathematics
            System.out.print("1. Factorials\n");
            System.out.print("2. Fibonacci numbers\n");
            System.out.print("3. Armstrong numbers\n");
            System.out.print("4. Greatest common divisor and Least common multiple\n");
            System.out.print("5. Binomial coefficient\n");
            System.out.print("6. Primality Testing\n");
            System.out.print("7. Sums of multiples\n");
            System.out.print("8. Proper divisors\n");
            System.out.print("9. Perfect numbers\n");
            System.out.print("10. Statistical Function\n");

            // Dates and Times
            System.out.print("11. Days of the week\n");
            System.out.print("12. Time Zone Chart\n");
            System.out.print("13. Time Zone Clocks\n");
            System.out.print("14. Calculating Duration\n");
            System.out.print("15. Age Calculator\n");

            // Randomization
            System.out.print("16. Random items\n");
            System.out.print("17. Random doubles\n");
            System.out.print("18. Randomise items\n");
            System.out.print("19. Random groups\n");
            System.out.print("20. Password generator\n");

            // Strings
            System.out.print("21. String compression\n");
            System.out.print("22. Remove punctuation\n");
            System.out.print("23. Edit distance\n");
            System.out.print("24. Soundex\n");
            System.out.print("25. Longest common substring\n");
            System.out.print("26. One-away strings\n");

            // Other
            System.out.print("27. Permutations\n");
            System.out.print("28. Combinations\n");
            System.out.print("29. Roman numerals\n");
            System.out.print("30. Bytes to Hex\n");

            System.out.print("\n31. Exit\n");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    launchFactorialCalculator();
                    break;
                case 2:
                    launchFibonacci();
                    break;
                case 3:
                    launchArmstrong();
                    break;
                case 4:
                    launchGcdLcmFinder();
                    break;
                case 5:
                    launchBinomialCoefficient();
                    break;
                case 6:
                    launchPrime();
                    break;
                case 7:
                    launchSoM();
                    break;
                case 8:
                    launchProperDivisor();
                    break;
                case 9:
                    launchPerfectNumbers();
                    break;
                case 10:
                    launchStatistics();
                    break;
                case 11:
                    launchDoW();
                    break;
                case 12:
                    launchTimeZoneChart();
                    break;
                case 13:
                    launchTimeZoneClocks();
                    break;
                case 14:
                    launchCalculatingDuration();
                    break;
                case 15:
                    launchAgeCalculator();
                    break;
                case 16:
                    launchRandomItems();
                    break;
                case 17:
                    launchRandomDoubles();
                    break;
                case 18:
                    launchRandomiseItems();
                    break;
                case 19:
                    launchRandomGroups();
                    break;
                case 20:
                    launchPasswordGenerator();
                    break;
                case 21:
                    launchStringCompressor();
                    break;
                case 22:
                    launchRemovePunctuation();
                    break;
                case 23:
                    launchEditDistance();
                    break;
                case 24:
                    launchSoundex();
                    break;
                case 25:
                    launchLongestCommonSubstring();
                    break;
                case 26:
                    launchOneAway();
                    break;
                case 27:
                    launchPermutations();
                    break;
                case 28:
                    launchCombinations();
                    break;
                case 29:
                    launchRomanNumerals();
                    break;
                case 30:
                    launchBytesToHex();
                    break;
                case 31:
                    System.out.println("Thank You");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 31.");
            }
        }
    }

    private static void setFocus(JFrame window) {
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        window.toFront();
        window.requestFocusInWindow();
        window.setAlwaysOnTop(false);
    }

    private static void launchFactorialCalculator() {
        SwingUtilities.invokeLater(() -> {
            FactorialCalculator calculator = new FactorialCalculator();
            setFocus(calculator);
        });
    }

    private static void launchFibonacci() {
        SwingUtilities.invokeLater(() -> {
            FibonacciCalculator fb = new FibonacciCalculator();
            setFocus(fb);
        });
    }

    private static void launchArmstrong() {
        SwingUtilities.invokeLater(() -> {
            ArmstrongNumberFinder ar = new ArmstrongNumberFinder();
            setFocus(ar);
        });
    }

    private static void launchGcdLcmFinder() {
        SwingUtilities.invokeLater(() -> {
            GCD_LCMFinder gl = new GCD_LCMFinder();
            setFocus(gl);
        });
    }

    private static void launchBinomialCoefficient() {
        SwingUtilities.invokeLater(() -> {
            BinomialCoefficient bn = new BinomialCoefficient();
            setFocus(bn);
        });
    }

    private static void launchPrime() {
        SwingUtilities.invokeLater(() -> {
            PrimeNumberChecker pr = new PrimeNumberChecker();
            setFocus(pr);
        });
    }

    private static void launchSoM() {
        SwingUtilities.invokeLater(() -> {
            SumsOfMultiples sm = new SumsOfMultiples();
            setFocus(sm);
        });
    }

    private static void launchProperDivisor() {
        SwingUtilities.invokeLater(() -> {
            ProperDivisors pd = new ProperDivisors();
            setFocus(pd);
        });
    }

    private static void launchPerfectNumbers() {
        SwingUtilities.invokeLater(() -> {
            PerfectNumbers pf = new PerfectNumbers();
            setFocus(pf);
        });
    }

    private static void launchStatistics() {
        SwingUtilities.invokeLater(() -> {
            StatisticalFunction st = new StatisticalFunction();
            setFocus(st);
        });
    }

    private static void launchDoW() {
        SwingUtilities.invokeLater(() -> {
            DaysOfTheWeek dw = new DaysOfTheWeek();
            setFocus(dw);
        });
    }

    private static void launchTimeZoneChart() {
        SwingUtilities.invokeLater(() -> {
            TimeZoneChart tz = new TimeZoneChart();
            setFocus(tz);
        });
    }

    private static void launchTimeZoneClocks() {
        SwingUtilities.invokeLater(() -> {
            TimeZoneClocks tc = new TimeZoneClocks();
            setFocus(tc);
        });
    }

    private static void launchCalculatingDuration() {
        SwingUtilities.invokeLater(() -> {
            CalculatingDuration cd = new CalculatingDuration();
            setFocus(cd);
        });
    }

    private static void launchAgeCalculator() {
        SwingUtilities.invokeLater(() -> {
            AgeCalculator ac = new AgeCalculator();
            setFocus(ac);
        });
    }

    private static void launchRandomItems() {
        SwingUtilities.invokeLater(() -> {
            RandomItem rd = new RandomItem();
            setFocus(rd);
        });
    }

    private static void launchRandomDoubles() {
        SwingUtilities.invokeLater(() -> {
            RandomDoubles rd = new RandomDoubles();
            setFocus(rd);
        });
    }

    private static void launchRandomiseItems() {
        SwingUtilities.invokeLater(() -> {
            RandomiseItems ri = new RandomiseItems();
            setFocus(ri);
        });
    }

    private static void launchRandomGroups() {
        SwingUtilities.invokeLater(() -> {
            RandomGroups rg = new RandomGroups();
            setFocus(rg);
        });
    }

    private static void launchPasswordGenerator() {
        SwingUtilities.invokeLater(() -> {
            PasswordGenerator pg = new PasswordGenerator();
            setFocus(pg);
        });
    }

    private static void launchStringCompressor() {
        SwingUtilities.invokeLater(() -> {
            CompressedString cs = new CompressedString();
            setFocus(cs);
        });
    }

    private static void launchRemovePunctuation() {
        SwingUtilities.invokeLater(() -> {
            RemovePunctuation rp = new RemovePunctuation();
            setFocus(rp);
        });
    }

    private static void launchEditDistance() {
        SwingUtilities.invokeLater(() -> {
            EditDistance ed = new EditDistance();
            setFocus(ed);
        });
    }

    private static void launchSoundex() {
        SwingUtilities.invokeLater(() -> {
            Soundex sd = new Soundex();
            setFocus(sd);
        });
    }

    private static void launchLongestCommonSubstring() {
        SwingUtilities.invokeLater(() -> {
            LongestCommonSubstring lcs = new LongestCommonSubstring();
            setFocus(lcs);
        });
    }

    private static void launchOneAway() {
        SwingUtilities.invokeLater(() -> {
            OneAway oa = new OneAway();
            setFocus(oa);
        });
    }

    private static void launchPermutations() {
        SwingUtilities.invokeLater(() -> {
            Permutations pm = new Permutations();
            setFocus(pm);
        });
    }

    private static void launchCombinations() {
        SwingUtilities.invokeLater(() -> {
            Combinations cb = new Combinations();
            setFocus(cb);
        });
    }

    private static void launchRomanNumerals() {
        SwingUtilities.invokeLater(() -> {
            RomanNumerals rn = new RomanNumerals();
            setFocus(rn);
        });
    }

    private static void launchBytesToHex() {
        SwingUtilities.invokeLater(() -> {
            BytesToHex bh = new BytesToHex();
            setFocus(bh);
        });
    }
}
