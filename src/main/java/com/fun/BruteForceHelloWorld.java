package com.fun;

import java.util.Random;

import static com.fun.GeneticHelloWorld.TARGET;

public class BruteForceHelloWorld {

    private static final Random rand = new Random(0);

    public static void main(String[] args) {

        int length = TARGET.length();
        String attempt = "";
        int i = 0;

        while (!attempt.equals(TARGET)) {
            attempt = generateRandomString(length);
            System.out.println("Attempt " + i++ + ": " + attempt);
        }

    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(randomChar());
        }

        return sb.toString();
    }

    private static char randomChar() {
        return (char) (rand.nextInt(90) + 32);
    }
}
