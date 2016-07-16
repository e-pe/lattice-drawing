package unstable.hassediagram.latticedrawing.utils;

import java.util.*;
import java.util.Map.*;

/**
 * This class represents a relation element in a relation.
 *  
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class RelationElement {
	private String name;
	private Entry<String, Set<String>> entry;
	
	/**
	 * Constructor
	 * 
	 * @param name is the name of the relation element
	 * @param entry context of the relation element
	 */
	public RelationElement(String name, Entry<String, Set<String>> entry){
		this.name = name;
		this.entry = entry;
	}
	
	/**
	 * Get the name of the relation element.
	 * 
	 * @return The name of the relation element.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the context(all relations) which the current element is part of. 
	 *
	 * @return The context.
	 */
	public Entry<String, Set<String>> getEntry(){
		return this.entry;
	}
    
	/**
	 * Gets the key of the relation.
	 * 
	 * @return The key.
	 */
	public String getKey(){
		return this.entry.getKey();
	}
}
