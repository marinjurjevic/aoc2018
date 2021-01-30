package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4b {
  // -2 => guard falls a sleep
  // -1 => guard wakes up
  // 0 <= guard id -> used for counters map
  private static Map<LocalDateTime, Integer> eventTimes;
  private static Map<Integer, int[]> sleepCounter;

  public static void main(String[] args) throws IOException {
    eventTimes = new TreeMap<>();
    sleepCounter = new HashMap<>();

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String s;

    while ((s = br.readLine()) != null) {
      parseEvent(s);
    }

    int currentGuardIndex = 0;
    int currentSleepStart = 0;

    for (Map.Entry<LocalDateTime, Integer> pair : eventTimes.entrySet()) {
      int event = pair.getValue();

      if (event == -2) {
        currentSleepStart = pair.getKey().getMinute();
      } else if (event == -1) {
        int[] array = sleepCounter.get(currentGuardIndex);
        if (array == null) {
          System.out.println("Creating new array for guard" + currentGuardIndex);
          array = new int[60];
          sleepCounter.put(currentGuardIndex, array);
        }

        for (int i = currentSleepStart; i < pair.getKey().getMinute(); i++) {
          array[i] += 1;
        }

        // printMinuteArray(array);

      } else {
        currentGuardIndex = event;
      }
    }

    int maxSleepGuardID = Collections.max(sleepCounter.entrySet(), new Comparator<Entry<Integer, int[]>>() {
      @Override
      public int compare(Entry<Integer, int[]> o1, Entry<Integer, int[]> o2) {
        return Integer.compare(Arrays.stream(o1.getValue()).max().getAsInt(),
            Arrays.stream(o2.getValue()).max().getAsInt());
      }
    }).getKey();

    int[] array = sleepCounter.get(maxSleepGuardID);
    int longestTimeAsleep = Arrays.stream(array).max().getAsInt();
    int minute = 0;
    for (int i = 0; i < 60; i++) {
      if (longestTimeAsleep == array[i]) {
        minute = i;
        break;
      }
    }
    System.out
        .println("Max time slept on single minute: Guard + " + maxSleepGuardID + " slept most on minute: " + minute);
  }

  private static LocalDateTime parseEvent(String line) {
    Pattern pattern = Pattern
        .compile("\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})] (wakes up|falls asleep|Guard #(\\d+) begins shift)");

    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      LocalDateTime timeOfEvent = LocalDateTime.of(Integer.parseInt(matcher.group(1)),
          Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)),
          Integer.parseInt(matcher.group(5)));

      String event = matcher.group(6);

      if (event.equals("wakes up")) {
        eventTimes.put(timeOfEvent, -1);
      } else if (event.equals("falls asleep")) {
        eventTimes.put(timeOfEvent, -2);
      } else {
        eventTimes.put(timeOfEvent, Integer.parseInt(matcher.group(7)));
      }
    }

    return null;
  }
}
