package aoc.aoc2018;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import java.awt.Color;

public class Day10 {

  private static List<Event> events;

  private static Rectangle rectangle;

  public static void main(String[] args) throws IOException, InterruptedException {
    events = new LinkedList<>();

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String s;

    while ((s = br.readLine()) != null) {
      parseEvent(s);
    }
    
    rectangle = new Rectangle();

    Thread.sleep(1000);
    int width,height;
    int last_width = 0;

    for(int i = 0; i < 100000; i++)
    {
      rectangle.x1_pos = events.get(0).x_pos;
      rectangle.y1_pos = events.get(0).y_pos;
      rectangle.x2_pos = events.get(1).x_pos;
      rectangle.y2_pos = events.get(1).y_pos;
      
      for(Event e : events)
      {
        e.step();

        rectangle.update(e.x_pos, e.y_pos);
      }
      
      width = rectangle.x2_pos - rectangle.x1_pos;
      height = rectangle.y2_pos - rectangle.y1_pos;
      if( width <= 500 && height <= 500)
      {
        if(last_width > width)
        {
          rectangle.repaint();
          Thread.sleep(100);
        }
        else
        {
          System.out.println("Breaking on i = " + i);
          break;
        }
      }
      last_width = width;
      
    }

  }

  private static Event parseEvent(String line) {
    final String regex = "position=<\\s*([\\-]?\\d+),\\s*([\\-]?\\d+)>\\svelocity=<\\s*([\\-]?\\d+),\\s*([\\-]?\\d+)>";
    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      Event event = new Event(Integer.parseInt(matcher.group(1)),
                          Integer.parseInt(matcher.group(2)), 
                          Integer.parseInt(matcher.group(3)), 
                          Integer.parseInt(matcher.group(4)));

      events.add(event);
    }

    return null;
  }

  static class Event
  {
    int x_pos;
    int y_pos;
    int x_vel;
    int y_vel;
    
    Event(int x_pos, int y_pos, int x_vel, int y_vel)
    {
      this.x_pos = x_pos;
      this.y_pos = y_pos;
      this.x_vel = x_vel;
      this.y_vel = y_vel;
    }

    public void step()
    {
      this.x_pos += x_vel;
      this.y_pos += y_vel;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d] [%d,%d]", 
          this.x_pos,
          this.y_pos,
          this.x_vel,
          this.y_vel
        );
    }
  }

  static class Rectangle extends JFrame
  {
    int x1_pos;
    int y1_pos;
    int x2_pos;
    int y2_pos;
    
    public Rectangle(int x1_pos, int y1_pos, int x2_pos, int y2_pos)
    {
      //Set JFrame title
      super("Message Frame");

      this.x1_pos = x1_pos < x2_pos ? x1_pos : x2_pos;
      this.y1_pos = y1_pos < y2_pos ? y1_pos : y2_pos;
      this.x2_pos = x1_pos > x2_pos ? x1_pos : x2_pos;
      this.y2_pos = y1_pos > y2_pos ? y1_pos : y2_pos;


      //Set default close operation for JFrame
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Set JFrame size
      setSize(1000,1000);

      //Make JFrame visible 
      setVisible(true);
    }

    public Rectangle()
    {
      this(0,0,0,0);
    }

    public void update(int x, int y)
    {
      if(x < x1_pos)
      {
        x1_pos = x;
      }
      if(x > x2_pos)
      {
        x2_pos = x;
      }

      if(y < y1_pos)
      {
        y1_pos = y;
      }
      if(y > y2_pos)
      {
        y2_pos = y;
      }
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);

      //draw rectangle outline
      //g.drawRect(50,50,300,100);

      //set color to RED
      //So after this, if you draw anything, all of it's result will be RED
      g.setColor(Color.RED);

      //fill rectangle with RED
      //g.fillRect(50,50,300,100);

      for(Event e : events)
      {
        int x = e.x_pos - this.x1_pos;
        int y = e.y_pos - this.y1_pos + 100;
        //g.drawLine(x, y, x, y);
        g.fillRect(x*5, y*5, 4, 4);

      }
    }

    @Override
    public String toString() {
        return String.format("[%d,%d] [%d,%d]", 
          this.x1_pos,
          this.y1_pos,
          this.x2_pos,
          this.y2_pos
        );
    }
  }

}
