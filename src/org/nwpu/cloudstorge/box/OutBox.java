package org.nwpu.cloudstorge.box;

import javax.swing.ImageIcon;

public class OutBox extends AbstractBox {
	public String getText() {
		return "������";
	}

	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/out-tree.gif");
	}
}
