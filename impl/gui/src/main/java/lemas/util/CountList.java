package lemas.util;

import java.util.ArrayList;

public class CountList extends ArrayList<Double> {

	private static final long serialVersionUID = 1L;
	public Double total = 0.0;
	public int count = 0;
	public int size = 0;

	public CountList() {
		size = 0;
	}
	
	public CountList(int i) {
		size = i;
	}

	@Override
	public boolean add(Double _value) {
		total += _value;
		if (size > 0) {
			if (count == size){
				total -= remove(0);
			}else{
				count++;
			}
			return super.add(_value);
		}else{
			count++;
		}
		return true;
	}

	public Double total() {
		return total;
	}

	public Double avg() {
		if (count==0){
			return 0.0;
		}else{
			return total() / count;
		}
	}

}
