package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    private static Map<String, Character> transitions;

    public static final long NUM_OF_GENERATIONS = 50_000_000_000L;

    public static final int MIN_DIST = 5;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String initialState = "";
        
        transitions = new HashMap<>();

        /* Parse initial state string */
        if((line = br.readLine()) != null)
        {
            initialState = parseInitialState(line);
            if(initialState == null)
            {
                System.err.println("Input data error!");
                return;
            }
            
        }

        /* Parse transitions */
        while ((line = br.readLine()) != null) {
            if(!line.isEmpty())
            {
                parseTransition(line);
            }
        }
        
        int negativeOffset = 0;
        String output = initialState;
        String temp = "";
        int cnt = 0;
        for(int i = 0; i < NUM_OF_GENERATIONS; i++)
        {
            System.out.println("Generated (i=" + i + "): " + output);
            System.out.println("     Temp (i=" + i + "): " + temp);
            /* Add more pots on start or end */
            int startIndex = output.indexOf("#");
            if(startIndex < MIN_DIST)
            {
                negativeOffset += (MIN_DIST - startIndex);
                output = ".".repeat(MIN_DIST - startIndex) + output;
            }

            int endIndex = output.lastIndexOf("#");
            if(endIndex > (output.length() - MIN_DIST - 1))
            {
                output = output + ".".repeat(MIN_DIST - (output.length() - endIndex) + 1);
            }
            
            temp = new String(output);
            output = generate(output, transitions);
            if(output.contains(temp.substring(5, temp.length()-5)))
            {
                System.out.println("XXXX");
                cnt = i;
                break;
            }
            //System.out.println(output);
            System.out.println("-------------------------------");
        }
        System.out.println("Generated: " + output);
    
        long initialSum = 0;
        for(int i = 0; i < output.length(); i++)
        {
            if(output.charAt(i) == '#')
            {
                initialSum += (i - negativeOffset);
            }
        }

        /* Each '#' occurences will contribute +1 to total sum */
        long totalSum = initialSum + (NUM_OF_GENERATIONS - cnt - 1) * output.replace(".", "").length();

        System.out.println("Initial sum: " + initialSum);
        System.out.println("Total sum: " + totalSum);
    }

    private static String parseInitialState(String line) {
        final String regex = "initial state: ([#.]*)";
        Pattern pattern = Pattern.compile(regex);
    
        Matcher matcher = pattern.matcher(line);
    
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private static void parseTransition(String line) {
        final String regex = "([#.]{5}) => ([#.])";
        Pattern pattern = Pattern.compile(regex);
    
        Matcher matcher = pattern.matcher(line);
    
        if (matcher.find()) {
            transitions.put(matcher.group(1), matcher.group(2).charAt(0));
        }
    
    }

    private static String generate(String initialState, 
        Map<String, Character> transitions)
    {
        //char[] output = initialState.toCharArray();
        char[] output = ".".repeat(initialState.length()).toCharArray();

        for (Map.Entry<String, Character> t : transitions.entrySet()) {
            String key = t.getKey();
            Character value = t.getValue();
            for(int index = initialState.indexOf(key); 
                index != -1; 
                index = initialState.indexOf(key, index+1))
            {
                output[index+2] = value;
            }
        }

        return new String(output);
    }
}

