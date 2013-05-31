package br.fir.sd.dimag.messenger.datastructure;

import java.util.Collection;

public class ArrayListWithMaxSize<T> extends java.util.ArrayList<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int maxSize = 0;
	
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public ArrayListWithMaxSize(int maxSize) {
		super();
		this.maxSize = maxSize;
	}
	
	public ArrayListWithMaxSize() {
		super();
		this.maxSize = 0;
	}

	public boolean add(T e) {
		if (maxSize != 0){
			if (this.size() < maxSize){
				return super.add(e);
			}
		}
		return false;
	}
	
	public void add(int index, T element) {
		if (maxSize != 0){
			if (this.size() < maxSize){
				super.add(index, element);
			}
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (maxSize != 0){
			if (this.size() + c.size() <maxSize){
				return super.addAll(c);
			}
		}
		return false;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		if (maxSize != 0){
			if (this.size() + c.size() <maxSize){
				return super.addAll(index, c);
			}
		}
		return false;
	}
}
