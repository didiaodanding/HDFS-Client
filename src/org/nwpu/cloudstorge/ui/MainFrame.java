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

	//欢迎的JLabel
	private JLabel welcome = new JLabel("欢迎您：");
	//分隔左边的树与右边邮件信息的JSplitPane
	private JSplitPane mailSplitPane;
	//右边邮件列表与邮件信息的JSplitPane
	private JSplitPane mailListInfoPane;
	//邮件详细信息的JSplitPane, 左边是邮件信息, 右边是附件
	private JSplitPane mailInfoPane;
	//邮件列表的JTable
	private MailListTable mailListTable;
	//存放列表的的JScrollPane
	private JScrollPane tablePane;
	//邮件导航树的JScrollPane
	private JScrollPane treePane;
	//邮件导航树
	private JTree tree;
	//邮件显示JTextArea
	private JTextArea mailTextArea = new JTextArea(10, 80);
	//邮件显示的JScrollPane, 存放显示邮件的JTextArea
	private JScrollPane mailScrollPane;
	//邮件附件列表
	private JScrollPane filePane;
	//邮件附件名称显示
	private JList fileList;
	//工具栏
	private JToolBar toolBar = new JToolBar();
	
	//收件箱的Mail对象集合，代表所有在收件箱中的邮件
	private List<Mail> inMails;
	//发件箱的邮件集合
	private List<Mail> outMails;
	//成功发送的邮件集合
	private List<Mail> sentMails;
	//草稿箱的邮件集合
	private List<Mail> draftMails;
	//垃圾箱的邮件集合
	private List<Mail> deleteMails;
	//当前界面列表所显示的对象
	private List<Mail> currentMails;
	
	//邮箱加载对象
	private MailLoader mailLoader = new MailLoaderImpl();
	//发送邮件对象
	private MailSender mailSender = new MailSenderImpl();
	//当前打开的文件对象
	private Mail currentMail;
	//接收邮件的间隔, 单位毫秒
	private long receiveInterval = 1000 * 10;
	
	//收取邮件
	private Action in = new AbstractAction("收取邮件", new ImageIcon("images/in.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//发送邮件
	private Action sent = new AbstractAction("发送邮件", new ImageIcon("images/out.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//写邮件
	private Action write = new AbstractAction("写邮件", new ImageIcon("images/new.gif")) {
		public void actionPerformed(ActionEvent e) {
					}
	};
	
	//回复邮件
	private Action reply = new AbstractAction("回复邮件", new ImageIcon("images/reply.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//回复邮件
	private Action transmit = new AbstractAction("转发邮件", new ImageIcon("images/transmit.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//删除邮件, 放进垃圾箱
	private Action delete = new AbstractAction("删除邮件", new ImageIcon("images/delete.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	//彻底删除邮件
	private Action realDelete = new AbstractAction("彻底删除邮件", new ImageIcon("images/real-delete.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	//从垃圾箱中还原邮件
	private Action revert = new AbstractAction("还原邮件", new ImageIcon("images/revert.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	//设置
	private Action setup = new AbstractAction("设置", new ImageIcon("images/setup.gif")) {
		public void actionPerformed(ActionEvent e) {
		
		}
	};
	
	public MainFrame() {
		//初始化各个列表集合
		initMails();
		//设置当前显示的邮件集合为收件箱的集合
		this.currentMails = this.inMails;
		//创建邮件导航树
		this.tree = createTree();
		//邮件列表JTable
		DefaultTableModel tableMode = new DefaultTableModel();
		this.mailListTable = new MailListTable(tableMode);
		tableMode.setDataVector(createViewDatas(this.currentMails), getListColumn());
		//设置邮件列表的样式
		setTableFace();
		this.tablePane = new JScrollPane(this.mailListTable);
		this.tablePane.setBackground(Color.WHITE);
		//邮件附件列表
		this.fileList = new JList();
		this.fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//	this.fileList.addMouseListener(new MainListMouseListener());
		this.filePane = new JScrollPane(fileList);
		this.mailTextArea.setLineWrap(true);
		this.mailTextArea.setEditable(false);
		this.mailTextArea.setFont(new Font(null, Font.BOLD, 14));
		//显示邮件内容的JScrollPane
		this.mailScrollPane =  new JScrollPane(this.mailTextArea);
		//邮件的信息
		this.mailInfoPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.filePane, this.mailScrollPane);
		this.mailInfoPane.setDividerSize(3);
		this.mailInfoPane.setDividerLocation(80);
		//邮件列表和邮件信息的JSplitPane
		this.mailListInfoPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				this.tablePane, mailInfoPane);
		this.mailListInfoPane.setDividerLocation(300);
		this.mailListInfoPane.setDividerSize(20);
		
		//树的JScrollPane
		this.treePane = new JScrollPane(this.tree);
		//主整个邮件界面的JSplitPane
		this.mailSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.treePane, this.mailListInfoPane);
		this.mailSplitPane.setDividerLocation(150);
		this.mailSplitPane.setDividerSize(3);
		//设置用户邮箱地址的显示
		this.welcome.setText(this.welcome.getText());
		//创建工具栏
		createToolBar();
		//设置JFrame的各个属性
		this.add(mailSplitPane);
		this.setTitle("邮件收发客户端");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		initListeners();
	}
	
	//获得列表中所选行的xmlName列的值（该值是唯一的）
	private String getSelectXmlName() {
		int row = this.mailListTable.getSelectedRow();
		int column = this.mailListTable.getColumn("xmlName").getModelIndex();
		if (row == -1) return null;
		String xmlName = (String)this.mailListTable.getValueAt(row, column);
		return xmlName;
	}
	
	//查看一封邮件
	private void viewMail() {
		this.mailTextArea.setText("");
		Mail mail = getSelectMail();
		this.mailTextArea.append("发送人：  " + mail.getSender());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("抄送：  " + mail.getCCString());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("收件人:   " + mail.getReceiverString());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("主题：  " + mail.getSubject());
		this.mailTextArea.append("\n");
		this.mailTextArea.append("接收日期：  " + dateFormat.format(mail.getReceiveDate()));
		this.mailTextArea.append("\n\n");
		this.mailTextArea.append("邮件正文：  ");
		this.mailTextArea.append("\n\n");
		this.mailTextArea.append(mail.getContent());
		//添加附件
		this.fileList.setListData(mail.getFiles().toArray());
		//设置当前被打开的邮件对象
		this.currentMail = mail;
		//如果邮件没有被查看过，就修改图标，并保存已经打开的状态
		if (!mail.getHasRead()) {
			//设置邮件已经被查看
			mail.setHasRead(true);
			//更新信封图标
	//		openEnvelop();
		}
	}
		
	//获取在列表中所选择的Mail对象
	private Mail getSelectMail() {
		String xmlName = getSelectXmlName();
		return getMail(xmlName, this.currentMails);
	}
	
	//从集合中找到xmlName与参数一致的Mail对象
	private Mail getMail(String xmlName, List<Mail> mails) {
		for (Mail m : mails) {
			if (m.getXmlName().equals(xmlName))return m;
		}
		return null;
	}
	
	private void initListeners() {
		//列表选择监听器
		this.mailListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				//当选择行时鼠标释放时才执行
				if (!event.getValueIsAdjusting()) {
					//如果没有选中任何一行, 则返回
					if (mailListTable.getSelectedRowCount() != 1) return;
					viewMail();
				}
			}
		});
	}
	
	//初始化时创建各个box中的数据
	private void initMails() {
		this.inMails =  new ArrayList<Mail>()  ;
		this.draftMails =  new ArrayList<Mail>();
		this.deleteMails =  new ArrayList<Mail>();
		this.outMails =  new ArrayList<Mail>();
		this.sentMails =  new ArrayList<Mail>();
	}
	
	//创建导航的树
	private JTree createTree() {
		//创建根节点
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		//加入各个子节点
		root.add(new DefaultMutableTreeNode(new InBox()));
		root.add(new DefaultMutableTreeNode(new OutBox()));
		root.add(new DefaultMutableTreeNode(new SentBox()));
		root.add(new DefaultMutableTreeNode(new DraftBox()));
		root.add(new DefaultMutableTreeNode(new DeletedBox()));
		//创建树
		JTree tree = new JTree(root);
		//加入鼠标监听器
		tree.addMouseListener(new SailTreeListener(this));
		//隐藏根节点
		tree.setRootVisible(false);
		//设置节点处理类
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
		//刷新列表
		refreshTable();
		//设置当前打开的邮件对象为空并清空组件
		cleanMailInfo();
	}
	
	//获得当前选中的box
	private MailBox getSelectBox() {
		TreePath treePath = this.tree.getSelectionPath();
		if (treePath == null) return null;
		//获得选中的TreeNode
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		return (MailBox)node.getUserObject();
	}
	
	//刷新列表的方法, 参数是不同的数据
	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel)this.mailListTable.getModel();
		tableModel.setDataVector(createViewDatas(this.currentMails), getListColumn());
		setTableFace();
	}
	
	private Object[] emptyListData = new Object[]{};
	
	//清空当前打开的邮件及对应的界面组件
	public void cleanMailInfo() {
		//设置当前打开的邮件对象为空
		this.currentMail = null;
		this.mailTextArea.setText("");
		this.fileList.setListData(this.emptyListData);
	}
	
	
	//没有查看的邮件显示关闭的信封的图片地址
	private static String CLOSE_ENVELOP_PATH = "images/envelop-close.gif";
	//已经查看的邮件显示打开的信封的图片地址
	private static String OPEN_ENVELOP_PATH = "images/envelop-open.gif";
	//信封打开的Icon对象
	private ImageIcon envelopOpen = new ImageIcon(OPEN_ENVELOP_PATH);
	//信封关闭的Icon对象
	private ImageIcon envelopClose = new ImageIcon(CLOSE_ENVELOP_PATH);
	//时间格式对象
	private DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	
	//将邮件数据集合转换成视图的格式
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
	
	//格式时间
	private String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	//获得邮件列表的列名
	@SuppressWarnings("unchecked")
	private Vector getListColumn() {
		Vector columns = new Vector();
		columns.add("xmlName");
		columns.add("打开");
		columns.add("发件人");
		columns.add("主题");
		columns.add("日期");
		columns.add("大小");
		return columns;
	}
	
	//设置邮件列表的样式
	private void setTableFace() {
		//隐藏邮件对应的xml文件的名字
		this.mailListTable.getColumn("xmlName").setMinWidth(0);
		this.mailListTable.getColumn("xmlName").setMaxWidth(0);
		this.mailListTable.getColumn("打开").setCellRenderer(new MailTableCellRenderer());
		this.mailListTable.getColumn("打开").setMaxWidth(40);
		this.mailListTable.getColumn("发件人").setMinWidth(200);
		this.mailListTable.getColumn("主题").setMinWidth(320);
		this.mailListTable.getColumn("日期").setMinWidth(130);
		this.mailListTable.getColumn("大小").setMinWidth(80);
		this.mailListTable.setRowHeight(30);
	}
	
	//初始化工具栏
	private void createToolBar() {
		this.toolBar.add(this.in).setToolTipText("收取邮件");
		this.toolBar.add(this.sent).setToolTipText("发送邮件");
		this.toolBar.add(this.write).setToolTipText("写邮件");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.reply).setToolTipText("回复邮件");
		this.toolBar.add(this.transmit).setToolTipText("转发邮件");
		this.toolBar.add(this.delete).setToolTipText("删除邮件");
		this.toolBar.add(this.realDelete).setToolTipText("彻底删除邮件");
		this.toolBar.add(this.revert).setToolTipText("还原邮件");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.setup).setToolTipText("设置");
		
		this.toolBar.addSeparator(new Dimension(50, 0));
		this.toolBar.add(this.welcome);
		this.toolBar.setFloatable(false);//设置工具栏不可移动
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));//设置工具栏的边距
		this.add(this.toolBar, BorderLayout.NORTH);
	}
}
