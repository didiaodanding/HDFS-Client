package org.nwpu.cloudstorge.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

public class MailListTable extends JTable {
	public MailListTable(TableModel dm) {
		super(dm);
		//ֻ��ѡ��һ��
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//ȥ��������
		setShowHorizontalLines(false);
		setShowVerticalLines(false);
	}
	//���б��ɱ༭
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
