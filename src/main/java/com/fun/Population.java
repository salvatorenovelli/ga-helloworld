
package com.fun;

import java.util.Arrays;
import java.util.Random;

public class Population {

    private static final int TOURNAMENT_SIZE = 3;
    public static final int POPULATION_SIZE = 2048;

    private final Random rand = new Random(0);

    private double elitism;
    private double mutation;
    private double crossover;
    private Individual[] currentPopulation;

    public Population() {
        this(0.8, 0.1, 0.1);
    }

    public Population(double crossoverRatio, double elitismRatio, double mutationRatio) {

        this.crossover = crossoverRatio;
        this.elitism = elitismRatio;
        this.mutation = mutationRatio;

        // Generate an initial population
        this.currentPopulation = new Individual[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            this.currentPopulation[i] = Individual.generateRandom();
        }

        Arrays.sort(this.currentPopulation);
    }

    public void evolve() {
        // Create a buffer for the new generation
        Individual[] buffer = new Individual[currentPopulation.length];

        // Copy over a portion of the population unchanged, based on
        // the elitism ratio.
        int idx = (int) Math.round(currentPopulation.length * elitism);
        System.arraycopy(currentPopulation, 0, buffer, 0, idx);

        // Iterate over the remainder of the population and evolve as appropriate.
        while (idx < buffer.length) {
            // Check to see if we should perform a crossover.
            if (shouldCrossover()) {

                // Select the parents and mate to get their children
                Individual[] parents = selectParents();
                Individual[] children = parents[0].mate(parents[1]);

                // Check to see if the first child should be mutated.
                if (shouldMutate()) {
                    buffer[idx++] = children[0].mutate();
                } else {
                    buffer[idx++] = children[0];
                }

                // Repeat for the second child, if there is room.
                if (idx < buffer.length) {
                    if (shouldMutate()) {
                        buffer[idx] = children[1].mutate();
                    } else {
                        buffer[idx] = children[1];
                    }
                }
            } else {
                if (shouldMutate()) {
                    buffer[idx] = currentPopulation[idx].mutate();
                } else {
                    buffer[idx] = currentPopulation[idx];
                }
            }

            // Increase our counter
            ++idx;
        }

        // Sort the buffer based on fitness.
        Arrays.sort(buffer);

        // Reset the population
        currentPopulation = buffer;
    }

    private boolean shouldMutate() {
        return rand.nextFloat() <= mutation;
    }

    private boolean shouldCrossover() {
        return rand.nextFloat() <= crossover;
    }

    private Individual[] selectParents() {
        Individual[] parents = new Individual[2];

        // Randomly select two best parents out of 4 random individuals
        // polygamy is allowed also, you could mate with yourself here

        for (int i = 0; i < 2; i++) {
            parents[i] = getRandomIndividual();
            for (int j = 0; j < TOURNAMENT_SIZE; j++) {
                Individual randomIndividual = getRandomIndividual();
                if (randomIndividual.compareTo(parents[i]) < 0) {
                    parents[i] = randomIndividual;
                }
            }
        }

        return parents;
    }

    private Individual getRandomIndividual() {
        return currentPopulation[rand.nextInt(currentPopulation.length)];
    }

    public Individual getFittest() {
        return this.currentPopulation[0];
    }

    public boolean isPerfectFitness() {
        return getFittest().isPerfectFitness();
    }
}