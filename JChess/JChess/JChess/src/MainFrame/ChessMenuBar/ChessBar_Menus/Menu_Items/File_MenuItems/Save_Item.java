/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.File_MenuItems;

import MainFrame.ChessFrame.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import MainFrame.ChessFrame.MainFrame;

/**
 *
 * @author sami
 */
public class Save_Item extends JMenuItem
{
    
    /** Creates a new instance of Save_Item */
    public Save_Item()
    {
        setText("Save Game");
        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SaveFile.showSaveDialog(null);
                
            }
        });
    }
    
    private final  JFileChooser SaveFile=new JFileChooser();
}/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
