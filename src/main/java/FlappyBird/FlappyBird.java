package FlappyBird;

import Evolution.Agent;
import Evolution.Evolution;
import Evolution.Evolution.EvolutionBuilder;
import Genome.Activation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author User
 */
public class FlappyBird implements KeyListener {

    public static final int WIDTH = 640, HEIGHT = 480;
    public static int FPS = 60;
    private Evolution evolution;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll, generations, difficulty, numAlive;
    private int numBirds, score = 0, maxPipeRange, minPipeRange;
    private Random rand;
    public double[] information;

    private boolean paused;

    public void go() {
        frame = new JFrame("Flappy FlappyBird.Bird");
        numBirds = 1000;
        numAlive = numBirds;
        try {
            this.evolution = new EvolutionBuilder().setInputNum(4).setOutputNum(1).setNumSimulated(numBirds)
                    .setDefaultHiddenAF(Activation.sigmoid).setOutputAF(Activation.arrays.none)
                    .setAgentConstructor((initialMutation, Constants) -> new BirdAgent(Constants, initialMutation)).build();
        } catch (EvolutionBuilder.MissingInformation e) {
            throw new RuntimeException(e);
        }
        evolution.Constants.mutationSynapseProbability = 0.01;
        evolution.Constants.mutationNodeProbability = 0.1;
        evolution.Constants.mutationWeightShiftProbability = 0.02;
        evolution.Constants.mutationWeightRandomProbability = 0.02;
        evolution.Constants.mutationBiasShiftProbability = 0.02;
        evolution.Constants.mutationWeightShiftStrength = 1;

        this.maxPipeRange = 100;
        this.minPipeRange = 60;
        rects = new ArrayList<>();
        panel = new GamePanel(this, rects);
        frame.add(panel);
        information = new double[4];
        generations = 0;
        rand = new Random();

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        difficulty = 90;

        paused = true;

        while (true) {
            run();
        }
    }

    public static void main(String[] args) {
        new FlappyBird().go();
    }

    public void run() {
        if (paused) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            paused = !paused;
        }
        if (!paused) {
            if (time % 1 == 0) {
                information = new double[4];
                for (int i = 0; i < Math.min(2, rects.size()); i++) {
                    information[i] = HEIGHT - (rects.get(i).y == 0 ? (rects.get(i).height + 20) : rects.get(i).y);
                }
//                for(double d : information)System.out.print(d+",");
//                System.out.println();
                for (Agent agent : evolution.agents) {
                    if (((BirdAgent) agent).isAlive) {
                        information[2] = -((BirdAgent) agent).bird.vy;
                        information[3] = HEIGHT - ((BirdAgent) agent).bird.y;
                        if (agent.calculateOutput(information)[0] > 0.5) ((BirdAgent) agent).bird.jump();
                        ((BirdAgent) agent).bird.physics();
                    }
                }
            }
            panel.repaint();
            if (scroll % difficulty == 0) {
                int midPoint = rand.nextInt(40 + maxPipeRange, 480 - 40 - maxPipeRange + 1);
                int range = rand.nextInt(minPipeRange, maxPipeRange + 1);
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, midPoint - range);
                Rectangle r2 = new Rectangle(WIDTH, midPoint + range, GamePanel.PIPE_W, HEIGHT - midPoint - range);
                rects.add(r); //top pipe
                rects.add(r2); //bottom pipe
            }
            time++;
            scroll++;
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            for (Rectangle r : rects) {
                r.x -= 3;
                if (r.x + r.width < 320) {
                    toRemove.add(r);
                    score++;
                }
                for (Agent agent : evolution.agents) {
                    if (((BirdAgent) agent).isAlive) {
                        if (r.contains(((BirdAgent) agent).bird.x, ((BirdAgent) agent).bird.y)) {
                            agent.setScore(score + 1 + scroll * 1.0 / 106);
                            ((BirdAgent) agent).isAlive = false;
                            numAlive--;
                        }
                    }
                }
            }
            rects.removeAll(toRemove);

            for (Agent agent : evolution.agents) {
                if (((BirdAgent) agent).isAlive) {
                    if (((BirdAgent) agent).bird.y > HEIGHT - 40 || ((BirdAgent) agent).bird.y + Bird.RAD < 0) {
                        agent.setScore(score + scroll * 1.0 / 106);
                        ((BirdAgent) agent).isAlive = false;
                        numAlive--;
                    }
                }
            }

//            System.out.println(numAlive);
            if (numAlive <= 0) {
                difficulty = 90;
                numAlive = numBirds;
                score = 0;
                rects.clear();
                time = 0;
                scroll = 0;
                paused = true;
                evolution.nextGen();
                generations++;
                // FPS = 60;
            }
        }

        if (FPS != 1000) {
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            FPS = FPS == 1000 ? 60 : Math.min(1000, FPS * 2);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public double[] getInformation() {
        return information;
    }

    public double getScore() {
        return score + scroll * 1.0 / 106;
    }

    public int getGenerations() {
        return generations;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public boolean paused() {
        return paused;
    }

    public int speed() {
        return FPS == 1000 ? 1000 : FPS / 60;
    }
}
