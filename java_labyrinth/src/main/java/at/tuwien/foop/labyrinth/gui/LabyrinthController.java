package at.tuwien.foop.labyrinth.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import at.tuwien.foop.labyrinth.model.Mouse;


public class LabyrinthController {

	private LabyrinthModel model;
	private LabyrinthView view;
	private ActionListener al;

	public LabyrinthController(LabyrinthModel model, LabyrinthView view){
		this.model = model;
		this.view = view;
	}
	
	public void control(){
		System.out.println("lala");
	//	view.getDoorButton().addMouseListener(new Ml());
	}

	public class Ml extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
		//	System.out.println("Door clicked: " + view.getDoorButton().getName());
		}
	}
	
	
//	doorButton.addActionListener(new ActionListener(){  
//		public void actionPerformed(ActionEvent e) {  
//			doorButton.setIcon(doorButton.getIcon() == closedDoorImg ? openedDoorImg : closedDoorImg);
//		}  
//	}); 

//	public void contol(){        
//		actionListener = new ActionListener() {
//			public void actionPerformed(ActionEvent actionEvent) {                  
//				linkBtnAndLabel();
//			}
//		};                
//		view.getButton().addActionListener(actionListener);   
//	}
//
//	private void linkBtnAndLabel(){
//		model.incX();                
//		view.setText(Integer.toString(model.getX()));
//	}    
}



