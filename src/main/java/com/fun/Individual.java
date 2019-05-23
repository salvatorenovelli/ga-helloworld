
package com.fun;

import java.util.Random;

import static com.fun.GeneticHelloWorld.PERFECT_FITNESS;


public class Individual implements Comparable<Individual> {
    private final String gene;
    private final int fitness;
    private static final char[] TARGET_GENE = PERFECT_FITNESS.toCharArray();
    private static final Random rand = new Random(0);

    public Individual(String gene) {
        this.gene = gene;
        this.fitness = calculateFitness(gene);
    }

    public int getFitness() {
        return fitness;
    }

    private static int calculateFitness(String gene) {
        int fitness = 0;
        char[] arr = gene.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            fitness += Math.abs(((int) arr[i]) - ((int) TARGET_GENE[i]));
        }

        return fitness;
    }

    public Individual mutate() {
        char[] arr = gene.toCharArray();
        int idx = rand.nextInt(arr.length);
        int delta = (rand.nextInt() % 90) + 32;
        arr[idx] = (char) ((arr[idx] + delta) % 122);

        return new Individual(String.valueOf(arr));
    }

    public Individual[] mate(Individual mate) {
        // Convert the genes to arrays to make thing easier.
        char[] arr1 = gene.toCharArray();
        char[] arr2 = mate.gene.toCharArray();

        // Select a random pivot point for the mating
        int pivot = rand.nextInt(arr1.length);

        // Provide a container for the child gene data
        char[] child1 = new char[gene.length()];
        char[] child2 = new char[gene.length()];

        // Copy the data from each gene to the first child.
        System.arraycopy(arr1, 0, child1, 0, pivot);
        System.arraycopy(arr2, pivot, child1, pivot, (child1.length - pivot));

        // Repeat for the second child, but in reverse order.
        System.arraycopy(arr2, 0, child2, 0, pivot);
        System.arraycopy(arr1, pivot, child2, pivot, (child2.length - pivot));

        return new Individual[]{new Individual(String.valueOf(child1)),
                new Individual(String.valueOf(child2))};
    }

    static Individual generateRandom() {
        char[] arr = new char[TARGET_GENE.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) (rand.nextInt(90) + 32);
        }

        return new Individual(String.valueOf(arr));
    }

    @Override
    public int compareTo(Individual c) {
        return fitness - c.fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Individual)) {
            return false;
        }

        Individual c = (Individual) o;
        return (gene.equals(c.gene) && fitness == c.fitness);
    }

    @Override
    public int hashCode() {
        return gene.hashCode();
    }

    public boolean isPerfectFitness() {
        return getFitness() == 0;
    }

    public String toString() {
        return gene;
    }
}