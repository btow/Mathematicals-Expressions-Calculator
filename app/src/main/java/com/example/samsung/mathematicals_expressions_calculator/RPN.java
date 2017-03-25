package com.example.samsung.mathematicals_expressions_calculator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.zip.Inflater;

/**
 * Created by btow on 22.03.2017.
 * The class contains static methods for the implementation of the algorithm
 * for expression evaluation using reverse Polish notation.
 */

class RPN {

    /**
     * The method checks if a character is an operator.
     * @param c - the type is <@code>char</@code> of character is checked
     * @return - <@code>true</@code> if character is operator, and <@code>false</@code> is not.
     */
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * The method determines the priority of the operation.
     * @param operator - the type is <@code>char</@code> of operator of operation;
     * @return - <@code>1</@code> if operation is the sum or difference;
     *           <@code>2</@code> if operation is the multiplication or division;
     *           <@code>-1</@code> if else.
     */
    static int priority(char operator) {

        switch (operator) {
            // при + или - возврат 1, при * / 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    /**
     * The method performs the operation for the operator between the operands.
     * @param numbers - the type is <@code>LinkedList<Integer></@code> of operands list;
     * @param operator - the type is <@code>char</@code> of operator for operation.
     */
    static void processOperator(LinkedList<Integer> numbers, char operator) {

        int r = numbers.removeLast(); // извлекаем из списка последний элемент
        int l = numbers.removeLast(); // и предпоследний элемент

        switch (operator) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат помещаем в numbers
            case '+':
                numbers.add(l + r);
                break;
            case '-':
                numbers.add(l - r);
                break;
            case '*':
                numbers.add(l * r);
                break;
            case '/':
                numbers.add(l / r);
                break;
        }
    }

    /**
     * The method converts the provided string to the list of operators and operands,
     * and computes the value of the expression algorithm reverse Polish notation.
     * @param string - the type is <@code>String</@code> of computes expression;
     * @return - the type is <@code>String</@code>.
     */
    public static String eval(String string) throws ExpressionCharException {

        LinkedList<Integer> numbers = new LinkedList<Integer>(); // сюда помещаем цифры
        LinkedList<Character> operators = new LinkedList<Character>(); // сюда - опрераторы и numbers и operators в порядке поступления
        int countBracket = 0;
        char lastChar = '\u0000', prevChar;
        Index position = new Index(0);

        for (int index = 0; index < string.length(); index++) { // парсим строку с выражением и вычисляем

            position.setIndex(index);
            prevChar = lastChar;
            lastChar = string.charAt(index);

            if (lastChar == '(') {
                String message = "";

                //Если перед открывающей скобкой нет оператора или другой левой скобки
                if (prevChar != '\u0000' && !isOperator(prevChar) && prevChar != '(') {
                    message = "Before the opening bracket is missing operator " +
                            "or another left parenthesis in the positions " + index;
                    throw new ExpressionCharException(message);
                }

                operators.add('(');
                countBracket++;
            }
            else if (lastChar == ')') {
                String message = "";

                if (countBracket < 1) { // Если закрывающая скобка поставлена ранее открывающей в позиции index
                    message = "The closing bracket previously placed in the opening position: " + index;
                    throw new ExpressionCharException(message);
                } else if (prevChar == '(') { //Если между скобками в позициях index - 1 и index ничего нет
                    message = "Inside the brackets at the positions \""
                            + (index - 1) + "\" and \"" + index + "\" there is no content!";
                    throw new ExpressionCharException(message);
                } else if (isOperator(prevChar)) { //Если перед закрывающей скобкой стоит любой оператор в позиции index - 1
                    message = "Before the closing bracket should any operator on position " + (index - 1);
                }

                while (operators.getLast() != '(')
                    processOperator(numbers, operators.removeLast());
                operators.removeLast();
                countBracket--;

            } else if (isOperator(lastChar)) {

                //Если предыдущий символ '\u0000', скобки или тоже является оператором, то возможны различные варианты
                if (!Character.isDigit(prevChar)) {
                    String message = "";

                    //Если приоритет текущего оператора - 2, а предыдущий не ')', тогда
                    if (priority(lastChar) == 2 && prevChar != ')' ) {

                        //Если идут подряд два оператора с приоритетом 2 ('*' или '/')
                        if (priority(prevChar) == 2) {
                            message = ("Go straight two operators with priority 2 ('*' or '/') in position " + index);
                        //Если оператор с приоритетом 2 ('*' или '/') находится в позиции 0
                        } else if (prevChar == '\u0000') {
                            message = "Operator with priority 2 ('*' or '/') is in position 0";
                        } else {
                        /**Если идут подряд два оператора, из которых последний имеет приоритет 2,
                         * а предпоследний или '1' ('+' либо '-'), или '-1' ('(' либо ')').
                         */
                            message = ("Are two operators in a row, the last of which has a priority of 2, " +
                                    "and the penultimate or '1' ('+' or '-'), or '-1' ('(' or ')') " + index);
                        }
                        throw new ExpressionCharException(message);

                    //Если приоритет текущего оператора - 1, тогда
                    } else if (priority(lastChar) == 1) {

                        /**Если это не последний символ строки, то нужно проверить, не является ли
                         * следующий тоже оператором или скобкой
                         */
                        if (index < (string.length() - 1)) {
                            char nextChar = string.charAt(index + 1);

                            //Если подряд идут более двух операторов с позиции index - 1
                            if (isOperator(nextChar)) {

                                message =("For more than two consecutive operators starting from position " + (index - 1));
                                throw new ExpressionCharException(message);

                            //Если после оператора закрывающая скобка, то это исключение
                            } else if (nextChar == ')') {

                                message = ("The operator before the closing parenthesis at position " + index);
                                throw new ExpressionCharException(message);

                            /**Если после оператора открывающая скобка, то перед ней нужно включить
                             * в выражение подстроку "1*"                             *
                              */
                            } else if (nextChar == '(') {

                                position.setIndex(++index);
                                string = insertSubString(string, "1*", position, false);
                                position.remOnIndex();
                                index = position.getIndex();

                            //А иначе оператор является знаком следующей цифры, которую помещаем в список
                            } else {
                                position.setIndex(index + 1);
                                numbers.add(Integer.parseInt(getSequenceDigits(string, position, lastChar)));
                            }
                        }

                    }
                }

                while (!operators.isEmpty() && priority(operators.getLast()) >= priority(lastChar)) {
                    processOperator(numbers, operators.removeLast());
                }

                if (index != position.getIndex()) {
                    index = position.getIndex();
                    lastChar = string.charAt(index);
                    continue;
                }
                operators.add(lastChar);

            } else {

                position.setIndex(index);
                numbers.add(Integer.parseInt(getSequenceDigits(string, position, '+')));
                index = position.getIndex();
            }
        }

        if (countBracket != 0) { //Если количество скобок не равно
            String message = countBracket > 0 ?
                    "Lack of opening parentheses in number: " + countBracket :
                    "Lacking closing parentheses in number: " + (countBracket * -1);
            throw new ExpressionCharException(message);
        } else if (isOperator(string.charAt(string.length() - 1))) { //Если последний символ является оператором
            String message = "The string must not end with operator!";
            throw new ExpressionCharException(message);
        }

        while (!operators.isEmpty()) {// обход списка операторов
            processOperator(numbers, operators.removeLast()); // вычисление значения операции
        }
        return numbers.get(0).toString();  // возврат результата

    }

    /**
     * The method returns a new string that is composed of a prefix and suffix obtained
     * by splitting the original string at the specified location and inserted between the specified substring.
     * @param string    - the type <@code>String</@code> of original string;
     * @param subString - the tyoe <@code>String</@code> of substring;
     * @param index     - the type <@code>int</@code> of position for insertion;
     * @param replace   - the type <@code>boolean</@code> to enable (true) or disable (false) character replacement
     * @return - <@code>String</@code>.
     */
    public static String insertSubString(String string, String subString, Index index, boolean replace) {

        String newString = "";
        char[] allChars = null, prefChars = null, sufChars = null;
        int oldIndex = index.getIndex(), subStringLength = subString.length();

        if (string.length() > 0) {
            allChars = string.toCharArray();
            prefChars = Arrays.copyOfRange(allChars, 0, oldIndex);
            newString += prefChars != null ? String.copyValueOf(prefChars) : "";

            if (replace) {
                sufChars = oldIndex + 1 < allChars.length ?
                        Arrays.copyOfRange(allChars, oldIndex + 1, allChars.length) : null;
                index.setIndex(oldIndex + subStringLength);
            } else {
                sufChars = Arrays.copyOfRange(allChars,oldIndex,allChars.length);
                index.setIndex(oldIndex + subStringLength);
            }

        } else {
            index.setIndex(subStringLength);
        }

        newString += subString;
        newString += sufChars != null ? String.copyValueOf(sufChars) : "";
        return newString;
    }

    /**
     * The method adds to the list of purely taking into account the sign.
     * @param string    - the type is <@code>String</@code> of expression;
     * @param index     - the type is <@code>int</@code> of positions index on expression;
     * @param sign      - the type is <@code>char</@code> of operand;
     */
    public static String getSequenceDigits(String string, Index index, char sign) {

        String operand = "";

        if (sign == '-') operand += sign;

        while (index.getIndex() < string.length() && Character.isDigit(string.charAt(index.getIndex()))) {
            operand += string.charAt(index.getIndex());
            index.addOnIndex();
        }
        index.remOnIndex();
        return operand;
    }

    /**
     * Inner class to pass values index by the link.
     */
    public static class Index {

        private int index;

        public Index (int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void addOnIndex () {
            ++index;
        }

        public void remOnIndex () {
            --index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
