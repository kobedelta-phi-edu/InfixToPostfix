package midlab1;

import midlab1.datastructure.LinkedStack;
import midlab1.datastructure.Stack;

import java.util.Scanner;

public class Tester {
    final private Scanner scanner = new Scanner(System.in);
    private String symbol="";

    /** shows user options and calls the methods necessary for program execution */
    void run() {
        while (true) {
            System.out.println(
                    "\nWhat would you like to do?" +
                    "\n1 - Convert an Infix to Postfix expression" +
                    "\n2 - Evaluate a Postfix Expression" +
                    "\n3 - Exit");
            byte choice = inputByte();
            switch (choice) {
                case 1 -> convertInfixToPostfix(input());
                case 2 -> evaluatePostfix(input());
                case 3 -> exit();
                default -> { System.err.println("Invalid input. Try again."); run(); }
            }
        }
    }

    /**
     * prompts the user to input a choice
     * @return user choice in byte
     */
    byte inputByte() {
        while (true) {
            try {
                System.out.print("Choice: ");
                return Byte.parseByte(scanner.next());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Input a number only. Please try again.");
            }
        }
    }

    /**
     * prompts the user to input an expression
     * @return expression to be used for conversion or evaluation
     */
    String input() {
        System.out.print("\nInput an Expression: ");
        return new Scanner(System.in).nextLine();
    }

    /**
     * converts an infix expression to a postfix one
     * @param infixExpression expression to be converted from infix to postfix
     */
    private void convertInfixToPostfix(String infixExpression) {
        int countOpen = 0;
        int countClose = 0;
        for (int i = 0; i < infixExpression.length(); i++){
            if (Character.isLetter(infixExpression.charAt(i)) || Character.isWhitespace(infixExpression.charAt(i)))
                throw new NumberFormatException();
            if (infixExpression.charAt(i) == '(')
                countOpen++;
            if (infixExpression.charAt(i) == ')')
                countClose++;
        }
        if(countOpen != countClose)
            throw new NumberFormatException();
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new LinkedStack<>();

        System.out.println("\nInfix Expression: " + infixExpression);
        System.out.println("=====================================================================================");
        System.out.printf("%-9s%40s%35s\n", "Symbol", "Postfix", "Stack");
        System.out.println("=====================================================================================");

        String[] symbolsArray = infixExpression.split("(?<=[-+/*^()])|(?=[-+/*^()])");

        for (String s : symbolsArray) {
            symbol = s;
            if (!isOperator(symbol) && !symbol.equals(")") && !symbol.equals("(")) {
                postfix.append(symbol).append(" ");
            } else if (isOperator(symbol)) {
                while (!stack.isEmpty() && isHigherPrecedence(symbol, stack.peek())) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(symbol);

            } else if (symbol.equals("(")) {
                stack.push(symbol);

            } else if (symbol.equals(")")) {
                while (!stack.peek().equals("("))
                    postfix.append(stack.pop()).append(' ');
                stack.pop();

            } else {
                postfix.append(symbol).append(' ');
            }
            System.out.printf("%-9s%-40s%35s\n", symbol, postfix.toString(), stack.toString());
        }
        while (!stack.isEmpty()){
            postfix.append(stack.pop()).append(" ");
            System.out.printf("%-9s%-40s%35s\n", symbol, postfix.toString(), stack.toString());
        }
        System.out.println("=====================================================================================");
        System.out.println("Converted Postfix Expression: " + postfix.toString());
        System.out.println("=====================================================================================");
    }

    /**
     * evaluates a postfix expression
     * @param postfixExp expression to be evaluated
     */
    private void evaluatePostfix(String postfixExp) {
        for(int i = 0; i < postfixExp.length(); i++){
            if(Character.isLetter(postfixExp.charAt(i)))
                throw new NumberFormatException("");
            if(!Character.isDigit(postfixExp.charAt(0)) || !Character.isDigit(postfixExp.charAt(1)))
                throw new NumberFormatException();
        }
        LinkedStack<Double> operandStack = new LinkedStack<>();
        String[] expression = postfixExp.split(" ");
        Double operand1 = 0d, operand2 = 0d, value = 0d;

        System.out.println("\nPostfix Expression: " + postfixExp);
        System.out.println("=====================================================================================");
        System.out.printf("%-10s%10s%15s%15s%35s\n", "Symbol", "Operand 1", "Operand 2", "Value", "Operand Stack");
        System.out.println("=====================================================================================");

        for (String s : expression) {
            symbol = s;
            if (!isOperator(symbol)) {
                operandStack.push(Double.parseDouble(symbol));
            } else {
                operand1 = operandStack.pop();
                operand2 = operandStack.pop();
                value = postEval(operand2, operand1, symbol);
                operandStack.push(value);
            }
            System.out.printf("%-10s%10s%15s%15s%35s\n", symbol, operand1, operand2, value, operandStack.toString());
        }
        System.out.println("=====================================================================================");
        System.out.println("Result: " + value);
        System.out.println("=====================================================================================");
        operandStack.pop();
    }

    /**
     * evaluates an expression
     * @param a second operand
     * @param b first operand
     * @param o operator
     * @return result (value) of the expression
     */
    double postEval(double a, double b, String o) {
        switch (o) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (a != 0) return a / b;
                else
                    throw new UnsupportedOperationException("Undefined!");
            case "^": return Math.pow(a,b); }
        return 0.0;
    }

    /**
     * check the precedence of an operator
     * @param c operator
     * @return precedence of an operator
     */
     int operatorPrecedence(String c){
        return switch (c) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1; };
    }

    /**
     * checks if an operator has a higher precedence than the other operator
     * @param optr1 first operator
     * @param optr2 second operator
     * @return true if the second operator has a higher or equal precedence compared to the first operator
     */
    boolean isHigherPrecedence(String optr1, String optr2) {
        return operatorPrecedence(optr2) >= operatorPrecedence(optr1);
    }

    /**
     * checks if the symbol is an operator or not
     * @param o symbol
     * @return true if the parameter is an operator
     */
    private boolean isOperator(String o){
        return o.equals("+") || o.equals("-") || o.equals("*") || o.equals("/") || o.equals("^");
    }

    /**
     * exits the program
     */
    void exit() {
        System.out.println("\nGoodbye!");
        System.exit(0);
    }

    /**
     * executes the program as HMTester
     */
    public static void checkTester(){
        System.out.println("************" + "\n* Welcome! *" + "\n************");
        try {
            Tester tester = new Tester();
            tester.run();
        } catch (Exception e) {
            System.err.println("Sorry, the expression cannot be converted/evaluated. Please input a valid expression. \n" +
            "NOTE: In infix to postfix conversion, the expression must not have spaces nor characters. \nIn evaluating" +
            " postfix, the expression must not have characters and first two tokens\nmust be numbers.");
            System.out.println();
            checkTester();
        }
    }
    
    /**
     * executes HMTester
     * @param args args
     */
    public static void main(String[] args) {
        checkTester();
    }
}
