
package com.fun;


public class GeneticHelloWorld {


    private static int generationNumber = 0;
    public static final String PERFECT_FITNESS = "Yes";

    public static void main(String[] args) {

        Population population = new Population();

        while (!population.isPerfectFitness()) {
            System.out.println("Generation " + generationNumber++ + ": " + population.getFittest());
            population.evolve();
        }

    }

}