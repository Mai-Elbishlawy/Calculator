package calculator;

public class CalculatorLogic {

	public CalculatorLogic() {
		// TODO Auto-generated constructor stub
	}
	    public double calculate(double n1, double n2, String op) {

	        switch (op) {
	            case "+": return n1 + n2;
	            case "-": return n1 - n2;
	            case "×": return n1 * n2;
	            case "÷":
	                if (n2 == 0) return Double.NaN;
	                return n1 / n2;
	            case "%": return n1 % n2;
	            default: return Double.NaN;
	        }
	    }
	    public String formatNum(double d) {
		    // if the number is an integer (no decimal part),
		    // cast it to long to remove the trailing ".0"
		    if (d == (long) d) {
		        return "" + (long) d;  // return as long formatted to String
		    } else {
		        return "" + d;         // return as original double for decimal numbers
		    }
		}
	}


