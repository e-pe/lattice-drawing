package unstable.hassediagram.latticedrawing.utils;

import java.util.*;
import java.util.Map.*;

/**
 * This class represents a collection for maintaining all relations.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class RelationMap extends HashMap<String, Set<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adds a new vertex with no edges.
	 * 
	 * @param vertex to add
	 */
	private void add(String vertex) {
        if (!this.containsKey(vertex))
           this.put(vertex, new HashSet<String>());        
    }
	
	/**
	 * Adds a new edge between two vertices.
	 * 
	 * @param vertex1 is the first vertex.
	 * @param vertex2 is the second vertex.
	 */
	public void add(String vertex1, String vertex2){
		if(!this.containsKey(vertex1)) 
          this.add(vertex1);
		
        if(!this.contains(vertex1, vertex2))
        	this.get(vertex1).add(vertex2);
	}
	
	/**
	 * Returns true whether the map contains a dependency between such vertices.
	 * 
	 * @param vertex1 is the first vertex.
	 * @param vertex2 is the second vertex.
	 * @return True if a dependency between such vertices exists.
	 */
	public Boolean contains(String vertex1, String vertex2){
		if(this.containsKey(vertex1))
			return this.get(vertex1).contains(vertex2);
		
		if(this.containsKey(vertex2))
			return this.get(vertex2).contains(vertex1);
		
		return false;
	}
	
	/**
	 * Removes the specified dependency between two vertices.
	 * 
	 * @param vertex1 is the first vertex.
	 * @param vertex2 is the second vertex.
	 */
	public void remove(String vertex1, String vertex2){
		if(this.contains(vertex1, vertex2)){
			Set<String> vertices = this.get(vertex1);
			vertices.remove(vertex2);
			
			if(vertices.isEmpty())
				this.remove(vertex1);
		}
	}
	
	/**
	 * Gets the specified entry.
	 * 
	 * @param key of the entry to look for
	 * @return The specified enty.
	 */
	public Entry<String, Set<String>> getEntry(String key){
		for(Entry<String, Set<String>> e : this.entrySet()){
			if(e.getKey().equals(key)){
				return e;
			}
		}
		
		return null;
	}
}
