package org.nwpu.cloudstorge.box;

import javax.swing.ImageIcon;

public class DeletedBox extends AbstractBox {
private ImageIcon icon;
	
	public String getText() {
		return "À¬»øÏä";
	}

	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/deleted-tree.gif");
	}
}
