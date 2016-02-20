

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

public class DndTest extends DropTargetAdapter {
	public JFrame jframe;
	public JList jlist;
	public DefaultListModel dlm;

	public DndTest() {
		jframe = new JFrame("Drag and Drop Sample");
		dlm = new DefaultListModel();
		jlist = new JList(dlm);
		jlist.setDropTarget(new DropTarget(jlist, DnDConstants.ACTION_REFERENCE, this, true));
		jframe.add(jlist);
		jframe.setSize(350, 300);
		jframe.setLocationRelativeTo(null);
		jframe.setAlwaysOnTop(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
	}

	public static void main(String[] args) {
		new DndTest();
	}

	@SuppressWarnings("unchecked")
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_REFERENCE);
		Transferable tf = dtde.getTransferable();
		try {
			List<File> list = (List<File>) tf.getTransferData(DataFlavor.javaFileListFlavor);
			for (File f : list) {
				dlm.addElement(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
