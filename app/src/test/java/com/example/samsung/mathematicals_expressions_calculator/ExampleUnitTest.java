package com.example.samsung.mathematicals_expressions_calculator;

import org.junit.Test;

import java.util.LinkedList;

import static com.example.samsung.mathematicals_expressions_calculator.RPN.*;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testIsOperator() throws Exception {

        char[] inputedValues = {'+', '-', '*', '/', ')', '(', '1', '2', '0'};
        boolean[] expectedValues = {true, true, true, true, false, false, false, false, false};

        for (int index = 0; index < inputedValues.length; index++) {
            assertEquals(expectedValues[index], isOperator(inputedValues[index]));
        }
    }

    @Test
    public void testPriority() throws Exception {

        char[] inputedValues = {'+', '-', '*', '/', ')', '('};
        int[] expectedValues = {1, 1, 2, 2, -1, -1};

        for (int index = 0; index < inputedValues.length; index++) {
            assertEquals(expectedValues[index], priority(inputedValues[index]));
        }
    }

    @Test
    public void testProcessOperator() throws Exception {

        Integer[] expectedValues = {579, 101, 408, 34, 333, 1679, -408, -34,
                -333, -1679, -408, -34, -579, -101, 408, 34};

        char[] inputedValuesOperarators = new char[] {'+', '-', '*', '/'};

        LinkedList<Integer> inputedValuesNumbers[] = new LinkedList[16];
        if (((inputedValuesNumbers[0] = new LinkedList<>()).add(456) & inputedValuesNumbers[0].add(123))
        & ((inputedValuesNumbers[1] = new LinkedList<>()).add(890) & inputedValuesNumbers[1].add(789))
        & ((inputedValuesNumbers[2] = new LinkedList<>()).add(34) & inputedValuesNumbers[2].add(12))
        & ((inputedValuesNumbers[3] = new LinkedList<>()).add(15504) & inputedValuesNumbers[3].add(456))

        & ((inputedValuesNumbers[4] = new LinkedList<>()).add(456) & inputedValuesNumbers[4].add(-123))
        & ((inputedValuesNumbers[5] = new LinkedList<>()).add(890) & inputedValuesNumbers[5].add(-789))
        & ((inputedValuesNumbers[6] = new LinkedList<>()).add(34) & inputedValuesNumbers[6].add(-12))
        & ((inputedValuesNumbers[7] = new LinkedList<>()).add(15504) & inputedValuesNumbers[7].add(-456))

        & ((inputedValuesNumbers[8] = new LinkedList<>()).add(-456) & inputedValuesNumbers[8].add(123))
        & ((inputedValuesNumbers[9] = new LinkedList<>()).add(-890) & inputedValuesNumbers[9].add(789))
        & ((inputedValuesNumbers[10] = new LinkedList<>()).add(-34) & inputedValuesNumbers[10].add(12))
        & ((inputedValuesNumbers[11] = new LinkedList<>()).add(-15504) & inputedValuesNumbers[11].add(456))

        & ((inputedValuesNumbers[12] = new LinkedList<>()).add(-456) & inputedValuesNumbers[12].add(-123))
        & ((inputedValuesNumbers[13] = new LinkedList<>()).add(-890) & inputedValuesNumbers[13].add(-789))
        & ((inputedValuesNumbers[14] = new LinkedList<>()).add(-34) & inputedValuesNumbers[14].add(-12))
        & ((inputedValuesNumbers[15] = new LinkedList<>()).add(-15504) & inputedValuesNumbers[15].add(-456))) {

            for (int indexNumbers = 0, indexOperators = 0; indexNumbers < inputedValuesNumbers.length; indexNumbers++) {
                indexOperators = indexNumbers - (indexNumbers/inputedValuesOperarators.length)*inputedValuesOperarators.length;
                processOperator(inputedValuesNumbers[indexNumbers],
                        inputedValuesOperarators[indexOperators]);
                assertEquals(expectedValues[indexNumbers],inputedValuesNumbers[indexNumbers].getLast());
            }

        } else {
            throw new Exception();
        }
    }

    @Test
    public void testGetSequenceDigits() {

        //In arrey index x,0 - string for input; index x,1 - sign; index x,2 - expected positon; index x,3 - axpected value;
        String[][] inputedStrings = {
                {"1234+567", "+", "3", "1234"},
                {"123-456", "-", "2", "-123"},
                {"123*456", "+", "2", "123"},
                {"234/567", "-", "2", "-234"},
                {"3456(789", "+", "3", "3456"},
                {"456)7890", "-", "2", "-456"}
        };

        Index position = new Index(0);

        for (String[] inputString :
             inputedStrings) {

            position.setIndex(0);
            assertEquals(inputString[3], getSequenceDigits(inputString[0], position, inputString[1].charAt(0)));
            assertEquals(Integer.parseInt(inputString[2]), position.getIndex());
        }

    }

    @Test
    public void testInsertSubString(){

        /**In arrey:    x,0 - string for input;
         *              x,1 - subString;
         *              x,2 - axpected value;
         *              x,3 - axpected position.
         */
        String[][] inputedStrings = {
                {"1234+567", "+", "+234+567", "1"}, //0 - replace true
                {"123-456", "-", "1-23-456", "2"},  //1 - replace false
                {"123*456", "", "12*456", "2"},     //2 - replace true
                {"234/567", "1*", "2341*/567", "5"}//3 - replace false
        };

        for (int index = 0; index < inputedStrings.length; index++) {
            Index position = new Index(index);
            assertEquals(inputedStrings[index][2],
                    insertSubString(inputedStrings[index][0], inputedStrings[index][1], position, index%2 == 0));
            assertEquals(Integer.parseInt(inputedStrings[index][3]), position.getIndex());
        }
    }

    @Test
    public void testIndex() throws Exception {

        /** in arrey:   x,0 - expected value on getter;
         *              x,1 - expected value on increment
         *              x,2 - expected value on decrement
         *              x,3 - expected value on setter;
         *              x,4 - inputed value on constructor;
         *              x,5 - inputed value on setter.
         */
        int[][] inputedValues = {
                {1, 2, 1, 3, 1, 3},
                {2, 3, 2, 5, 2, 5},
                {3, 4, 3, 10, 3, 10},
                {234, 235, 234, 56734, 234, 56734}
        };

        for (int[] inputedValue :
                inputedValues) {
            Index index = new Index(inputedValue[4]);
            assertEquals(inputedValue[0],index.getIndex());
            index.addOnIndex();
            assertEquals(inputedValue[1], index.getIndex());
            index.remOnIndex();
            assertEquals(inputedValue[2], index.getIndex());
            index.setIndex(inputedValue[5]);
            assertEquals(inputedValue[3], index.getIndex());
        }


    }

    @Test
    public void testEval() throws Exception {

        String[][] inputedValues = {
                {"1+2*5-25/5+(45+24)*-3", "-201"},
                {"-15/5+(-7)*88/(98-42)", "-14"},
                {"-15/5(-7)*88/(98-42)", "Before the opening bracket is missing operator " +
                        "or another left parenthesis in the positions 5"},
                {"-15/5+)(-7)*88/(98-42)", "The closing bracket previously placed in the opening position: 6"},
                {"-15/5+()*88/(98-42)", "Inside the brackets at the positions \"6\" and \"7\" there is no content!"},
                {"-15/5+(-7)*/88/(98-42)", "Go straight two operators with priority 2 ('*' or '/') in position 11"},
                {"*15/5+(-7)*/88/(98-42)", "Operator with priority 2 ('*' or '/') is in position 0"},
                {"-15/5+*88/(98-42)", "Are two operators in a row, the last of which has a priority of 2, " +
                        "and the penultimate or '1' ('+' or '-'), or '-1' ('(' or ')') 6"},
                {"-15/5*+-7)*/88/(98-42)", "For more than two consecutive operators starting from position 5"},
//                {"-15/5*(-7-)*88/(98-42)", "Before the closing bracket should any operator on position 9"},
                {"-15/5*(7--)*88/(98-42)", "The operator before the closing parenthesis at position 9"},
//                {"1+2*5-5/22+(45+24)*-", "The string must not end with operator!"},
                {"1+2*5-5/22+(45+24)*(-2", "Lack of opening parentheses in number: 1"}
//                {"1+2*5-5/22+(45+24))*(-2)", "Lacking closing parentheses in number: 1"}
        };

        for (String[] inputValue :
                inputedValues) {
            String actualValue = "";
            try {
                actualValue += eval(inputValue[0]);
            } catch (ExpressionCharException e) {
                actualValue += e.getMessage();
            }
            assertEquals(inputValue[1],actualValue);
        }

    }
}