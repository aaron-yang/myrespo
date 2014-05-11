package com.jframe.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class LoanCalculator extends JFrame {
	private JTextField annualInterestRate = new JTextField();
	private JTextField numberOfYears = new JTextField();
	private JTextField loanAmount = new JTextField();
	private JTextField monthlyPayment = new JTextField();
	private JTextField totalPayment = new JTextField();
	private JButton compute = new JButton("计算");
	
	public LoanCalculator(){
		JPanel p1 = new JPanel(new GridLayout(5,2));
		p1.add(new JLabel("贷款利率"));
		p1.add(annualInterestRate);
		p1.add(new JLabel("贷款年限"));
		p1.add(numberOfYears);
		p1.add(new JLabel("贷款金额"));
		p1.add(loanAmount);
		p1.add(new JLabel("每月月供"));
		p1.add(monthlyPayment);
		p1.add(new JLabel("累计还款总额"));
		p1.add(totalPayment);
		p1.setBorder(new TitledBorder("输入贷款金额，贷款利率，借款年数"));
		
		JPanel p2  = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(compute);
		
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
		compute.addActionListener(new ButtonListener());
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			double interest = Double.parseDouble(annualInterestRate.getText().toString());
			int year = Integer.parseInt(numberOfYears.getText().toString());
			double amount = Double.parseDouble(loanAmount.getText().toString());
			Loan loan = new Loan(interest,year,amount);
			monthlyPayment.setText(String.format("%.2f", loan.getMonthlyPayment()));
			totalPayment.setText(String.format("%.2f", loan.getTotalPayment()));
		}
		
	}
	
	public static void main(String[] args){
		LoanCalculator frame = new LoanCalculator();
		frame.pack();
		frame.setTitle("贷款计算器");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
