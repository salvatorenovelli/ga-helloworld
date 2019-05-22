
package com.fun;

import java.util.Random;

import static com.fun.GeneticHelloWorld.TARGET;


public class Chromosome implements Comparable<Chromosome> {
	private final String gene;
	private final int fitness;
	
	/** The target gene, converted to an array for convenience. */
	private static final char[] TARGET_GENE = TARGET.toCharArray();

	/** Convenience randomizer. */
	private static final Random rand = new Random(System.currentTimeMillis());
	
	/**
	 * Default constructor.
	 *
	 * @param gene The gene representing this <code>Chromosome</code>.
	 */
	public Chromosome(String gene) {
		this.gene    = gene;
		this.fitness = calculateFitness(gene);
	}
	
	/**
	 * Method to retrieve the gene for this <code>Chromosome</code>.
	 *
	 * @return The gene for this <code>Chromosome</code>.
	 */
	public String getGene() {
		return gene;
	}
	
	/**
	 * Method to retrieve the fitness of this <code>Chromosome</code>.  Note
	 * that a lower fitness indicates a better <code>Chromosome</code> for the
	 * solution.
	 *
	 * @return The fitness of this <code>Chromosome</code>.
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
	 * Method to generate a new <code>Chromosome</code> that is a random
	 * mutation of this <code>Chromosome</code>.  This method randomly
	 * selects one character in the <code>Chromosome</code>s gene, then
	 * replaces it with another random (but valid) character.  Note that
	 * this method returns a new <code>Chromosome</code>, it does not
	 * modify the existing <code>Chromosome</code>.
	 * 
	 * @return A mutated version of this <code>Chromosome</code>.
	 */
	public Chromosome mutate() {
		char[] arr  = gene.toCharArray();
		int idx     = rand.nextInt(arr.length);
		int delta   = (rand.nextInt() % 90) + 32;
		arr[idx]    = (char) ((arr[idx] + delta) % 122);

		return new Chromosome(String.valueOf(arr));
	}

	/**
	 * Method used to mate this <code>Chromosome</code> with another.  The
	 * resulting child <code>Chromosome</code>s are returned.
	 * 
	 * @param mate The <code>Chromosome</code> to mate with.
	 * 
	 * @return The resulting <code>Chromosome</code> children.
	 */
	public Chromosome[] mate(Chromosome mate) {
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

		return new Chromosome[] { new Chromosome(String.valueOf(child1)), 
				new Chromosome(String.valueOf(child2))}; 
	}
	static Chromosome generateRandom() {
		char[] arr = new char[TARGET_GENE.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (char) (rand.nextInt(90) + 32);
		}

		return new Chromosome(String.valueOf(arr));
	}

	public int compareTo(Chromosome c) {
		if (fitness < c.fitness) {
			return -1;
		} else if (fitness > c.fitness) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chromosome)) {
			return false;
		}
		
		Chromosome c = (Chromosome) o;
		return (gene.equals(c.gene) && fitness == c.fitness);
	}

	@Override
	public int hashCode() {		
		return new StringBuilder().append(gene).append(fitness)
				.toString().hashCode();
	}

	public boolean perfectFitness() {
		return getFitness() == 0;
	}
}