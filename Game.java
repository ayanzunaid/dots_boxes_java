import java.awt.*;
import java.applet.Applet;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Game extends Applet implements MouseListener,MouseMotionListener
{       private int sx = 17;
        private int sy = 15;
        private int rad = 5;
        private int len = 50;
        private int width = 4;
        private ArrayList<Rectangle> lines = new ArrayList<>();
        private ArrayList<Byte> lineused = new ArrayList<>();
        private Color red = Color.RED;
        private Color cyan = Color.CYAN;
        private Color blue = Color.BLUE;   //P2
        private Color green = Color.GREEN; //P1
        private Color tp = new Color(1,1,1,1);
        private Rectangle hovered = null;
        private Color player = green ;
        private int p1 = 0;
        private int p2 =0;
        public void init()
        {
            addMouseListener(this);
            addMouseMotionListener(this);
            
        }
	public void paint(Graphics g)
	{
         g.setColor(red);	
         for (int i=0;i<8;i++)
           for (int j= 0;j<8;j++)
            {
	       g.fillOval(sx + len*j,sy + len*i,2*rad,2*rad);

	    }	
         
         //g.setColor(red);
	 for (int i= 0;i<8;i++)
             for (int j= 0;j<7;j++)
              {
                //g.fillRect(sx + rad + len*j,sy+rad - 2+len*i,len,width);
                lines.add(new Rectangle(sx + rad + len*j,sy+rad - 2+len*i,len,width));
                lineused.add((byte)-1);
		//g.fillRect(sx + rad - 2+50*i,sy + rad + 50*j,width,len);
                lines.add(new Rectangle(sx + rad - 2+50*i,sy + rad + 50*j,width,len));
                lineused.add((byte)-1);
                
              }
         g.setColor(player);
         showStatus("Player 1 move" + " P1: " + p1 + " , P2: " + p2);

      	}

public void mouseClicked(MouseEvent e)
{   Graphics gg = getGraphics();
           gg.setColor(player);
 	for (Rectangle rect : lines)
           {
               if (rect.contains(e.getX(),e.getY()) && lineused.get(lines.indexOf(rect)) != (byte)1    )
                           {
                              gg.fillRect((int)rect.getX() , (int)rect.getY() , (int)rect.getWidth() , (int)rect.getHeight());
                              boolean w = checkWin(rect);
                              if (player.equals(green))
                                 {   if (!w)
                                     {player = blue;
                                     gg.setColor(player);
                                     showStatus("Player 2 move" + " P1: " + p1 + " , P2: " + p2);}
                                     
                                     
                                 }
                              else
                                 {   if (!w)
                                     {player = green;
                                     gg.setColor(player);
                                     showStatus("Player 1 move" + " P1: " + p1 + " , P2: " + p2);}
                                 }
                            lineused.set(lines.indexOf(rect) , (byte)1);
                            hovered = null;
                            break;
                           }
           }  

}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseDragged(MouseEvent e) {}


public void mouseMoved(MouseEvent e) {
             int flag = 0;   
             Graphics gg; 
              for (Rectangle rect : lines)
                 {
                     if (rect.contains(e.getX(),e.getY()) && lineused.get(lines.indexOf(rect)) != (byte)1 )
                           {
                             gg = getGraphics();
                             gg.setColor(cyan);
                             gg.fillRect((int)rect.getX() , (int)rect.getY() , (int)rect.getWidth() , (int)rect.getHeight());
				hovered = rect;
                                flag = 1; break;
                           }
                 }
           if (flag == 0 && hovered!=null)
               {   
                    gg = getGraphics();
                    gg.setColor(Color.WHITE);
                    
                    gg.fillRect((int)hovered.getX() , (int)hovered.getY() , (int)hovered.getWidth() , (int)hovered.getHeight());
                    
                    if ((int)hovered.getWidth() == len)
                       {
                           gg.setColor(red);
                           gg.fillOval((int)hovered.getX() -rad,(int)hovered.getY() + 2 -rad,2*rad,2*rad);
                           gg.fillOval((int)hovered.getX() + len -rad,(int)hovered.getY() + 2 -rad,2*rad,2*rad);
                       }
                    else
                       {   gg.setColor(red);
                           gg.fillOval((int)hovered.getX()+2 -rad ,(int)hovered.getY() -rad ,2*rad,2*rad);
                           gg.fillOval((int)hovered.getX() +2 -rad,(int)hovered.getY()+len -rad,2*rad,2*rad);
                         
                       }
                    hovered = null;
               }
                 
	}



   private boolean checkWin(Rectangle r)
         { 
           int x = (int)r.getX();
           int y = (int)r.getY();
           int w = (int)r.getWidth();
           int h=  (int)r.getHeight();
           Rectangle b1 ,b2,b3;
           boolean won = false;
           Graphics gg =getGraphics();
           if (w == len)
               { System.out.println("aa gaya"); int co =0;
                  b1 = new Rectangle(x,y-len,w,h);
                  b2 = new Rectangle(x-2,y-len +2 ,h,w);
                  b3 = new Rectangle(x+len-2,y-len+2,h,w);
                      System.out.println("done");
                 for (Rectangle re : lines)
                     {
			if (re.equals(b1) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b2) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b3) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                      }
                    System.out.println("yes");
                   System.out.println(co);
                 if (co == 3)
                    {    won = true;
                      if (player.equals(green))
                          {p1++;gg.drawString("P1",x+10,y-38);}
                      else {p2++;gg.drawString("P2",x+10,y-38);}
                    }



                   co = 0;
                   b1 = new Rectangle(x,y+len,w,h);
                  b2 = new Rectangle(x-2,y+2 ,h,w);
                  b3 = new Rectangle(x+len-2,y+2,h,w);
                  System.out.println("done");
                  for (Rectangle re : lines)
                     {
			if (re.equals(b1) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b2) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b3) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                      }
                    System.out.println("yes");
                  System.out.println(co);
                 if (co == 3)
                    { won = true;
                      if (player.equals(green))
                          {p1++;gg.drawString("P1",x+10,y +12);}
                      else {p2++;gg.drawString("P2",x+10,y +12 );}
                    }            
               return won;  

               }
           else
               {   
                   int co =0;
                  b1 = new Rectangle(x+ len,y,w,h);
                  b2 = new Rectangle(x+2,y-2 ,h,w);
                  b3 = new Rectangle(x+2,y+len-2,h,w);
                 for (Rectangle re : lines)
                     {
			if (re.equals(b1) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b2) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b3) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                      }
                   
                 if (co == 3)
                    {    won = true;
                      if (player.equals(green))
                          {p1++;gg.drawString("P1",x+10,y+10);}
                      else {p2++;gg.drawString("P2",x+10,y+10);}
                    }



                   co = 0;
                   b1 = new Rectangle(x-len,y,w,h);
                  b2 = new Rectangle(x-len+2,y-2 ,h,w);
                  b3 = new Rectangle(x-len+2,y-2+len,h,w);
                  for (Rectangle re : lines)
                     {
			if (re.equals(b1) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b2) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                        if (re.equals(b3) && lineused.get(lines.indexOf(re)) == 1)
                              co++;
                      }
                  
                 if (co == 3)
                    { won = true;
                      if (player.equals(green))
                          {p1++;gg.drawString("P1",x-40,y +10);}
                      else {p2++;gg.drawString("P2",x-40,y +10 );}
                    }            
               return won;




               }
          }

}