
package com.fun;


public class GeneticHelloWorld {


    private static int generationNumber = 0;
    public static final String TARGET = "Yes";

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Population population = new Population();

        Individual fittest = population.getFittest();

        while (!fittest.isPerfectFitness()) {
            System.out.println("Generation " + generationNumber++ + ": " + fittest);
            population.evolve();
            fittest = population.getFittest();
        }

        long endTime = System.currentTimeMillis();

        // Print out some information to the console.
        System.out.println("Generation " + generationNumber + ": " + fittest);
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }

}