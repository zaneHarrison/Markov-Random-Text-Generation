import com.sun.source.tree.BreakTree;

import java.util.*;

public class EfficientWordMarkov extends BaseWordMarkov {
    private Map<WordGram,ArrayList<String>> myMap;

    public EfficientWordMarkov(){
        this(3);
    }

    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

    @Override
    public void setTraining(String text) {
        super.setTraining(text);
        myMap.clear();
        for (int index = 0; index <= myWords.length - myOrder; index++) {
            WordGram mapKey = new WordGram(myWords, index, myOrder);
            myMap.putIfAbsent(mapKey, new ArrayList<>());
            if (index + myOrder <= myWords.length - 1) {
                myMap.get(mapKey).add(myWords[index + myOrder]);
            } else {
                myMap.get(mapKey).add(PSEUDO_EOS);
            }
        }
    }

    public ArrayList<String> getFollows(WordGram key) {
        if (!myMap.containsKey(key)) throw new NoSuchElementException(key+" not in map");
        return myMap.get(key);
    }
}
