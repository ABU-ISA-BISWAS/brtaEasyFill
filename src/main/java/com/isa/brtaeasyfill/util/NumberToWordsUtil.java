package com.isa.brtaeasyfill.util;

public class NumberToWordsUtil {
    public static String convertNumberToWords(String numberStr) {
        try {
            long number = Long.parseLong(numberStr);
            return convert(number).trim() ;
        } catch (Exception e) {
            return "";
        }
    }

    private static String convert(long number) {
        if (number == 0) return "Zero";

        StringBuilder result = new StringBuilder();

        if (number >= 10000000) {
            result.append(convertBelowThousand((int)(number / 10000000))).append(" Crore ");
            number %= 10000000;
        }
        if (number >= 100000) {
            result.append(convertBelowThousand((int)(number / 100000))).append(" Lakh ");
            number %= 100000;
        }
        if (number >= 1000) {
            result.append(convertBelowThousand((int)(number / 1000))).append(" Thousand ");
            number %= 1000;
        }
        if (number >= 100) {
            result.append(convertBelowThousand((int)(number / 100))).append(" Hundred ");
            number %= 100;
        }
        if (number > 0) {
            result.append(convertBelowThousand((int)number)).append(" ");
        }

        return result.toString();
    }

    private static String convertBelowThousand(int number) {
        String[] units = {
            "", "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen",
            "Fifteen", "Sixteen", "Seventeen", "Eighteen",
            "Nineteen"
        };

        String[] tens = {
            "", "", "Twenty", "Thirty", "Forty",
            "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };

        if (number < 20)
            return units[number];

        if (number < 100)
            return tens[number / 10] + (number % 10 != 0 ? " " + units[number % 10] : "");

        return units[number / 100] + " Hundred " + (number % 100 != 0 ? convertBelowThousand(number % 100) : "");
    }
}
