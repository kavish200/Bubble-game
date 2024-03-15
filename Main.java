import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BubbleGame extends JFrame {
    private ArrayList<Bubble> bubbles;
    private int score;

    public BubbleGame() {
        setTitle("Bubble Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        bubbles = new ArrayList<>();
        score = 0;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int mouseX = e.getX();
                int mouseY = e.getY();
                for (int i = 0; i < bubbles.size(); i++) {
                    Bubble bubble = bubbles.get(i);
                    if (bubble.contains(mouseX, mouseY)) {
                        bubbles.remove(i);
                        score++;
                        break;
                    }
                }
                repaint();
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBubble();
                repaint();
            }
        });
        timer.start();
    }

    private void createBubble() {
        Random random = new Random();
        int size = random.nextInt(50) + 30;
        int x = random.nextInt(getWidth() - size);
        int y = random.nextInt(getHeight() - size);
        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        bubbles.add(new Bubble(x, y, size, color));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Bubble bubble : bubbles) {
            bubble.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BubbleGame().setVisible(true);
            }
        });
    }
}

class Bubble {
    private int x, y, size;
    private Color color;

    public Bubble(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
}
