package gameFiles.Battle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.MainApplication;

public class TextBox extends GraphicsPane implements KeyListener {
	AudioPlayer audio = AudioPlayer.getInstance();
	private MainApplication program;
	private GraphicsPane last;
	private boolean alive;
	private GImage textBoxBack;
	private GLabel textBox;
	private String showText = "";
	private String outText = "";
	private boolean textActive = false;
	
	public TextBox(MainApplication app, GraphicsPane from, String showText) {
		this.program = app;
		this.showText = showText;
		this.last = from;
		
		//textBoxBack = new GRect(0, program.getHeight(), 0, 0);
		textBoxBack = new GImage("media/textbox.png", 0, program.getHeight() - (program.getHeight() / 5));
		//textBoxBack = new GRect(0, 0, program.getWidth(), program.getHeight());
		textBoxBack.setSize(program.getWidth() - 25, program.getHeight() / 5);

		textBox = new GLabel("", textBoxBack.getX() + 40, textBoxBack.getY() + 80);
		textBox.setFont("Arial-45");
	}
	
	@Override
	public void showContents() {
		alive = true;
		program.add(textBoxBack);
		program.add(textBox);
		//textTimer.start();
		
		Thread text = new Thread(this::showText);
		text.start();
	}
	
	@Override
	public void hideContents() {
		program.remove(textBoxBack);
		program.remove(textBox);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void showText() {
		textBox.setLocation(textBoxBack.getX() + 50, textBoxBack.getY() + 50);
		
		while (showText != "") {
			audio.playSound("sounds", "text.mp3");
			outText = outText + showText.charAt(0);
			textBox.setLabel(outText);
			showText = showText.substring(1);
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		textActive = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (textActive) {
			audio.playSound("sounds", "back.mp3");
			hideContents();
			program.setCurScreen(last);
			alive = false;
		}
	}
}
