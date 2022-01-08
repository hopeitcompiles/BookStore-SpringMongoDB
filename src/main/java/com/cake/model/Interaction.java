package com.cake.model;


public enum Interaction {
	SALE(5),
	VISIT(1);
	
	private final int weight;

	public int getWeight() {
		return weight;
	}
	
	Interaction(final int weight) {
		this.weight = weight;
    }
	
}
