package FlappyBird;

import Evolution.*;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author User
 */
public class FlappyBird implements KeyListener {
    
    public static final int WIDTH = 640, HEIGHT = 480;
    public static int FPS = 1000;
    private Evolution evolution;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll,generations,difficulty,numAlive;
    private int numBirds,score=0,maxPipeRange,minPipeRange;
    private Random rand;
    public double[] information;
    
    private boolean paused;
    
    public void go() {
        frame = new JFrame("Flappy FlappyBird.Bird");
        numBirds = 1000;
        numAlive = numBirds;
        this.evolution = new Evolution(numBirds); //YEAH BABY
        this.maxPipeRange=100;
        this.minPipeRange=60;
        rects = new ArrayList<>();
        panel = new GamePanel(this, rects);
        frame.add(panel);
        information = new double[4];
        generations=0;
        rand = new Random();
        
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        difficulty = 90;
        
        paused = true;

        while (true){
            run();
        }
    }
    public static void main(String[] args) {
        new FlappyBird().go();
    }

    public void run() {
        if(paused){
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            paused=!paused;
        }
        if(!paused) {
            if(time%1==0){
                information=new double[4];
                for (int i=0;i<Math.min(2,rects.size());i++){
                    information[i] = HEIGHT - (rects.get(i).y == 0 ? (rects.get(i).height + 20) : rects.get(i).y);
                }
//                for(double d : information)System.out.print(d+",");
//                System.out.println();
                for(NN nn : evolution.NeuralNets){
                    if(nn.score==-1){
                        information[2]=-nn.bird.vy;
                        information[3]=HEIGHT-nn.bird.y;
                        if (nn.calculateWeightedOutput(information)[0] > 0.5) nn.bird.jump();
                        nn.bird.physics();
                    }
                }
            }
            panel.repaint();
            if(scroll % difficulty == 0) {
                int midPoint = rand.nextInt(40+maxPipeRange,480-40-maxPipeRange+1);
                int range = rand.nextInt(minPipeRange,maxPipeRange+1);
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, midPoint-range);
                Rectangle r2 = new Rectangle(WIDTH, midPoint+range, GamePanel.PIPE_W, HEIGHT-midPoint-range);
                rects.add(r); //top pipe
                rects.add(r2); //bottom pipe
            }
            time++;
            scroll++;
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            for(Rectangle r : rects) {
                r.x-=3;
                if(r.x + r.width < 320) {
                    toRemove.add(r);
                    score++;
                }
                for(NN nn : evolution.NeuralNets){
                    if(nn.score==-1){
                        if(r.contains(nn.bird.x, nn.bird.y)) {
                            nn.score = score+1+scroll*1.0/106;
                            numAlive--;
                        }
                    }
                }
            }
            rects.removeAll(toRemove);

            for(NN nn : evolution.NeuralNets){
                if(nn.score==-1){
                    if(nn.bird.y > HEIGHT-40 || nn.bird.y+ Bird.RAD < 0) {
                        nn.score = score+scroll*1.0/106;
                        numAlive--;
                    }
                }
            }

//            System.out.println(numAlive);
            if(numAlive<=0) {
                difficulty=90;
                numAlive=numBirds;
                score=0;
                rects.clear();
                time = 0;
                scroll = 0;
                paused = true;
                evolution.nextGen();
                generations++;
                // FPS = 60;
            }
        }

        if(FPS!=1000){
            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            paused = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_A) {
            FPS = FPS==1000 ? 60 : Math.min(1000,FPS*2);
        }
    }
    public void keyReleased(KeyEvent e) {
        
    }
    public void keyTyped(KeyEvent e) {
        
    }

    public double[] getInformation(){
        return information;
    }

    public double getScore(){
        return score+scroll*1.0/106;
    }

    public int getGenerations(){
        return generations;
    }
    public Evolution getEvolution(){
        return evolution;
    }
    
    public boolean paused() {
        return paused;
    }

    public int speed(){
        return FPS==1000 ? 1000 : FPS/60;
    }
}
