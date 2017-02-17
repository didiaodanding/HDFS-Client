package org.nwpu.cloudstorge.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class LoginFrame extends JFrame {

	//用户名
	private JLabel userLabel = new JLabel("用户名：");	
	private JTextField userField = new JTextField(20);
	//确定按钮
	private JButton confirmButton = new JButton("确定");
	//取消按钮
	private JButton cancelButton = new JButton("取消");
	//按钮Box
	private Box buttonBox = Box.createHorizontalBox();
	//用户Box
	private Box userBox = Box.createHorizontalBox();
	//最大的Box
	private Box mainBox = Box.createVerticalBox();
	//系统主界面
	private MainFrame mainFrame;
	
	public LoginFrame() {
		this.userBox.add(Box.createHorizontalStrut(30));
		this.userBox.add(userLabel);
		this.userBox.add(Box.createHorizontalStrut(20));
		this.userBox.add(userField);
		this.userBox.add(Box.createHorizontalStrut(30));
		
		//按钮的Box
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.cancelButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.userBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.buttonBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.add(mainBox);
		this.setLocation(300, 200);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("云盘客户端");
		initListener();
	}
	//初始化监听器
		private void initListener() {
			this.confirmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					confirm();
				}
			});
			this.cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
		private void confirm() {
			String user = this.userField.getText();
			if (user.trim().equals("")) {
				JOptionPane.showConfirmDialog(this, "请输入用户名", "警告", 
						JOptionPane.OK_CANCEL_OPTION);
				return;
			}
			//创建系统界面主对象
			this.mainFrame = new MainFrame();
			this.mainFrame.setVisible(true);
			this.setVisible(false);
		}
}
