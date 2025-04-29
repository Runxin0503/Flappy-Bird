package FlappyBird;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Evolution.Agent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class GamePanel extends JPanel {
    private ArrayList<Rectangle> rects;
    private Agent[] Agents;
    private FlappyBird fb;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(0, 158, 158);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength;

    public GamePanel(FlappyBird fb, ArrayList<Rectangle> rects) {
        this.fb = fb;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);

        try {
            pipeHead = ImageIO.read(new File("78px-Pipe.png"));
            pipeLength = ImageIO.read(new File("pipe_part.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            g.setColor(bg);
            g.fillRect(0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT);
            this.Agents = fb.getEvolution().agents;
            for (Agent agent : Agents) {
                if (((BirdAgent) agent).isAlive) ((BirdAgent) agent).bird.update(g);
            }
            for (Rectangle r : rects) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.GREEN);
                // g2d.fillRect(r.x, r.y, r.width, r.height);
                AffineTransform old = g2d.getTransform();
                g2d.translate(r.x + PIPE_W / 2, r.y + PIPE_H / 2);
                if (r.y < FlappyBird.HEIGHT / 2) {
                    g2d.translate(0, r.height);
                    g2d.rotate(Math.PI);
                }
                g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, GamePanel.PIPE_W, GamePanel.PIPE_H, null);
                g2d.drawImage(pipeLength, -PIPE_W / 2, PIPE_H / 2, GamePanel.PIPE_W, r.height, null);
                g2d.setTransform(old);
            }
            g.setColor(Color.RED);
            double[] information = fb.information;
            for (int i = 0; i < 2; i++) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.RED);
                g2d.fillArc(320, FlappyBird.HEIGHT - (int) information[i], PIPE_W / 4, PIPE_W / 4, 0, 360);
            }
            g.setFont(scoreFont);
            g.setColor(Color.BLACK);
            g.drawString("Generation: " + fb.getGenerations(), 10, 20);
            g.drawString("Score: " + fb.getScore(), 10, 40);
            g.drawString("Speed: " + (fb.speed() == 1000 ? "MAX SPEED" : fb.speed() + "x"), 10, 60);

//            if(fb.paused()) {
//                g.setFont(pauseFont);
//                g.setColor(new Color(0,0,0,170));
//                g.drawString("PAUSED", FlappyBird.FlappyBird.WIDTH/2-100, FlappyBird.FlappyBird.HEIGHT/2-100);
//                g.drawString("PRESS SPACE TO BEGIN", FlappyBird.FlappyBird.WIDTH/2-300, FlappyBird.FlappyBird.HEIGHT/2+50);
//            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
