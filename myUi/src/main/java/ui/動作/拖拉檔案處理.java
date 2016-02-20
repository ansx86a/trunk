package ui.動作;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 先是列印檔案
 * 
 * @author rockx
 *
 */
public class 拖拉檔案處理 extends DropTargetAdapter {

	private ArrayList<File> list;
	/* 檔案處理方式，可用列舉 */

	@Override
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_REFERENCE);
		Transferable tf = dtde.getTransferable();
		try {
			List<File> list0 = (List<File>) tf.getTransferData(DataFlavor.javaFileListFlavor);
			list = new ArrayList(list0);
			for (File f : list) {
				System.out.println(f.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<File> getList() {
		return list;
	}

	public void 設定啟用拖拉的容器(Component c) {
		c.setDropTarget(new DropTarget(c,DnDConstants.ACTION_REFERENCE,this,true));
	}

}
