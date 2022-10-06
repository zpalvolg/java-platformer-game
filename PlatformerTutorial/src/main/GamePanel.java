package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private int xDelta = 100;
	private int yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private int animationTicker = 0,animationIndex = 0,animationSpeed = 15;
	private int playerAction = IDLE;
	
	
	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		setPanelSize();
		importImage();
		loadAnimations();
		
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private void loadAnimations() {
		animations = new BufferedImage[9][6];
		
		for (int j = 0; j < animations.length; j++) {
	
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
			}
		}
	
		
	}

	private void importImage() {
		InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png");
		try {
			img = ImageIO.read(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280,800);
		setPreferredSize(size);
	}

	public void changeXDelta(int value) {
		this.xDelta += value;
	}
	
	public void changeYDelta(int value) {
		this.yDelta += value;
	}
	
	public void setRecPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}
	
	private void updateAnimationTicker() {
		
		animationTicker++;
		
		if (animationTicker >= animationSpeed) {
			animationTicker = 0;
			animationIndex++;
			
			if (animationIndex >= getSpriteAmount(playerAction)) {
				animationIndex=0;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateAnimationTicker();
		
		g.drawImage(animations[playerAction][animationIndex], (int)xDelta, (int)yDelta, 256,160, null);
	}

}
