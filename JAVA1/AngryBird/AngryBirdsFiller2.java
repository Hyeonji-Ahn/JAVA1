package unit4;
// Angry Birds template provided by Mr. David
// stopwatch system from https://gist.github.com/EdHurtig/78cbe307c1c85db12af7
//images from Animal Crossing

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import unit2.Pong_Tiffany_Ahn;

public class AngryBirdsFiller2 extends JPanel {
	
	// the width/height of the window - feel free to change these
	private final int W_WIDTH = 900, W_HEIGHT = 600;
	
	// the number of enemies in the game - feel free to change
	private final int NUM_ENEMIES = 8;
	
	// a constant for the gravitational pull on our 'bird'
	private final double GRAVITY = .3;
	private final int BALLDIAM = 25;
	
	// Enemy's diameter
	private final int ENEMYDIAM = 50;
	
	//location of enemies
	private int[] enemyX = new int[NUM_ENEMIES] ; 
	private int[] enemyY = new int[NUM_ENEMIES] ; 
	
	//whether the enemy is dead or not
	private boolean[] dead = new boolean[NUM_ENEMIES];
	
	//whether the gravity is applied to the ball
	private boolean isGravityOn = false;
	
	//variables for images
	private Image ballon6;
	private Image back1;
	private Image back2;
	private Image back3;
	private Image ggback;
	private Image ball;
	private Image slingshot;
	
	//more instance variables
	
	//location of the sling shot
	 private int slingshotx = 75;
	 private int slingshoty = W_HEIGHT-175;
	 
	 //location of the ball
	 private double ballx = (double)slingshotx+20;
	 private double bally =(double)slingshoty;
	 
	 //start location of the mouse click
	 private int startX,startY;
	 
	 //speed of the ball
	 private double speedX, speedY;
	 
	 //score
	 private int score;
	 
	 //maximum and minimum location of the balloons
	 private double ballonMaxX = W_WIDTH - ENEMYDIAM;
	 private double ballonMaxY = slingshoty-ENEMYDIAM*3;
	 private double ballonMinX = slingshotx + 100; //100 for more spaces between sling shot and balloons
	 private double ballonMinY = 0;
	 
	 
	
	 //stages
	 private boolean[] stages = {false,true,false,false,false};
	 
	 //Visibility of background
	 private boolean[] background = {false,true,false,false,false};
	 //add false to the 0th index of both arrays so that it is easier to match the stage and value in array
	 //ex) stage 1 = stages[1] , background[1]
	 
	 //current stage
	 private int stage=1;
	 
	 //speed of balloons
	 private int[] ballonSpeedY = new int[NUM_ENEMIES];
	 private int[] ballonSpeedX = new int[NUM_ENEMIES];
	 
	 private int used_balls = 0;
	
	// this method is for setting up any arrays that need filling in and loading images. 
	// This method will run once at the start of the game.
	public void setup() {

		//loading an image file
		try {
			ballon6 = ImageIO.read(new File("ballon6.png"));
			back1 = ImageIO.read(new File("back1.png"));
			back2 = ImageIO.read(new File("back2.jpeg"));
			back3 = ImageIO.read(new File("background3.jpg"));
			ball = ImageIO.read(new File("ball.png"));
			slingshot = ImageIO.read(new File("slingshot.png"));
			ggback = ImageIO.read(new File("gg.jpg"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//setup the location of enemies
		for(int i=0; i<enemyX.length; i++) {
			enemyX[i] = (int)(Math.random() * (ballonMaxX - ballonMinX ) + ballonMinX);
			enemyY[i] = (int)(Math.random() * (ballonMaxY - ballonMinY ) + ballonMinY);
		}
		
		// make sure that every enemies are appeared
		for(int i=0; i<dead.length; i++) {
			dead[i]= false;
		}
		
		//start the stopwatch
		start();
	}
	
	// move your 'bird' and apply any gravitational pull 
	public void moveBird() {
		ballx += speedX;
		bally += speedY;
		if(isGravityOn) {
			speedY += GRAVITY; //grivatational pull
		}
		
	}
	
	// check for any collisions between your 'bird' and the enemies.
	// if there is a collision, address it
	public void checkHits() {
		
		//check whether the ball is out of the window
		if(ballx>=W_WIDTH || bally>=W_HEIGHT || ballx<=-BALLDIAM || bally<=-BALLDIAM) {
			ballx = (double)slingshotx+20; //reset the location of ball
			bally =(double)slingshoty;
			isGravityOn = false; // turn off the gravity
			speedX = 0; //reset the speed
			speedY = 0;
			used_balls ++; //increase the number of used ball
		}
		
		//check collision between enemy and the ball
		for(int i = 0; i < enemyX.length; i++) {
			if(!dead[i]) {
				if(distance(ballx,bally,enemyX[i], enemyY[i]) <= ENEMYDIAM/2 + BALLDIAM/2) {
				dead[i] = true; //make enemy to disappear
				score ++; //increase the score
				}
			}
		}
		
		//check whether the balloons are out of the window
		for(int i = 0; i < enemyY.length; i++) {
			if( enemyY[i]>=slingshoty-ENEMYDIAM*3 || enemyY[i]<=0) { // check Y value
				//y value should not over slingshoty-ENEMYDIAM*3 because of the 3rd round.
				ballonSpeedY[i] = -ballonSpeedY[i];
			}
			if(enemyX[i]>=W_WIDTH-ENEMYDIAM || enemyX[i]<=0) {  //check X value
				ballonSpeedX[i] = -ballonSpeedX[i];
				
			}
		}
	}
	
	public void OtherStages() {
		
		//move to the stage 2
		if(score==8 && stages[1]) {
			stages[1] = false; // change the stage
			stages[2] = true;
			stage=2;
			background[2]=true; // change the back ground
			background[1]=false;
			ballx = (double)slingshotx+20; //reset the ball
			bally =(double)slingshoty;
			isGravityOn = false;
			speedX = 0; // reset the speed of the ball 
			speedY = 0;
			for(int i=0; i<dead.length; i++) { // reappear the balloons
				dead[i] = false;
				ballonSpeedY[i] += i+1; //change Yspeed of the balloon
			}
			
		}
		if(stages[2]) { //change the y location of the balloons in stage 2
			for(int i=0; i<enemyY.length; i++) {
				enemyY[i] += ballonSpeedY[i];
			}
		}
		
		//move to stage 3
		if(score==16 && stages[2]) {
			stages[3] = true; //change the stage
			stage=3;
			stages[2] = false;
			
			background[3]=true; // change the background
			background[2]=false;
			
			ballx = (double)slingshotx+20; //reset the ball
			bally =(double)slingshoty;
			isGravityOn = false;
			
			speedX = 0; //reset the bal's location
			speedY = 0;
			
			for(int i=0; i<dead.length; i++) { //reappear the balloons
				int count = dead.length; //count for speed of balloon's x value
				dead[i] = false;
				ballonSpeedX[i] += count; //change speed of balloon's x value
				ballonSpeedY[i] = 0; //make Yspeed of balloon 0
				count++;
			}
			
		}
		
		//change the x location of the balloons in the stage 3
		if(stages[3]) {
			for(int i=0; i<enemyX.length; i++) {
				enemyX[i] += ballonSpeedX[i];
			}
		}
		
		//move to the end of the game
		if(score==24 && stages[3]) {
			stages[3] = false; //change the stage
			stages[4] = true;
			background[3]=false; //change the back ground
			background[4] = true;
			stop();
			
			
		}
	
		
	}
	
//calculate distance between two objects
	private double distance(double x1, double y1, double x2, double y2) {
		int distance = (int) Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1),2));
		
		return distance;
	}
	
	
	// what you want to happen at the moment when the 
	// mouse is first pressed down.
	public void mousePressed(int mouseX, int mouseY) {
		startX = mouseX; //record x value of the mouse
		startY = mouseY; //record y value of the mouse
	}
	
	// what you want to happen when the mouse button is released
	public void mouseReleased(int mouseX, int mouseY) {
		double distDraggedX = mouseX - startX; 
		double distDraggedY = mouseY - startY;
		speedX = -distDraggedX / 10.0; 
		speedY = -distDraggedY / 10.0;
		isGravityOn = true; //turn on the gravity for the ball
	}
	
	// draws everything in our project - the enemies, your 'bird', 
	// and anything else that is present in the game
	public void paint(Graphics g) {
		// draws a background depends on stage
		if(background[1] ==true) {
		g.drawImage(back1, 0,0, W_WIDTH, W_HEIGHT, null);
		}
		if(background[2] ==true) {
			g.drawImage(back2, 0,0, W_WIDTH, W_HEIGHT, null);
		}
		if(background[3] ==true) {
			g.drawImage(back3, 0,0, W_WIDTH, W_HEIGHT, null);
		}
		if(background[4] ==true) {
			g.drawImage(ggback, 0,0, W_WIDTH, W_HEIGHT, null);
		}
		
		//draw enemies
		for(int i = 0; i < enemyX.length; i++) {
			if(!dead[i]) {
				g.drawImage(ballon6, enemyX[i],enemyY[i], ENEMYDIAM, ENEMYDIAM*2, null);
			}
			
		}
		
		//draw ball
		g.drawImage(ball, (int)ballx,(int)bally, BALLDIAM, BALLDIAM, null);
		
		//draw sling shot
		g.drawImage(slingshot,slingshotx,slingshoty, 75, 100, null);
		
		// writes the score of the game 
		Font f = new Font("Arial", Font.BOLD, 30);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("SCORE:" +score, 20, 50);
		
		//write the stage of the game
		g.drawString("STAGE " +stage, W_WIDTH-150, 50);
		
		
		//end statement
		if(stages[4]) {
			g.drawString("Thank you for playing!", W_WIDTH/2-175, W_HEIGHT/2-200);
			g.drawString(toString(), W_WIDTH/2-175, W_HEIGHT/2-150); //draw a time
			g.drawString("used balls: " + used_balls, W_WIDTH/2-130, W_HEIGHT/2-100); //write a used ball 
		}else {
			g.drawString("BALLS: " + used_balls, W_WIDTH-350, 50); //write a used ball
		}
		
		
		
	}
	
	    //Whether the stopwatch is running
	    private boolean running;
	   
	    //The start time in microseconds
	    private long start;

	    //The end time
	    private long end;

	    // Default Constructor
	    public void Stopwatch() {
	        this.start = 0;
	        this.end = 0;
	    }

	    // Determines if the Stopwatch is running (could be paused)
	    public boolean isRunning() {
	        return running;
	    }

	   

	    //Starts the Stopwatch
	    public void start() {
	        start = System.nanoTime();
	        running = true;
	    }

	    //Stops the stopwatch and returns the time elapsed
	    public long stop() {
	        if (!isRunning()) {
	            return -1;
	 
	        } else {
	            end = System.nanoTime();
	            running = false;
	            return end - start;
	        }
	    }

	    //calculate time
	    public long elapsed() {
	        if (isRunning()) {
	            return (System.nanoTime() - start);
	        } else
	            return (end - start);
	    }

	    // Returns the number of seconds this Stopwatch has elapsed
	    public String toString() {
	        long enlapsed = elapsed();
	        return (((double) enlapsed / 1000000000.0)) + " seconds";
	    }

	
	
	// ************** DON'T TOUCH THE BELOW CODE ********************** //
	
	public void run() {
		while (true) {
			moveBird();
			checkHits();
			repaint();
			OtherStages();
			
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {}
		}
	}
	
	public AngryBirdsFiller2() {
		setup();
		
		JFrame frame = new JFrame();
		frame.setSize(W_WIDTH,W_HEIGHT);
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				AngryBirdsFiller2.this.mousePressed(e.getX(),e.getY());	
			}
			public void mouseReleased(MouseEvent e) {
				AngryBirdsFiller2.this.mouseReleased(e.getX(),e.getY());
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		run();
	}
	public static void main(String[] args) {
		new AngryBirdsFiller2();
	}
}
