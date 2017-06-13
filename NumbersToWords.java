import java.util.Scanner;

/**
 * Turns the numbers to their correspondence words (Arabic Language)
 *
 * @author Waleed Mortaja, contact Email :
 * <a href="mailto:waleed.mortaja@gmail.com">waleed.mortaja@gmail.com</a>
 */
public class NumbersToWords {

    private static String number; // the number that the user inputs. It was defined as static to be able to use it in "convert" method

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String substring; // takes three digits (or less) every loop
        String convertedNumber; // the corresondence String for the number
        String numberWithComma; // the entered number with commas every three digits
        int place; // the oderer of every three digits (or less)
        Scanner in = new Scanner(System.in);
        boolean error; // checks if the input is OK

        while (true) { // infinity loop (convert as many numbers as the user want)

            // take inputs and check them
            do {

                // initialize these three variables every loop
                convertedNumber = "";
                numberWithComma = "";
                error = false;

                System.out.print("  : أدخل عددا صحيحا طويلا موجبا أو أدخل سالب واحد للخروج");

                if (in.hasNext()) {
                    number = in.next(); // the static number (whole number)
                    if (number.equals("-1")) { // if the input is (-1), exit
                        System.exit(0);
                    }
                } else { // if the input is NOT String somehow
                    error = true;
                }

                try {

                    if (Long.parseLong(number) < 0) { // if input is negative 
                        error = true;
                        System.out.println("Error: أدخل عددا صحيحا طويلا موجبا فقط\n");
                    }

                } catch (NumberFormatException e) { // if inputs can NOT be prased as integer
                    error = true;
                    System.out.println("Error: أدخل عددا صحيحا طويلا موجبا فقط\n");
                }

            } while (error);

            //The Start of the code
            // split the code every three parts starting from the last digits
            if (Long.parseLong(number) == 0) {

                System.out.println("لقد أدخلت : " + 0);
                System.out.println("صفر");
                System.out.println("\n"); // two new lines for the coming run
                main(args);  // rerun the program as it is NOT required to contine to the other parts of the program

            } else if (number.length() % 3 != 0) { // if the digits are not from 3 mulitplications ( ! 3K )

                substring = number.substring(0, number.length() % 3);
                place = number.length() / 3;  // because the reminder will be ingnored automatically. If it has three digits, the compiler wont even reach this
                convertedNumber += converToString(substring, place);

                if (number.length() > 3) { // add comma if there are more than three digits
                    numberWithComma += substring + ',';

                } else {
                    numberWithComma += substring;
                }
            }
            for (int i = number.length() % 3; i < number.length() - number.length() % 3; i += 3) {
                substring = number.substring(i, i + 3);
                place = ((number.length() - i) / 3) - 1; //because the number is consisting from (multi) three digits, so for the first third, " (number.length()-i) / 3) " will computes 1 and I want its place as zero, so I decreased it by 1. and so on for the other triples.
                convertedNumber += converToString(substring, place);

                if (place > 0) { // if there are still digits in the right add comma, When place is zero, then the proccessing three digits are the last three digits
                    numberWithComma += substring + ',';

                } else {
                    numberWithComma += substring;

                }
            }
            System.out.println("لقد أدخلت : " + numberWithComma);
            System.out.println(convertedNumber);
            System.out.println("\n"); // two new lines for the coming run

        }
    }

    /**
     * Conver the numbers to the correspondence number in letters. this method
     * works with the help of this code, ie , it needs the number to be divide
     * to three digits and also to have a static variable called number (its max
     * is three digits and you have to enter the place of these three digits)
     *
     * @param substring the number to be converted (Enter it as String, and it
     * must be three digits at most)
     * @param place the order of these three digits, ie , thousand ,millions,
     * billions.
     * @return result the corresondence String for the number
     */
    public static String converToString(String substring, int place) {
        String result = "";
        String[] ones = {"", "واحد", "اثنان", "ثلاثة", "أربعة", "خمسة", "ستة", "سبعة", "ثمانية", "تسعة", "عشرة", "أحد عشر", "اثنا عشر", "ثلاثة عشر", "أربعة عشر", "خمسة عشر", "ستة عشر", "سبعة عشر", "ثمانية عشر", "تسعة عشر"};
        String[] tens = {"", "عشرة", "عشرون", "ثلاثون", "أربعون", "خمسون", "ستون", "سبعون", "ثمانون", "تسعون", "مئة"};
        String[] hundreds = {"", "مئة", "مئتان", "ثلاث مئة", "أربع مئة", "خمس مئة", "ست مئة", "سبع مئة", "ثماني مئة", "تسع مئة"};
        String[] places = {"", " آلاف", " ملايين", " مليارات", " بلايين", " بليارات", " تريليونات", " تريليارات"};
        String[] placesOdd = {"", " ألف", " مليون", " مليار", " بليون", " بليار", " تريليون", " تريليار"};

        int subNumber = Integer.parseInt(substring);  // store the substring as integer in the variable called subName

        if (subNumber == 0) { // if there is a three digits equlas zero, then do NOT add any thing to the String of letters
            return "";
        } else if (subNumber > 0 && subNumber % 100 <= 19 && subNumber / 100 == 0) { // the number has only ones and tens (NO hundreds) less than 19   

            if (subNumber == 1 && place != 0) { // (thousand) is a special case (In relation to hundreds, they had been fixed using tbe array and I don not want to rewrite its code as this block)
                result += placesOdd[place];

            } else if (subNumber == 2 && place != 0) {//two thousand is a special case too

                result += placesOdd[place] + "ان";

            } else {

                result += ones[subNumber] + places[place];

            }

        } else {

            if (subNumber / 100 == 0) {         // if the number does NOT have hundreds 
                if (subNumber % 10 == 0) {      // if the number does NOT have ones (tens only)
                    if (place == 0) { //  اذا كان العدد في الثلاث خانات الأوائل أي المكان يساوي صفر  إذن لا تطبع المكان و ذلك لأنه صفر أي لا يوجد تمييز و لا تطبع تنوين الفتح
                        result += tens[(subNumber / 10)];
                    } else {
                        result += tens[(subNumber / 10)] + placesOdd[place] + "اً";

                    }
                } else {                     // if the number has ones and tens only
                    if (place == 0) { // if the ones and tens are in the first three digits(place ==0), then there is no need for the place neither for "اً"
                        result += ones[subNumber % 10] + " و " + tens[subNumber / 10];

                    } else {
                        result += ones[subNumber % 10] + " و " + tens[subNumber / 10] + placesOdd[place] + "اً";
                        /* Note: in relation to tens, there is no need to write its index as (number/10)%10 because there
                           is no hundrerds, so the tens are always less than 10*/

                    }
                }

            } else {                         // if the number DOES have hundreds

                if (subNumber % 10 == 0) {      // if the number does NOT have ones (hundreds and tens)

                    if (subNumber / 10 % 10 == 0) {  // if the number does NOT have tens(hundres only)
                        result += hundreds[subNumber / 100] + placesOdd[place]; // المئات لا تحتاج تنوين فتح مضافا تمييزها

                    } else {  // the number DOES have hundreds and tens only
                        if (subNumber % 100 == 10) { //الأعداد التي أقل من أو تساوي عشرة تأخذ كلمة جمع مثل آلاف أما الأكبر من العشرة مثلا عشرون يأخذ كلمة مفردة مثلا ألف و نظرا لأن العدد الذس يصل إلى هنا يتكون من مئات و عشرات فقط   إذن هذا الشرط يكفي أن يختبر الرقم عشرة فقط
                            result += hundreds[subNumber / 100] + " و " + tens[1] + places[place];

                        } else {
                            if (place == 0) {
                                result += hundreds[subNumber / 100] + " و " + tens[(subNumber / 10) % 10]; // لا حاجة للمكان ولا لتنوين الفتح
                            } else {
                                result += hundreds[subNumber / 100] + " و " + tens[(subNumber / 10) % 10] + placesOdd[place] + "اً";
                            }
                        }
                    }

                } else {                     // if the number DOES have hundreds and ones
                    if ((subNumber / 10) % 10 == 0) {  // if the number does NOT have tens(hundres and ones only)
                        if (subNumber % 10 > 0 && subNumber % 10 <= 2) { // ones less than or equal 2 takes placesOdd
                            result += hundreds[subNumber / 100] + " و " + ones[subNumber % 10] + placesOdd[place];
                        } else {
                            result += hundreds[subNumber / 100] + " و " + ones[subNumber % 10] + places[place];
                        }
                    } else {  // the number DOES have the three digits (all)

                        // note that hunders + 10  and hundreds + ones (ones less than 10) had been proccessed previously
                        if (subNumber % 100 > 10 && subNumber % 100 <= 19) { // all digits and ones are less or equal to 19
                            result += hundreds[subNumber / 100] + " و " + ones[subNumber % 100] + placesOdd[place] + "اً";

                        } else { // all the digits with ones more than 19
                            if (place == 0) { // if the three digits are the first three digits from the right, then there is no place required nor تنوين فتح
                                result += hundreds[subNumber / 100] + " و " + ones[subNumber % 10] + " و " + tens[(subNumber / 10) % 10];
                            } else {
                                result += hundreds[subNumber / 100] + " و " + ones[subNumber % 10] + " و " + tens[(subNumber / 10) % 10] + placesOdd[place] + "اً";
                            }
                        }
                    }
                }

            }

        }
        if (Long.parseLong(IntegerToString.number) % (Math.pow(1000, place)) != 0) { // if there is still numbers( NOT equal zero) in the remainig number print "و" , ie, place 3 have 9 zeros which is writen as 1,000,000 = (Math.pow(1000, 3)
            if (Long.parseLong(IntegerToString.number) / (int) (Math.pow(1000, (place - 1))) % 1000 <= 2) { // if the next three digits are less than or equal to 2 then add "و" without a space after it ( place -1 to delete all the digits before [the three digits which are before the current being-procced digit(s)] .... %1000 to take just three digits, so not take the digits next to it )
                result += " و"; // print "و" without a space after it, beaces places already have spaces before them in their definitions
            } else {
                result += " و ";
            }
        }
        return result;
    }
}
