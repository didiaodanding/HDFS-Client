package org.nwpu.cloudstorge.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MailTableCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable arg0, Object value,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		//判断该单元格的值是否图片
		if (value instanceof Icon) {
			this.setIcon((Icon)value);
		} else {
			this.setText(value.toString());
		}
		return this;
	}
}
