package gameFiles.Battle;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.MainApplication;
import gameFiles.Inventory.Consumable;
import gameFiles.Inventory.Item;
import gameFiles.Inventory.Weapon;

public class BattleInventory extends GraphicsPane {
	private MainApplication program;
	private MainCharacter mc;
	private GraphicsPane last;
	private boolean alive;
	private boolean action;
	
	private ArrayList<GObject> hoverable = new ArrayList<GObject>();
	private int hovering;
	
	private ArrayList<GObject> render = new ArrayList<GObject>();
	private GRect opacityBg;
	private GRect itemBacking;
	private ArrayList<GObject> inventoryDisplay = new ArrayList<GObject>();
	private GRect itemInfoBacking;
	private GLabel select;
	private GLabel itemInfo;
	private GLabel temp;
	
	public BattleInventory(MainApplication app, GraphicsPane last, MainCharacter mc) {
		this.program = app;
		this.mc = mc;
		this.alive = true;
		this.last = last;
		
		select = new GLabel(">");
		select.setFont("Arial-40");
		select.setColor(Color.WHITE);
		
		opacityBg = new GRect(0, 0, program.getWidth(), program.getHeight());
		opacityBg.setFillColor(new Color(138, 138, 138, 138));
		opacityBg.setFilled(true);
		program.add(opacityBg);
		
		itemBacking = new GRect(200, 200, program.getWidth() / 6, program.getHeight() - 400);
		itemBacking.setFillColor(new Color(200, 200, 200));
		itemBacking.setFilled(true);
		render.add(itemBacking);
		
		itemInfoBacking = new GRect(itemBacking.getX() + 400, itemBacking.getY(), program.getWidth() /2 , program.getHeight() - 800);
		itemInfoBacking.setFillColor(new Color(200, 200, 200));
		itemInfoBacking.setFilled(true);
		render.add(itemInfoBacking);
		
		itemInfo = new GLabel("", itemInfoBacking.getX() + 50, itemInfoBacking.getY() + 50);
		itemInfo.setFont("Arial-20");
		render.add(itemInfo);
		
		for (int i = 0; i < mc.getMainInventory().getMyInventory().size(); i++) {
			temp = new GLabel(mc.getMainInventory().getMyInventory().get(i).getItemName(), itemBacking.getX() + 25, itemBacking.getY() + 25 + (50 * i));
			temp.setFont("Arial-15");
			hoverable.add(temp);
			inventoryDisplay.add(temp);
			render.add(temp);
		}
		System.out.println("Constructor complete - battleinventory.java");
	}
	
	@Override
	public void showContents() {
		alive = true;
		System.out.println("Showed contents - Inventory.java");
		
		for (GObject iter : render) {
			program.add(iter);
		}
		
		new Thread(this::gameActions).start();
		program.add(select);
		System.out.println("Completed show contents - battle inventory");
	}

	@Override
	public void hideContents() {
		for (GObject iter : render) {
			program.remove(iter);
		}
		program.remove(select);
		program.remove(opacityBg );
	}
	
	private void gameActions() {
		while (alive) {
			//System.out.println("gameaction-battleinventory");
			
			if (hoverable.size() == 0) {
				select.setLocation(-999, -999);
			} else {
				select.setLocation(hoverable.get(hovering).getX() - 20, hoverable.get(hovering).getY() + (hoverable.get(hovering).getHeight() / 2));
			}
			
			itemInfo.setLabel(mc.getMainInventory().getMyInventory().get(hovering).getItemDescription());
			
			try {
	            // Pause the thread for a short duration (adjust as needed)
	            Thread.sleep(200);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public boolean wasAction() {
		return action;
	}
	
	public void exit() {
		System.out.println("Leaving inventory");
		alive = false;
		hideContents();
		program.setCurScreen(last);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void resetHovering() {
		hoverable.clear();
		hovering = 0;
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		System.out.println("Key down - battle inventory");
		
		switch (keyCode) {
		case KeyEvent.VK_X:
			action = false;
			exit();
			break;
		case KeyEvent.VK_Z:
			//get the object we are hovering
			//GObject obj = hoverable.get(hovering);
			Item item = mc.getMainInventory().getMyInventory().get(hovering);		
			Weapon temp;
			//use item:
			
			switch (item.getItemType()) {
			case CONSUMABLE:
				Consumable consumable = (Consumable)item;
				if (consumable.isHealthType()) {
					System.out.println("using item hp");
					mc.setHp(mc.getHp() + consumable.getHealAmount());
					hideContents();
					//TextBox(mc.getName() + " used: " + consumable.getItemName() + ".");
					//TextBox(mc.getName() + " healed " + consumable.getHealAmount() + " hp.");
					action = true;
					mc.getMainInventory().getMyInventory().remove(consumable);
					exit();
				} else {
					System.out.println("using item mp");
					mc.setMp(mc.getMp() + consumable.getHealAmount());
					hideContents();
					//TextBox(mc.getName() + " used: " + consumable.getItemName() + ".");
					//TextBox(mc.getName() + " restored " + consumable.getHealAmount() + " mp.");
					action = true;
					mc.getMainInventory().getMyInventory().remove(consumable);
					exit();
				}
				break;
			case WEAPON:
				System.out.println("using item");
				temp = mc.getEquipped();
				mc.setEquipped((Weapon)item);
				mc.getMainInventory().getMyInventory().remove(item);
				mc.getMainInventory().getMyInventory().add(temp);
				hideContents();
				//TextBox(mc.getName() + " equipped: " + item.getItemName() + ".");
				action = true;
				mc.getMainInventory().getMyInventory().remove(item);
				exit();
				break;
			}
			break;
		case KeyEvent.VK_RIGHT:
			
			break;
			
		case KeyEvent.VK_LEFT:
			
			break;
			
		case KeyEvent.VK_UP:
			System.out.println("Up key down");
			hovering--;
			if (hovering < 0) {
				hovering = hoverable.size() - 1;
			}
			break;
			
		case KeyEvent.VK_DOWN:
			hovering++;
			if (hovering > hoverable.size() - 1) {
				hovering = 0;
			}
			break;
		}
	}
	
}
