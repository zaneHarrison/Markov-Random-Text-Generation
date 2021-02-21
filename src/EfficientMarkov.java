import com.sun.source.tree.BreakTree;

import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		myMap.clear();
		for (int index = 0; index <= myText.length() - myOrder; index++) {
			String sub = myText.substring(index, index + myOrder);
			myMap.putIfAbsent(sub, new ArrayList<>());
			if (index + myOrder <= myText.length() - 1) {
				myMap.get(sub).add(myText.substring(index + myOrder, index + myOrder + 1));
			} else {
				myMap.get(sub).add(PSEUDO_EOS);
			}
		}
	}

	@Override
	public ArrayList<String> getFollows(String key) {
		if (!myMap.containsKey(key)) throw new NoSuchElementException(key+" not in map");
		return myMap.get(key);
	}
}	
