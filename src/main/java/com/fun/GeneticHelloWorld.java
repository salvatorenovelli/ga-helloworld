
package com.fun;


public class GeneticHelloWorld {


    public static final String TARGET = "Hello World";

    public static void main(String[] args) {

        final double crossoverRatio = 0.8;
        final double elitismRatio = 0.1;
        final double mutationRatio = 0.03;

        long startTime = System.currentTimeMillis();

        // Create the initial population
        Population pop = new Population(crossoverRatio, elitismRatio, mutationRatio);
        Chromosome best = pop.getBest();

        int generations = 0;
        while (best.getFitness() != 0) {
            System.out.println("Generation " + generations++ + ": " + best.getGene());
            pop.evolve();
            best = pop.getBest();
        }

        // Get the end time for the simulation.
        long endTime = System.currentTimeMillis();

        // Print out some information to the console.
        System.out.println("Generation " + generations + ": " + best.getGene());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }
}