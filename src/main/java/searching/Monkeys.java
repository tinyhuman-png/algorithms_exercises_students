package searching;

import java.util.*;


/**
 * Problem 21 of AdventOfCode 2022
 *
 * The monkeys are back!
 *
 * Each monkey is given a job: either to yell a specific number or to yell the result of a math operation.
 * All of the number-yelling monkeys know their number from the start;
 * however, the math operation monkeys need to wait for two other monkeys to yell a number,
 * and those two other monkeys might also be waiting on other monkeys.
 *
 * Your job is to work out the number the monkey named 'root'
 * will yell before the monkeys figure it out themselves.
 *
 * For example:
 *
 * root: pppw + sjmn
 * dbpl: 5
 * cczh: sllz + lgvd
 * zczc: 2
 * ptdq: humn - dvpt
 * dvpt: 3
 * lfqf: 4
 * humn: 5
 * ljgn: 2
 * sjmn: drzm * dbpl
 * sllz: 4
 * pppw: cczh / lfqf
 * lgvd: ljgn * ptdq
 * drzm: hmdt - zczc
 * hmdt: 32
 *
 * Each line contains the name of a monkey, a colon, and then the job of that monkey:
 *
 * A lone number means the monkey's job is simply to yell that number.
 * A job like aaaa + bbbb means the monkey waits for monkeys aaaa and bbbb to yell each of their numbers;
 * the monkey then yells the sum of those two numbers.
 * aaaa - bbbb means the monkey yells aaaa's number minus bbbb's number.
 * Job aaaa * bbbb will yell aaaa's number multiplied by bbbb's number.
 * Job aaaa / bbbb will yell aaaa's number divided by bbbb's number.
 * These (+, -, /, *) are the only possible operators.
 * So, in the above example, monkey drzm has to wait for monkeys hmdt and zczc to yell their numbers.
 * Fortunately, both hmdt and zczc have jobs that involve simply yelling a single number,
 * so they do this immediately: 32 and 2.
 * Monkey drzm can then yell its number by finding 32 minus 2: 30.
 *
 * Then, monkey sjmn has one of its numbers (30, from monkey drzm),
 * and already has its other number, 5, from dbpl.
 * This allows it to yell its own number by finding 30 multiplied by 5: 150.
 *
 * This process continues until root yells a number: 152
 *
 * This input is inside input_advent21_debug.txt
 *
 * The parsing is already done for you.
 * What you receive is a list of Monkey (one for each line of the input) and there
 * are two types of monkey: YellingMonkey and OperationMonkey.
 *
 * Hint: use if (m instanceof YellingMonkey) to verify which instance it is
 *
 * Feel free to use existing java classes for your algorithm.
 */
public class Monkeys {


    /**
     * Compute the number for monkey named "root.
     * Your algorithm should run in O(n) where n is
     * the size of the input.
     */
    public static long evaluateRoot(List<Monkey> input) {
        HashMap<String, MonkeyNode> nameToNode = new HashMap<>();
        for (Monkey mon : input) {
            if (mon instanceof YellingMonkey) {
                YellingMonkey monkey = (YellingMonkey) mon;
                if (!nameToNode.containsKey(monkey.name)) {
                    nameToNode.put(monkey.name, new MonkeyNode(monkey.name, (long) monkey.number));
                } else {
                    MonkeyNode node = nameToNode.get(monkey.name);
                    node.result = (long) monkey.number;
                }
            } else { //OperationMonkey
                OperationMonkey monkey = (OperationMonkey) mon;
                MonkeyNode leftNode;
                if (!nameToNode.containsKey(monkey.leftMonkey)) {
                    leftNode = new MonkeyNode(monkey.leftMonkey);
                    nameToNode.put(monkey.leftMonkey, leftNode);
                } else {
                    leftNode = nameToNode.get(monkey.leftMonkey);
                }

                MonkeyNode rightNode;
                if (!nameToNode.containsKey(monkey.rightMonkey)) {
                    rightNode = new MonkeyNode(monkey.rightMonkey);
                    nameToNode.put(monkey.rightMonkey, rightNode);
                } else {
                    rightNode = nameToNode.get(monkey.rightMonkey);
                }

                if (!nameToNode.containsKey(monkey.name)) {
                    nameToNode.put(monkey.name, new MonkeyNode(monkey.name, leftNode, rightNode, monkey.op));
                } else {
                    MonkeyNode node = nameToNode.get(monkey.name);
                    node.leftNode = leftNode;
                    node.rightNode = rightNode;
                    node.op = monkey.op;
                }
            }
        }

        if (!nameToNode.containsKey("root")) return 0;
        MonkeyNode rootNode = nameToNode.get("root");

        recursiveEval(rootNode);
        return rootNode.result;
    }

    private static void recursiveEval(MonkeyNode node) {
        if (node.leftNode == null || node.rightNode == null) return;

        recursiveEval(node.leftNode);
        recursiveEval(node.rightNode);

        switch (node.op){
            case '+':
                node.result = node.leftNode.result + node.rightNode.result;
                break;
            case '-':
                node.result = node.leftNode.result - node.rightNode.result;
                break;
            case '/':
                node.result = node.leftNode.result / node.rightNode.result;
                break;
            case '*':
                node.result = node.leftNode.result * node.rightNode.result;
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }


    //good idea the node custom -> a approfondir
    static class MonkeyNode {
        String name;
        Long result;
        char op;
        MonkeyNode leftNode;
        MonkeyNode rightNode;

        public MonkeyNode(String monkeyName) {
            this.name = monkeyName;
        }

        public MonkeyNode(String monkeyName, Long monkeyResult) {
            this.name = monkeyName;
            this.result = monkeyResult;
        }

        public MonkeyNode(String monkeyName, MonkeyNode leftNode, MonkeyNode rightNode, char operation) {
            this.name = monkeyName;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.op = operation;
        }
    }


    static class Monkey {
        String name;
    }
    static class YellingMonkey extends Monkey {
        int number;
        public YellingMonkey(String name,int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name+": "+number;
        }
    }
    static class OperationMonkey extends Monkey {
        char op;
        String leftMonkey;
        Long leftLong;
        String rightMonkey;
        Long rightLong;

        public OperationMonkey(String name, String left, char op, String right) {
            this.name = name;
            this.leftMonkey = left;
            this.op = op;
            this.rightMonkey = right;
        }

        public Long doOperation() {
            if (leftLong == null || rightLong == null) return null;
            switch (op){
                case '+':
                    return leftLong + rightLong;
                case '-':
                    return leftLong - rightLong;
                case '/':
                    return leftLong / rightLong;
                default:
                    return leftLong * rightLong;
            }
        }

        @Override
        public String toString() {
            return name+": "+leftMonkey+" "+op+" "+rightMonkey;
        }
    }


}
