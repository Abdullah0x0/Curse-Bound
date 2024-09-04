package gameFiles.Inventory;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.MainApplication;
import gameFiles.Battle.MainCharacter;
import gameFiles.Map.MapGraphics;


public class InventoryPane  extends GraphicsPane implements MouseListener{
	private MainApplication program;
	private MapGraphics prev;
	AudioPlayer audio = AudioPlayer.getInstance();
	private MainCharacter mc;
	private ArrayList<GObject> render = new ArrayList<GObject>();
	private ArrayList<GObject> remove = new ArrayList<GObject>();
	private int currSelection;
	private GLabel select;
	private GRect intro;
	private GRect Background;
	private GLabel introText;
	private GButton returnButton;
			
		public InventoryPane(MainApplication program, MainCharacter mc, MapGraphics prev) {
			super();
			this.program = program;
			this.prev = prev;
	        this.mc = mc;
	        
	      //Background GRect to edit background
			Background = new GRect(0, 0, program.getWidth(), program.getHeight());
			Background.setFillColor(new Color(34, 40, 49)); // Dark Blue-Grey background
			Background.setFilled(true);
			render.add(Background);
			
			//GRect for inventory introduction at the top of the screen
			intro = new GRect(0, 0, program.getWidth(), program.getHeight() / 8);
			intro.setFillColor(new Color(57, 62, 70)); // Dark Steel Blue introduction GRect
			intro.setFilled(true);
			render.add(intro);
			
			// GLabel Text for inventory 
			introText = new GLabel("INVENTORY", intro.getX() + intro.getWidth() / 4, intro.getY() + intro.getHeight() / 2 + 30);
			introText.setFont("Helvetica Neue-Bold-70");
			introText.setColor(new Color(255, 255, 255));
		    render.add(introText);

			
			// RETURN BUTTON
			double buttonWidth = 150;
			double buttonX = (program.getWidth() - buttonWidth) / 1.5;
			double buttonY = program.getHeight() / 25; // Adjust this to set the vertical position

			returnButton = new GButton("RETURN", buttonX, buttonY, 250, 50); 
			returnButton.setFillColor(new Color(75, 139, 190));
			returnButton.setColor(new Color(255, 255, 255));
			returnButton.addMouseListener(this);
			render.add(returnButton);

			select = new GLabel(">");
			select.setFont("Arial-40");
			select.setColor(Color.WHITE);
			select.setLocation(30, 210);
			render.add(select);	 
				
	        displayInventory();
	       
		}
		
		private void displayInventory() {    
	        int yCoordinate = 150; // Starting y-coordinate for items
	        //get items from MainCharacter 
	        for (Item item : mc.getMainInventory().getMyInventory()) {
	        	displayItem(item, yCoordinate); //display items main character has
	            yCoordinate += 120; // Update y-coordinate for the next item
	        }
	    }
		
		private void removeInventory() {
	        for (GObject item : remove) {
	           program.remove(item);
	        }
	        remove.clear();
	    }
		
		private void displayItem(Item item, double yCoordinate) {
		    // Rectangle for displaying the USE INFO of the inventory item
            GRect itemRect = new GRect(50, yCoordinate, 300, 100);
	        itemRect.setFillColor(new Color(46, 139, 87));
	        itemRect.setFilled(true);
	        render.add(itemRect);
	        remove.add(itemRect);
	       
	        // Rectangle for displaying the NAME of the inventory item
			GRect itemLabel = new GRect(375, yCoordinate, 300, 100);
			itemLabel.setFillColor(new Color(119, 136, 153));// Slate Grey label background
			itemLabel.setFilled(true);
			render.add(itemLabel);
			remove.add(itemLabel);

			// Rectangle for displaying the Type of the inventory item
			GRect itemType = new GRect(700, yCoordinate, 300, 100);
			itemType.setFillColor(new Color(119, 136, 153)); // Midnight Green quantity background
			itemType.setFilled(true);
			render.add(itemType);
			remove.add(itemType);

			// Rectangle for displaying the description of the inventory item
			GRect itemDescription = new GRect(1025, yCoordinate, 500, 100);
			itemDescription.setFillColor(new Color(119, 136, 153)); // Slate Grey description background
			itemDescription.setFilled(true);
			render.add(itemDescription);
			remove.add(itemDescription);

		   // GLabel for displaying the USE label of the inventory item
            GLabel itemNameLabel = new GLabel(item.getItemName(), 75, yCoordinate + 30);
            itemNameLabel.setFont("Helvetica Neue-20");
            render.add(itemNameLabel);
            remove.add(itemNameLabel);

           // GLabel for displaying the USE type  info of the inventory item
            GLabel itemTypeLabel = new GLabel("Type: " + item.getItemType(), 75, yCoordinate + 60);
            itemTypeLabel.setFont("Helvetica Neue-15");
            render.add(itemTypeLabel);
            remove.add(itemTypeLabel);
            
           // GLabel for displaying the NAME of the inventory item
	        GLabel itemText = new GLabel(item.getItemName(), 395,yCoordinate + 50);
            itemText.setFont("Helvetica Neue-Bold-25");
            render.add(itemText);
            remove.add(itemText);
	        
         // GLabel for displaying the type of the inventory item
	        GLabel typeText = new GLabel(item.getItemType().getName(), 720, yCoordinate + 50);
            typeText.setFont("Helvetica Neue-Bold-25");
            render.add(typeText);
            remove.add(typeText);
	        
         // GLabel for displaying the description of the inventory item
	        GLabel descriptionText = new GLabel(item.getItemDescription() , 1045 ,yCoordinate + 50);
	        descriptionText.setFont("Helvetica Neue-20");
            render.add(descriptionText);
            remove.add(descriptionText);

            if (item.getItemType() == ItemType.WEAPON) {
                // Display weapon-specific information
                Weapon weapon = (Weapon) item;
                GLabel damageLabel = new GLabel("Damage: " + weapon.getItemDamage(), 200, yCoordinate + 60);
                damageLabel.setFont("Arial-15");
                render.add(damageLabel);
                remove.add(damageLabel);

                GLabel critLabel = new GLabel("Critical Rate: " + weapon.getCritChance() + "%", 200, yCoordinate + 80);
                critLabel.setFont("Arial-15");
                render.add(critLabel);
                remove.add(critLabel);
            } else if (item.getItemType() == ItemType.CONSUMABLE) {
                // Display consumable-specific information
                Consumable consumable = (Consumable) item;
                GLabel healLabel = new GLabel("Heal Amount: " + consumable.getHealAmount(), 200, yCoordinate + 80);
                healLabel.setFont("Arial-15");
                render.add(healLabel);
                remove.add(healLabel);
            }

		}
		 
		 public void showContents() {
			 	System.out.println("showContents");
				for (GObject obj : render) {
					program.add(obj);
				}
				program.add(returnButton);
				displayInventory();
			}
		 public void show() {
			 	System.out.println("showContents");
				for (GObject obj : remove) {
					program.add(obj);
				}			
			}
		 
		 
		 public void hideContents() {
				program.clear();
			}
		
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof GButton) {
	            GButton sourceButton = (GButton) e.getSource();
	            sourceButton.setFillColor(Color.RED); // Change to red when hovering
	        }
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			 if (e.getSource() instanceof GButton) {
		            returnButton.setFillColor(new Color(75, 139, 190)); // Change back to the original color when not hovering
		        }
		}
		
		 @Override
		    public void mousePressed(MouseEvent e) {
		    	//Switch to map when Return is pressed
		        GObject obj = program.getElementAt(e.getX(), e.getY());
		        if (obj == returnButton) {
		            program.switchToMap(prev); 
		        } 
		    }

		 
		 private void handleItemHovering(int currArrow) {
		       render.remove(select);
		       if(currArrow  == 0) {
		    	   select.setLocation(30, 210); //position GLabel select
		       }
		       select.setLocation(30, 210 + 120 * currArrow); //update Select location
		       select.sendToFront();
		       render.add(select);
		    }

		   
			private void useSelectedItem(int currSelection) {
		        // Use the selected item based on the provided selected item 
		        ArrayList<Item> inventory = mc.getMainInventory().getMyInventory();

		        if (currSelection >= 0 && currSelection < inventory.size()) {
		            Item selectedItem = inventory.get(currSelection);
		            Weapon temp;

		            // Perform item usage based on item type
		            if (selectedItem instanceof Consumable) {
		                // Use consumable item
		                Consumable consumable = (Consumable) selectedItem;
		                mc.getMainInventory().removeItem(currSelection); //remove item after consumption
		            } else if (selectedItem instanceof Weapon) {
		                // Use weapon item
		            	temp = mc.getEquipped();	            	
		            	mc.setEquipped((Weapon)selectedItem);
		            	mc.getMainInventory().removeItem(currSelection);
		            	mc.getMainInventory().addItem(temp);
		              
		            }
		            // Refresh the inventory display after using an item s
		            removeInventory();
		            displayInventory();
		            show();
		        }
		    }

		    public void keyPressed(KeyEvent e) {
		    	int keyCode = e.getKeyCode();
		    	switch (keyCode) {
		    	case KeyEvent.VK_UP:
		    		audio.playSound("sounds", "navigate.mp3");
		    		currSelection--;
		    		if(currSelection < 0) {
		    			currSelection = mc.getMainInventory().getMyInventory().size() - 1;
		    		}
					
					System.out.println(currSelection);
					handleItemHovering(currSelection);
					break;
					
				case KeyEvent.VK_DOWN:
					audio.playSound("sounds", "navigate.mp3");
					currSelection++;
					if (currSelection > mc.getMainInventory().getMyInventory().size() - 1) {
		    			currSelection = 0;
					}
					System.out.println(currSelection);
					handleItemHovering(currSelection);
					break;
				
		        case KeyEvent.VK_Z:
		    	  audio.playSound("sounds", "getItem.mp3");
		             {
		    	      useSelectedItem(currSelection);
		             }
		    	}
		    }
	}