import java.util.Scanner;

/**
 * Manages dental records for up to six family members.
 * Stores upper and lower teeth, prints records, extracts teeth,
 * and calculates root canal indices.
 *
 * @author alcearoseboudou
 */

public class DentalRecords {
    /**
     * Maximum people in a family
     */
    private static final int MAX_PEOPLE = 6;
    /**
     * Maximum teeth in a row
     */
    private static final int MAX_TEETH = 8;

    private static final Scanner keyboard = new Scanner(System.in);

    /**
     * Runs the dental records program.
     * Collects family information and processes menu selections
     * until the user chooses to exit.
     *
     * @param args command-line arguments
     */

    public static void main(String[] args) {

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        int numberOfPeople = readFamilySize();
        String[] familyNames = new String[numberOfPeople];
        char[][][] familyTeeth = new char[numberOfPeople][2][];

        for (int index = 0; index < numberOfPeople; index++) {
            System.out.print("Please enter name for family member " + (index + 1) + ": ");
            familyNames[index] = keyboard.next();

            System.out.print("Please enter the uppers for " + familyNames[index] + ": ");
            String upperTeeth;

            do {
                upperTeeth = keyboard.next().toUpperCase();

                if (!isValidTeeth(upperTeeth)) {
                    if (upperTeeth.length() > MAX_TEETH) {
                        System.out.print("Too many teeth, try again:");
                    } else {
                        System.out.print("Invalid teeth types, try again:");
                    }
                }


            } while (!isValidTeeth(upperTeeth));

            familyTeeth[index][0] = upperTeeth.toCharArray();

            System.out.print("Please enter the lowers for " + familyNames[index] + ": ");

            String lowerTeeth;

            do {
                lowerTeeth = keyboard.next().toUpperCase();

                if (!isValidTeeth(lowerTeeth)) {
                    if (lowerTeeth.length() > MAX_TEETH) {
                        System.out.print("Too many teeth, try again");
                    } else {
                        System.out.print("Invalid teeth types, try again");
                    }

                    System.out.print("Enter lower teeth for " + familyNames[index] + ": ");
                }

            } while (!isValidTeeth(lowerTeeth));

            familyTeeth[index][1] = lowerTeeth.toCharArray();

        }// end of for loop asking for upp/low teeth

        char menuChoice;

        do {

            menuChoice = readMenuChoice();

            switch (menuChoice) {

                case 'P':
                    printRecords(familyNames, familyTeeth);
                    break;

                case 'E':
                    extractTooth(familyNames, familyTeeth);
                    break;

                case 'R':
                    calculateRoots(familyTeeth);
                    break;

                case 'X':
                    System.out.println("Exiting the Floridian Tooth Records :-)");
                    break;

                default:
                    System.out.print("Invalid menu option, try again:");
                    break;
            }

        } while (menuChoice != 'X');


    }// end of main

    /**
     * Asks the user for the number of family members.
     * Makes sure that the number is between 1 and 6.
     *
     * @return the valid number of family members
     */

    private static int readFamilySize() {

        int numberOfPeople = 0;
        System.out.print("Please enter number of people in the family: ");

        do {

            if (keyboard.hasNextInt()) {
                numberOfPeople = keyboard.nextInt();
            } else {
                keyboard.next();
                numberOfPeople = 0;
            }

            if (numberOfPeople < 1 || numberOfPeople > MAX_PEOPLE) {
                System.out.print("Invalid number of people, try again:");
            }

        } while (numberOfPeople < 1 || numberOfPeople > MAX_PEOPLE);

        return numberOfPeople;
    }// end of readFamilySize


    /**
     * Checks whether a row of teeth contains between 1 and 8
     * valid tooth characters: I, B, or M.
     *
     * @param teeth the string of tooth characters to validate
     * @return true if the teeth are valid, false otherwise
     */

    private static boolean isValidTeeth(String teeth) {
        if (teeth.length() < 1 || teeth.length() > MAX_TEETH) {
            return false;
        }
        for (int index = 0; index < teeth.length(); index++) {
            char tooth = Character.toUpperCase(teeth.charAt(index));

            if (tooth != 'I' && tooth != 'B' && tooth != 'M') {
                return false;
            }
        }
        return true;

    }// end of isValidTeeth


    /**
     * Displays the menu and reads the user's selection.
     * Converts the selection to uppercase.
     *
     * @return the selected menu character
     */

    private static char readMenuChoice() {
        char choice;

        System.out.println();
        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");

        do {
            String input = keyboard.next().toUpperCase();

            if (input.length() == 1) {
                choice = input.charAt(0);
            } else {
                choice = '?';
            }

            if (choice != 'P' && choice != 'E' &&
                    choice != 'R' && choice != 'X') {
                System.out.print("Invalid menu option, try again : ");
            }

        } while (choice != 'P' && choice != 'E' &&
                choice != 'R' && choice != 'X');

        return choice;
    }// end of readMenuChoice method

    /**
     * Prints each family members name and numbered
     * upper and lower teeth
     *
     * @param familyNames the names of the family members
     * @param familyTeeth the upper and lower teeth of each member
     */


    private static void printRecords(String[] familyNames, char[][][] familyTeeth) {

        for (int person = 0; person < familyNames.length; person++) {

            System.out.println();
            System.out.println(familyNames[person]);

            System.out.print("  Uppers: ");

            for (int tooth = 0; tooth < familyTeeth[person][0].length; tooth++) {
                System.out.print((tooth + 1) + ":" + familyTeeth[person][0][tooth] + "  ");
            }

            System.out.println();

            System.out.print("  Lowers:  ");

            for (int tooth = 0; tooth < familyTeeth[person][1].length; tooth++) {
                System.out.print((tooth + 1) + ":" + familyTeeth[person][1][tooth] + "  ");
            }

            System.out.println();
        }
    }//end of printRecords method

    /**
     * Allows the user to select a family member and extract a tooth
     * Validates the family member, tooth row and tooth number ]
     * changes the selected toot to M for missing
     *
     * @param familyNames the names of the family members
     * @param familyTeeth the upper and lower teeth of each member
     */
    private static void extractTooth(String[] familyNames, char[][][] familyTeeth) {

        String name;
        int personIndex = -1;

        System.out.print("Which family member? ");
        do {
            personIndex = -1;
            name = keyboard.next();

            for (int index = 0; index < familyNames.length; index++) {
                if (familyNames[index].equalsIgnoreCase(name)) {
                    personIndex = index;
                    break;
                }
            }

            if (personIndex == -1) {
                System.out.print("Invalid family member, try again:");
            }

        } while (personIndex == -1);
        char layer;

        System.out.print("Which tooth layer (U)pper or (L)ower: ");

        do {
            layer = Character.toUpperCase(keyboard.next().charAt(0));

            if (layer != 'U' && layer != 'L') {
                System.out.print("Invalid layer, try again:");
            }

        } while (layer != 'U' && layer != 'L');


        int layerIndex;
        if (layer == 'U') {
            layerIndex = 0;
        } else {
            layerIndex = 1;
        }
        boolean allMissing = true;

        for (int tooth = 0; tooth < familyTeeth[personIndex][layerIndex].length; tooth++) {

            if (familyTeeth[personIndex][layerIndex][tooth] != 'M') {
                allMissing = false;
                break;
            }
        }
// return to the menu because no teeth are available to extract
        if (allMissing) {
            System.out.println("All teeth in this row are already missing.");
            return;
        }
        int toothNumber;
        int toothIndex = 0;
        int numberOfTeeth = familyTeeth[personIndex][layerIndex].length;

        boolean validTooth = false;
        System.out.print("Which tooth number: ");
        do {


            if (keyboard.hasNextInt()) {

                toothNumber = keyboard.nextInt();

                if (toothNumber >= 1 && toothNumber <= numberOfTeeth) {

                    toothIndex = toothNumber - 1;

                    if (familyTeeth[personIndex][layerIndex][toothIndex] != 'M') {
                        validTooth = true;
                    } else {
                        System.out.print("Missing tooth, try again:");
                    }

                } else {
                    System.out.print("Invalid tooth number, try again:");
                }

            } else {
                keyboard.next();
                System.out.print("Invalid tooth number, try again:");
            }

        } while (!validTooth);

        familyTeeth[personIndex][layerIndex][toothIndex] = 'M';

        System.out.println("Tooth extracted successfully.");
    }// end of extractTooth method

    /**
     * Counts incisors, bicuspids and missing teeth
     * and calculates the root canal indices
     * using the quadratic formula where it can be applicable
     *
     * @param familyTeeth the upper and lower teeth of each family member
     */

    private static void calculateRoots(char[][][] familyTeeth) {

        int incisors = 0;
        int bicuspids = 0;
        int missing = 0;

        for (int person = 0; person < familyTeeth.length; person++) {

            for (int layer = 0; layer < familyTeeth[person].length; layer++) {

                for (int tooth = 0; tooth < familyTeeth[person][layer].length; tooth++) {

                    char toothType = familyTeeth[person][layer][tooth];

                    if (toothType == 'I') {
                        incisors++;
                    } else if (toothType == 'B') {
                        bicuspids++;
                    } else if (toothType == 'M') {
                        missing++;
                    }
                }
            }
        }


        if (incisors > 0) {

            double discriminant = bicuspids * bicuspids + 4.0 * incisors * missing;

            double root1 = (-bicuspids + Math.sqrt(discriminant)) / (2.0 * incisors);

            double root2 = (-bicuspids - Math.sqrt(discriminant)) / (2.0 * incisors);

            System.out.printf("One root canal at %.2f%n", root1);
            System.out.printf("Another root canal at %.2f%n", root2);

        } else if (bicuspids > 0) {

            double root = (double) missing / bicuspids;

            System.out.printf("One root canal at %.2f%n", root);

        } else {

            System.out.println("No unique root canal index can be calculated.");

        }
    }// end of calculateRoots method

}// end of DentalRecords class
