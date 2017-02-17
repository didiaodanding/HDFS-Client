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

	//�û���
	private JLabel userLabel = new JLabel("�û�����");	
	private JTextField userField = new JTextField(20);
	//ȷ����ť
	private JButton confirmButton = new JButton("ȷ��");
	//ȡ����ť
	private JButton cancelButton = new JButton("ȡ��");
	//��ťBox
	private Box buttonBox = Box.createHorizontalBox();
	//�û�Box
	private Box userBox = Box.createHorizontalBox();
	//����Box
	private Box mainBox = Box.createVerticalBox();
	//ϵͳ������
	private MainFrame mainFrame;
	
	public LoginFrame() {
		this.userBox.add(Box.createHorizontalStrut(30));
		this.userBox.add(userLabel);
		this.userBox.add(Box.createHorizontalStrut(20));
		this.userBox.add(userField);
		this.userBox.add(Box.createHorizontalStrut(30));
		
		//��ť��Box
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
		this.setTitle("���̿ͻ���");
		initListener();
	}
	//��ʼ��������
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
				JOptionPane.showConfirmDialog(this, "�������û���", "����", 
						JOptionPane.OK_CANCEL_OPTION);
				return;
			}
			//����ϵͳ����������
			this.mainFrame = new MainFrame();
			this.mainFrame.setVisible(true);
			this.setVisible(false);
		}
}
