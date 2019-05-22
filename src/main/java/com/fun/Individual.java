
package com.fun;

import java.util.Random;

import static com.fun.GeneticHelloWorld.TARGET;


public class Individual implements Comparable<Individual> {
	private final String gene;
	private final int fitness;
	
	/** The target gene, converted to an array for convenience. */
	private static final char[] TARGET_GENE = TARGET.toCharArray();

	/** Convenience randomizer. */
	private static final Random rand = new Random(System.currentTimeMillis());
	
	/**
	 * Default constructor.
	 *
	 * @param gene The gene representing this <code>Individual</code>.
	 */
	public Individual(String gene) {
		this.gene    = gene;
		this.fitness = calculateFitness(gene);
	}

	
	/**
	 * Method to retrieve the fitness of this <code>Individual</code>.  Note
	 * that a lower fitness indicates a better <code>Individual</code> for the
	 * solution.
	 *
	 * @return The fitness of this <code>Individual</code>.
	 */
	public int getFitness() {
		return fitness;
	}
	
	/**
	 * Helper method used to calculate the fitness for a given gene.  The
	 * fitness is defined as being the sum of the absolute value of the 
	 * difference between the current gene and the target gene.
	 * 
	 * @param gene The gene to calculate the fitness for.
	 * 
	 * @return The calculated fitness of the given gene.
	 */
	private static int calculateFitness(String gene) {
		int fitness = 0;
		char[] arr  = gene.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			fitness += Math.abs(((int) arr[i]) - ((int) TARGET_GENE[i]));
		}
		
		return fitness;
	}

	/**
	 * Method to generate a new <code>Individual</code> that is a random
	 * mutation of this <code>Individual</code>.  This method randomly
	 * selects one character in the <code>Individual</code>s gene, then
	 * replaces it with another random (but valid) character.  Note that
	 * this method returns a new <code>Individual</code>, it does not
	 * modify the existing <code>Individual</code>.
	 * 
	 * @return A mutated version of this <code>Individual</code>.
	 */
	public Individual mutate() {
		char[] arr  = gene.toCharArray();
		int idx     = rand.nextInt(arr.length);
		int delta   = (rand.nextInt() % 90) + 32;
		arr[idx]    = (char) ((arr[idx] + delta) % 122);

		return new Individual(String.valueOf(arr));
	}

	/**
	 * Method used to mate this <code>Individual</code> with another.  The
	 * resulting child <code>Individual</code>s are returned.
	 * 
	 * @param mate The <code>Individual</code> to mate with.
	 * 
	 * @return The resulting <code>Individual</code> children.
	 */
	public Individual[] mate(Individual mate) {
		// Convert the genes to arrays to make thing easier.
		char[] arr1  = gene.toCharArray();
		char[] arr2  = mate.gene.toCharArray();
		
		// Select a random pivot point for the mating
		int pivot    = rand.nextInt(arr1.length);
		
		// Provide a container for the child gene data
		char[] child1 = new char[gene.length()];
		char[] child2 = new char[gene.length()];
		
		// Copy the data from each gene to the first child.
		System.arraycopy(arr1, 0, child1, 0, pivot);
		System.arraycopy(arr2, pivot, child1, pivot, (child1.length - pivot));
		
		// Repeat for the second child, but in reverse order.
		System.arraycopy(arr2, 0, child2, 0, pivot);
		System.arraycopy(arr1, pivot, child2, pivot, (child2.length - pivot));

		return new Individual[] { new Individual(String.valueOf(child1)),
				new Individual(String.valueOf(child2))};
	}
	static Individual generateRandom() {
		char[] arr = new char[TARGET_GENE.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (char) (rand.nextInt(90) + 32);
		}

		return new Individual(String.valueOf(arr));
	}

	public int compareTo(Individual c) {
		if (fitness < c.fitness) {
			return -1;
		} else if (fitness > c.fitness) {
			return 1;
		}
		
		return 0;
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
		return new StringBuilder().append(gene).append(fitness)
				.toString().hashCode();
	}

	public boolean isPerfectFitness() {
		return getFitness() == 0;
	}

	public String toString(){
		return gene;
	}
}