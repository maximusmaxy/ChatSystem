/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.io.File;
import java.util.List;
import javax.swing.JList;

/**
 *
 * @author Max
 */
public class ListDropListener extends DropTargetAdapter {

    private ChatClientFrame frame;

    public ListDropListener(ChatClientFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void dragExit(DropTargetEvent dte) {
        frame.getOnlineUsers().clearSelection();
    }
    
    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        int index = frame.getOnlineUsers().locationToIndex(dtde.getLocation());
        frame.getOnlineUsers().setSelectedIndex(index);
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
                    int index = frame.getOnlineUsers().locationToIndex(dtde.getLocation());
                    for (File file : files) {
                        frame.sendImage(file, index);
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
