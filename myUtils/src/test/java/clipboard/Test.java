package clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class Test {

	public static void main(String[] args) throws Exception {
		Clipboard c=	Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t =c.getContents(null);
		System.out.println(t);
		for(DataFlavor d:t.getTransferDataFlavors()){
			System.out.println(d.getHumanPresentableName());
			Object o =t.getTransferData(DataFlavor.javaFileListFlavor);
			System.out.println(o);
			System.out.println(o.getClass());
		}
		
		
		System.out.println(t.isDataFlavorSupported(DataFlavor.stringFlavor));
		
		
		System.out.println(t.getTransferData(DataFlavor.stringFlavor));
		  
	}

}
