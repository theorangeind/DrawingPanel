import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel
{
    int width, height;
    private int field[][];
    private int scale;
    private int x1, y1;
    private MouseHandler mouseHandler;
    Graphics graphics;

    public DrawingPanel()
    {
        this(8, 8, 100);
    }

    public DrawingPanel(int width, int height, int scaleMultiplier)
    {
        this.width = width;
        this.height = height;
        this.field = new int[width][height];
        this.scale = scaleMultiplier;

        mouseHandler = new MouseHandler();

        setPreferredSize(new Dimension(width * scaleMultiplier, height * scaleMultiplier));

        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    private Pixel getScaledPixel(int x, int y)
    {
        x = Math.floorDiv(x, scale);
        if(x < 0) x = 0;
        else if(x > width - 1) x = width - 1;
        y = Math.floorDiv(y, scale);
        if(y < 0) y = 0;
        else if(y > height -1) y = height - 1;
        return new Pixel(x, y);
    }

    class Pixel
    {
        public int xCoord, yCoord;
        public Pixel(int x, int y)
        {
            this.xCoord = x;
            this.yCoord = y;
        }
    }

    class MouseHandler extends MouseAdapter
    {
        private Pixel prevScaledPx = new Pixel(0, 0);

        @Override
        public void mousePressed(MouseEvent e)
        {
            x1 = e.getX();
            y1 = e.getY();
            graphics = getGraphics();
            Pixel scaledPx = getScaledPixel(x1, y1);
            field[scaledPx.xCoord][scaledPx.yCoord] = 1;
            graphics.fillRect(scaledPx.xCoord * scale,scaledPx.yCoord * scale, scale, scale);
        }

        public void mouseDragged( MouseEvent e )
        {
            x1 = e.getX();
            y1 = e.getY();
            Pixel px = getScaledPixel(x1, y1);

            if(!(px.xCoord == prevScaledPx.xCoord && px.yCoord == prevScaledPx.yCoord))
            {
                graphics = getGraphics();
                graphics.fillRect(px.xCoord * scale, px.yCoord * scale, scale, scale);
                field[px.xCoord][px.yCoord] = 1;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            Pixel px = getScaledPixel(e.getX(), e.getY());
            if(px.xCoord == prevScaledPx.xCoord && px.yCoord == prevScaledPx.yCoord)
            {
                return;
            }
            int curPxState = field[px.xCoord][px.yCoord];
            int prevPxState = field[prevScaledPx.xCoord][prevScaledPx.yCoord];
            graphics = getGraphics();
            if(prevPxState == -1)
            {
                Color prevColor = graphics.getColor();
                graphics.setColor(Color.white);
                graphics.fillRect(prevScaledPx.xCoord * scale,prevScaledPx.yCoord * scale, scale, scale);
                graphics.setColor(prevColor);
                field[prevScaledPx.xCoord][prevScaledPx.yCoord] = 0;
            }
            if(curPxState == 0)
            {
                graphics.drawRect(px.xCoord * scale,px.yCoord * scale, scale - 1, scale - 1);
                field[px.xCoord][px.yCoord] = -1;
            }
            prevScaledPx = px;
        }
    }
}
