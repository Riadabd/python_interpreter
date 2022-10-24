import java.util.*;
import java.util.stream.*;

public class TFunctions {
	static HashMap<String,ArrayList<Object>> lists = new HashMap<String,ArrayList<Object>>();
	
	public void initList(String ListID) {
		lists.put(ListID,new ArrayList<Object>());
	}
	
	public void printList(String ListID) {
		System.out.println(ListID + " is " + lists.get(ListID));
	}
	
	public void addToList(String listID,Object value){
		ArrayList<Object> l = lists.get(listID);
		l.add(value);
	}
	
	public void update(String listID,int index,Object value){
		ArrayList<Object> l = lists.get(listID);
		l.set(index,value);
	}

	public String logicalResult(String listName, int leftArg, String operation, int rightArg) {
		ArrayList<Object> o = this.lists.get(listName);
		int listSize = o.size();
		String elements = new String();
		boolean result = false;

		switch(operation) {
			case ">":
				result = (leftArg > rightArg);
				break;
			case ">=":
				result = (leftArg >= rightArg);
				break;
			case "<":
				result = (leftArg < rightArg);
				break;
			case "<=":
				result = (leftArg <= rightArg);
				break;
			case "==":
				result = (leftArg == rightArg);
				break;
			case "!=":
				result = (leftArg != rightArg);
				break;
			default:
		}

		if (result) {
			elements = IntStream.range(0, listSize)
								.mapToObj(String::valueOf)
								.collect(Collectors.joining(","));
		}

		return elements;
	}

	public String logicalLeftIDResult(String listName, int arg, String operation) {
		ArrayList<Object> o = this.lists.get(listName);
		int listSize = o.size();
		
		switch(operation) {
			case ">":
				String gt = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) > arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return gt;
			case ">=":
				String gte = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) >= arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return gte;
			case "<":
				String lt = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) < arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return lt;
			case "<=":
				String lte = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) <= arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return lte;
			case "==":
				String eq = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) == arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return eq;
			case "!=":
				String neq = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) != arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return neq;
			default:
				return new String();
		}
	}

	public String logicalRightIDResult(String listName, int arg, String operation) {
		ArrayList<Object> o = this.lists.get(listName);
		int listSize = o.size();
		
		switch(operation) {
			case ">":
				String gt = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) < arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return gt;
			case ">=":
				String gte = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) <= arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return gte;
			case "<":
				String lt = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) > arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return lt;
			case "<=":
				String lte = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) >= arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return lte;
			case "==":
				String eq = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) == arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return eq;
			case "!=":
				String neq = IntStream.range(0, listSize)
									 .filter(i -> (int)(o.get(i)) != arg)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return neq;
			default:
				return new String();
		}
	}

	public String logicalIDResult(String listName, String operation) {
		ArrayList<Object> o = this.lists.get(listName);
		int listSize = o.size();

		switch(operation) {
			case ">=":
			case "<=":
			case "==":
				String lt = IntStream.range(0, listSize)
									 .mapToObj(String::valueOf)
									 .collect(Collectors.joining(",")); 
				return lt;
			default:
				return new String();
		}
	}

	public String logicNot(String listName, String o, String notOp) {
		if (notOp == null) {
			return o;
		}

		if (o == null) {
			return IntStream.range(0, this.lists.get(listName).size())
							.mapToObj(String::valueOf)
							.collect(Collectors.joining(","));
		}

		List<Object> obj = this.lists.get(listName);
		int listSize = obj.size();
		return IntStream.range(0, listSize)
					    .filter(i -> !o.contains(String.valueOf(i)))
						.mapToObj(String::valueOf)
						.collect(Collectors.joining(","));
	}

	public String logicOr(String first, String second) {
		Stream<Character> firstStream = first.chars()
										 .mapToObj(i -> (char)i)
										 .filter(ch -> ch != ',');
        Stream<Character> secondStream = second.chars()
										  .mapToObj(i -> (char)i)
										  .filter(ch -> ch != ',');
        Stream<Character> concat = Stream.concat(firstStream, secondStream)
										 .distinct();

		return concat.map(ch->ch.toString())
					 .collect(Collectors.joining(","));
	}

	public String logicAnd(String first, String second) {
		Stream<Character> chStream = first.chars()
									  	  .mapToObj(i -> (char)i)
									      .filter(ch -> second.contains(Character.toString(ch)))
										  .filter(ch -> ch != ',');

		return chStream.map(ch->ch.toString())
					   .collect(Collectors.joining(","));
	}

	public void populateMap(String destlistName, String origListName, String result) {
		if (result == null | result.equals("")) {
			this.lists.put(destlistName, new ArrayList<Object>());
			return;
		}
		
		List<Integer> ints = Arrays.stream(result.split(","))
        						   .map(Integer::parseInt)
								   .sorted()
        						   .collect(Collectors.toList());
		
		ArrayList<Object> origin = this.lists.get(origListName);
		ArrayList<Object> o = new ArrayList<>();

		ints.forEach(i -> o.add(origin.get(i)));

		this.lists.put(destlistName, o);
	}
}