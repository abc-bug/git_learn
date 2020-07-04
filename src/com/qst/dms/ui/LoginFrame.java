package com.qst.dms.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.qst.dms.entity.MatchedTransport;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("用户名 ：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(62, 65, 108, 32);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密 码  ：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(62, 110, 108, 32);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(174, 65, 144, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(174, 113, 144, 30);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new LoginListener() );

		btnNewButton.setBounds(50, 180, 81, 30);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("重置");
		btnNewButton_1.addActionListener(new ResetListener() );
		btnNewButton_1.setBounds(174, 181, 81, 28);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("注册");
		btnNewButton_2.addActionListener(new RegistListener());
		btnNewButton_2.setBounds(295, 180, 81, 29);
		panel.add(btnNewButton_2);
	}
	
	public class LoginListener implements ActionListener {
		//登录按钮
		public void actionPerformed(ActionEvent e) {
			
		    User user=new User();
			String userName = textField.getText().trim();			
			user=UserService.findUserByName(userName);
			if (user==null )
			{
				JOptionPane.showMessageDialog(null,"用户不存在！","错误提示",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				String userPassward = passwordField.getText().trim();
				if(user.getPassword().equals(userPassward))
				{
					LoginFrame.this.setVisible(false);
					new MainFrametest2();
				}
				else
				{
					JOptionPane.showMessageDialog(null,"密码错误！","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		
	}

	// 监听类，负责处理重置按钮
		public class ResetListener implements ActionListener {
			// 重写actionPerFormed()方法，重置组件内容事件处理方法
			public void actionPerformed(ActionEvent e) {
				// 清空姓名、密码内容
				textField.setText("");
				passwordField.setText("");
			
			}
		}
	
		public class RegistListener implements ActionListener {
			 
			public void actionPerformed(ActionEvent e) {
				new RegistFrame();
				
			}
		}
}