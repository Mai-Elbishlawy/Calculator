package calculator;
import java.util.Stack;
	
public class HistoryManager {

	public HistoryManager() {
		// TODO Auto-generated constructor stub
	}


	    private Stack<String> historyStack = new Stack<>();

	    public void add(String operation) {
	        historyStack.push(operation);
	    }

	    public void clear() {
	        historyStack.clear();
	    }

	    public String getAll() {
	        if (historyStack.isEmpty()) return "No history yet";

	        StringBuilder sb = new StringBuilder();

	        for (int i = historyStack.size() - 1; i >= 0; i--) {
	            sb.append(historyStack.get(i)).append("\n");
	        }

	        return sb.toString();
	    }
	}

