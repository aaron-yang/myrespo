package com.jframe.test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class AdapterDemo extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdapterDemo demo = new AdapterDemo();
		demo.setSize(220,80);
		demo.setLocationRelativeTo(null);
		demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		demo.setTitle("AdapterDemo");
		demo.setVisible(true);

	}
	
	public AdapterDemo(){
		 addWindowListener(new WindowAdapter() {
			 public void windowActivated(WindowEvent event){
				 System.out.println("Window activated");
			 }
		});
	}

}
