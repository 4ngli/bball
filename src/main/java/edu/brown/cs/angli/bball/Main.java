package edu.brown.cs.angli.bball;

import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			BouncingBallEx ex = new BouncingBallEx();
			ex.setVisible(true);
		});
	}
}
