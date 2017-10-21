
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Max
 */
public class LabelDropListener extends DropTarget {
        private ChatClientFrame frame;

    public LabelDropListener(ChatClientFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);
        Transferable transferable = dtde.getTransferable();
        DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
        for (DataFlavor df : dataFlavors) {
            try {
                if (df.isFlavorJavaFileListType()) {
                    List<File> files = (List<File>) transferable.getTransferData(df);
                    for (File file : files) {
                        BufferedImage img = frame.createImage(file);
                        frame.displayImage(img);
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
}
