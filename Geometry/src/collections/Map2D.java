package collections;

import java.util.ArrayList;

public class Map2D<E> {
	
	private ArrayList<ArrayList<E>> values;
	
	private int xSize;
	private int ySize;
	
	
	public Map2D(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		values = new ArrayList<ArrayList<E>>(xSize);
		for (int i = 0; i < xSize; i++) {
			ArrayList<E> row = new ArrayList<>();
			for (int j = 0; j < ySize; j++) {
				row.add(null);
			}
			values.add(row);
		}
	}
	
	public void set(int x, int y, E value) {
		values.get(x).set(y, value);
	}
	
	public E get(int x, int y) {
		return values.get(x).get(y);
	}
	
	public ArrayList<E> get(int x) {
		return values.get(x);
	}
	
	public int xSize() {
		return xSize;
	}

	public int ySize() {
		return ySize;
	}
}
