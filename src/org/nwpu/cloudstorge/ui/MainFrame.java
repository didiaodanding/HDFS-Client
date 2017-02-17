package org.nwpu.cloudstorge.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.nwpu.cloudstorge.box.DeletedBox;
import org.nwpu.cloudstorge.box.DraftBox;
import org.nwpu.cloudstorge.box.InBox;
import org.nwpu.cloudstorge.box.MailBox;
import org.nwpu.cloudstorge.box.OutBox;
import org.nwpu.cloudstorge.box.SentBox;
import org.nwpu.cloudstorge.dao.MailLoader;
import org.nwpu.cloudstorge.dao.MailLoaderImpl;
import org.nwpu.cloudstorge.dao.MailSender;
import org.nwpu.cloudstorge.dao.MailSenderImpl;
import org.nwpu.cloudstorge.object.Mail;


public class MainFrame extends JFrame {

	//��ӭ��JLabel
	private JLabel welcome = new JLabel("��ӭ����");
	//�ָ���ߵ������ұ��ʼ���Ϣ��JSplitPane
	private JSplitPane mailSplitPane;
	//�ұ��ʼ��б����ʼ���Ϣ��JSplitPane
	private JSplitPane mailListInfoPane;
	//�ʼ���ϸ��Ϣ��JSplitPane, ������ʼ���Ϣ, �ұ��Ǹ���
	private JSplitPane mailInfoPane;
	//�ʼ��б��JTable
	private MailListTable mailListTable;
	//����б�ĵ�JScrollPane
	private JScrollPane tablePane;
	//�ʼ���������JScrollPane
	private JScrollPane treePane;
	//�ʼ�������
	private JTree tree;
	//�ʼ���ʾJTextArea
	private JTextArea mailTextArea = new JTextArea(10, 80);
	//�ʼ���ʾ��JScrollPane, �����ʾ�ʼ���JTextArea
	private JScrollPane mailScrollPane;
	//�ʼ������б�
	private JScrollPane filePane;
	//�ʼ�����������ʾ
	private JList fileList;
	//������
	private JToolBar toolBar = new JToolBar();
	
	//�ռ����Mail���󼯺ϣ������������ռ����е��ʼ�
	private List<Mail> inMails;
	//��������ʼ�����
	private List<Mail> outMails;
	//�ɹ����͵��ʼ�����
	private List<Mail> sentMails;
	//�ݸ�����ʼ�����
	private List<Mail> draftMails;
	//��������ʼ�����
	private List<Mail> deleteMails;
	//��ǰ�����б�����ʾ�Ķ���
	private List<Mail> currentMails;
	
	//������ض���
	private MailLoader mailLoader = new MailLoaderImpl();
	//�����ʼ�����
	private MailSender mailSender = new MailSenderImpl();
	//��ǰ�򿪵��ļ�����
	private Mail currentMail;
	//�����ʼ��ļ��, ��λ����
	private long receiveInterval = 1000 * 10;
	
	//��ȡ�ʼ�
	private Action in = new AbstractAction("��ȡ�ʼ�", new ImageIcon("images/in.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//�����ʼ�
	private Action sent = new AbstractAction("�����ʼ�", new ImageIcon("images/out.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//д�ʼ�
	private Action write = new AbstractAction("д�ʼ�", new ImageIcon("images/new.gif")) {
		public void actionPerformed(ActionEvent e) {
					}
	};
	
	//�ظ��ʼ�
	private Action reply = new AbstractAction("�ظ��ʼ�", new ImageIcon("images/reply.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//�ظ��ʼ�
	private Action transmit = new AbstractAction("ת���ʼ�", new ImageIcon("images/transmit.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//ɾ���ʼ�, �Ž�������
	private Action delete = new AbstractAction("ɾ���ʼ�", new ImageIcon("images/delete.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//����ɾ���ʼ�
	private Action realDelete = new AbstractAction("����ɾ���ʼ�", new ImageIcon("images/real-delete.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	//���������л�ԭ�ʼ�
	private Action revert = new AbstractAction("��ԭ�ʼ�", new ImageIcon("images/revert.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	//����
	private Action setup = new AbstractAction("����", new ImageIcon("images/setup.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	public MainFrame() {
		//��ʼ�������б���
		initMails();
		//���õ�ǰ��ʾ���ʼ�����Ϊ�ռ���ļ���
		this.currentMails = this.inMails;
		//�����ʼ�������
		this.tree = createTree();
		//�ʼ��б�JTable
		DefaultTableModel tableMode = new DefaultTableModel();
		this.mailListTable = new MailListTable(tableMode);
		tableMode.setDataVector(createViewDatas(this.currentMails), getListColumn());
		//�����ʼ��б����ʽ
		setTableFace();
		this.tablePane = new JScrollPane(this.mailListTable);
		this.tablePane.setBackground(Color.WHITE);
		//�ʼ������б�
		this.fileList = new JList();
		this.fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//	this.fileList.addMouseListener(new MainListMouseListener());
		this.filePane = new JScrollPane(fileList);
		this.mailTextArea.setLineWrap(true);
		this.mailTextArea.setEditable(false);
		this.mailTextArea.setFont(new Font(null, Font.BOLD, 14));
		//��ʾ�ʼ����ݵ�JScrollPane
		this.mailScrollPane =  new JScrollPane(this.mailTextArea);
		//�ʼ�����Ϣ
		this.mailInfoPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.filePane, this.mailScrollPane);
		this.mailInfoPane.setDividerSize(3);
		this.mailInfoPane.setDividerLocation(80);
		//�ʼ��б���ʼ���Ϣ��JSplitPane
		this.mailListInfoPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				this.tablePane, mailInfoPane);
		this.mailListInfoPane.setDividerLocation(300);
		this.mailListInfoPane.setDividerSize(20);
		
		//����JScrollPane
		this.treePane = new JScrollPane(this.tree);
		//�������ʼ������JSplitPane
		this.mailSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.treePane, this.mailListInfoPane);
		this.mailSplitPane.setDividerLocation(150);
		this.mailSplitPane.setDividerSize(3);
		//�����û������ַ����ʾ
		this.welcome.setText(this.welcome.getText());
		//����������
		createToolBar();
		//����JFrame�ĸ�������
		this.add(mailSplitPane);
		this.setTitle("�ʼ��շ��ͻ���");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		initListeners();
	}
	
	//����б�����ѡ�е�xmlName�е�ֵ����ֵ��Ψһ�ģ�
	private String getSelectXmlName() {
		int row = this.mailListTable.getSelectedRow();
		int column = this.mailListTable.getColumn("xmlName").getModelIndex();
		if (row == -1) return null;
		String xmlName = (String)this.mailListTable.getValueAt(row, column);
		return xmlName;
	}
	
	//�鿴һ���ʼ�
	private void viewMail() {
		this.mailTextArea.setText("");
		Mail mail = getSelectMail();
		this.mailTextArea.append("�����ˣ�  " + mail.getSender());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("���ͣ�  " + mail.getCCString());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("�ռ���:   " + mail.getReceiverString());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("���⣺  " + mail.getSubject());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("�������ڣ�  " + dateFormat.format(mail.getReceiveDate()));
		this.mailTextArea.append("\n\n");
		this.mailTextArea.append("�ʼ����ģ�  ");
		this.mailTextArea.append("\n\n");
		this.mailTextArea.append(mail.getContent());
		//��Ӹ���
		this.fileList.setListData(mail.getFiles().toArray());
		//���õ�ǰ���򿪵��ʼ�����
		this.currentMail = mail;
		//����ʼ�û�б��鿴�������޸�ͼ�꣬�������Ѿ��򿪵�״̬
		if (!mail.getHasRead()) {
			//�����ʼ��Ѿ����鿴
			mail.setHasRead(true);
			//�����ŷ�ͼ��
	//		openEnvelop();
		}
	}
		
	//��ȡ���б�����ѡ���Mail����
	private Mail getSelectMail() {
		String xmlName = getSelectXmlName();
		return getMail(xmlName, this.currentMails);
	}
	
	//�Ӽ������ҵ�xmlName�����һ�µ�Mail����
	private Mail getMail(String xmlName, List<Mail> mails) {
		for (Mail m : mails) {
			if (m.getXmlName().equals(xmlName))return m;
		}
		return null;
	}
	
	private void initListeners() {
		//�б�ѡ�������
		this.mailListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				//��ѡ����ʱ����ͷ�ʱ��ִ��
				if (!event.getValueIsAdjusting()) {
					//���û��ѡ���κ�һ��, �򷵻�
					if (mailListTable.getSelectedRowCount() != 1) return;
					viewMail();
				}
			}
		});
	}
	
	//��ʼ��ʱ��������box�е�����
	private void initMails() {
		this.inMails =  new ArrayList<Mail>()  ;
		this.draftMails =  new ArrayList<Mail>();
		this.deleteMails =  new ArrayList<Mail>();
		this.outMails =  new ArrayList<Mail>();
		this.sentMails =  new ArrayList<Mail>();
	}
	
	//������������
	private JTree createTree() {
		//�������ڵ�
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		//��������ӽڵ�
		root.add(new DefaultMutableTreeNode(new InBox()));
		root.add(new DefaultMutableTreeNode(new OutBox()));
		root.add(new DefaultMutableTreeNode(new SentBox()));
		root.add(new DefaultMutableTreeNode(new DraftBox()));
		root.add(new DefaultMutableTreeNode(new DeletedBox()));
		//������
		JTree tree = new JTree(root);
		//������������
		tree.addMouseListener(new SailTreeListener(this));
		//���ظ��ڵ�
		tree.setRootVisible(false);
		//���ýڵ㴦����
		SailTreeCellRenderer cellRenderer = new SailTreeCellRenderer();
		tree.setCellRenderer(cellRenderer);
		return tree;
	}
	
	public void select() {
		MailBox box = getSelectBox();
		if (box instanceof InBox) {
			this.currentMails = this.inMails;
		} else if (box instanceof OutBox) {
			this.currentMails = this.outMails;
		} else if (box instanceof SentBox) {
			this.currentMails = this.sentMails;
		} else if (box instanceof DraftBox) {
			this.currentMails = this.draftMails;
		} else {
			this.currentMails = this.deleteMails;
		}
		//ˢ���б�
		refreshTable();
		//���õ�ǰ�򿪵��ʼ�����Ϊ�ղ�������
		cleanMailInfo();
	}
	
	//��õ�ǰѡ�е�box
	private MailBox getSelectBox() {
		TreePath treePath = this.tree.getSelectionPath();
		if (treePath == null) return null;
		//���ѡ�е�TreeNode
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		return (MailBox)node.getUserObject();
	}
	
	//ˢ���б�ķ���, �����ǲ�ͬ������
	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel)this.mailListTable.getModel();
		tableModel.setDataVector(createViewDatas(this.currentMails), getListColumn());
		setTableFace();
	}
	
	private Object[] emptyListData = new Object[]{};
	
	//��յ�ǰ�򿪵��ʼ�����Ӧ�Ľ������
	public void cleanMailInfo() {
		//���õ�ǰ�򿪵��ʼ�����Ϊ��
		this.currentMail = null;
		this.mailTextArea.setText("");
		this.fileList.setListData(this.emptyListData);
	}
	
	
	//û�в鿴���ʼ���ʾ�رյ��ŷ��ͼƬ��ַ
	private static String CLOSE_ENVELOP_PATH = "images/envelop-close.gif";
	//�Ѿ��鿴���ʼ���ʾ�򿪵��ŷ��ͼƬ��ַ
	private static String OPEN_ENVELOP_PATH = "images/envelop-open.gif";
	//�ŷ�򿪵�Icon����
	private ImageIcon envelopOpen = new ImageIcon(OPEN_ENVELOP_PATH);
	//�ŷ�رյ�Icon����
	private ImageIcon envelopClose = new ImageIcon(CLOSE_ENVELOP_PATH);
	//ʱ���ʽ����
	private DateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
	
	//���ʼ����ݼ���ת������ͼ�ĸ�ʽ
	@SuppressWarnings("unchecked")
	private Vector<Vector> createViewDatas(List<Mail> mails) {
		Vector<Vector> views = new Vector<Vector>();
		for (Mail mail : mails) {
			Vector view = new Vector();
			view.add(mail.getXmlName());
			if (mail.getHasRead()) view.add(envelopOpen);
			else view.add(envelopClose);
			view.add(mail.getSender());
			view.add(mail.getSubject());
			view.add(formatDate(mail.getReceiveDate()));
			view.add(mail.getSize() + "k");
			views.add(view);
		}
		return views;
	}
	
	//��ʽʱ��
	private String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	//����ʼ��б������
	@SuppressWarnings("unchecked")
	private Vector getListColumn() {
		Vector columns = new Vector();
		columns.add("xmlName");
		columns.add("��");
		columns.add("������");
		columns.add("����");
		columns.add("����");
		columns.add("��С");
		return columns;
	}
	
	//�����ʼ��б����ʽ
	private void setTableFace() {
		//�����ʼ���Ӧ��xml�ļ�������
		this.mailListTable.getColumn("xmlName").setMinWidth(0);
		this.mailListTable.getColumn("xmlName").setMaxWidth(0);
		this.mailListTable.getColumn("��").setCellRenderer(new MailTableCellRenderer());
		this.mailListTable.getColumn("��").setMaxWidth(40);
		this.mailListTable.getColumn("������").setMinWidth(200);
		this.mailListTable.getColumn("����").setMinWidth(320);
		this.mailListTable.getColumn("����").setMinWidth(130);
		this.mailListTable.getColumn("��С").setMinWidth(80);
		this.mailListTable.setRowHeight(30);
	}
	
	//��ʼ��������
	private void createToolBar() {
		this.toolBar.add(this.in).setToolTipText("��ȡ�ʼ�");
		this.toolBar.add(this.sent).setToolTipText("�����ʼ�");
		this.toolBar.add(this.write).setToolTipText("д�ʼ�");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.reply).setToolTipText("�ظ��ʼ�");
		this.toolBar.add(this.transmit).setToolTipText("ת���ʼ�");
		this.toolBar.add(this.delete).setToolTipText("ɾ���ʼ�");
		this.toolBar.add(this.realDelete).setToolTipText("����ɾ���ʼ�");
		this.toolBar.add(this.revert).setToolTipText("��ԭ�ʼ�");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.setup).setToolTipText("����");
		
		this.toolBar.addSeparator(new Dimension(50, 0));
		this.toolBar.add(this.welcome);
		this.toolBar.setFloatable(false);//���ù����������ƶ�
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));//���ù������ı߾�
		this.add(this.toolBar, BorderLayout.NORTH);
	}
}
