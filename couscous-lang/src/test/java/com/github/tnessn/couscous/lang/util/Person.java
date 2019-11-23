/**
 * 
 */
package com.github.tnessn.couscous.lang.util;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 *
 * @author huangjinfeng
 * @Description TODO
 */
public class Person {
	
	/** The name. */
	private String name;
	
	/** The age. */
	private int age;
	
	/** The rr. */
	private String rr;
	
	

	/**
	 * Gets the rr.
	 *
	 * @return the rr
	 */
	public String getRr() {
		return rr;
	}

	/**
	 * Sets the rr.
	 *
	 * @param rr the new rr
	 */
	public void setRr(String rr) {
		this.rr = rr;
	}


	/**
	 * Instantiates a new person.
	 *
	 * @param name the name
	 * @param age the age
	 * @param rr the rr
	 */
	public Person(String name, int age, String rr) {
		super();
		this.name = name;
		this.age = age;
		this.rr = rr;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
