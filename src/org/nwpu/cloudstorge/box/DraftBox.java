package org.nwpu.cloudstorge.box;

import javax.swing.ImageIcon;

public class DraftBox extends AbstractBox{
	public String getText() {
		return "≤›∏Âœ‰";
	}

	public ImageIcon getImageIcon() {
		return super.getImageIcon("images/draft-tree.gif");
	}
}
