package org.nwpu.cloudstorge.box;

import javax.swing.ImageIcon;

	public interface MailBox {
	/**
	 * 获得名字
	 * @return
	 */
	String getText();
	
	/**
	 * 返回对应图标
	 * @return
	 */
	ImageIcon getImageIcon();
}
