/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.File_MenuItems;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
/**
 *
 * @author sami
 */
public class Load_Item  extends JMenuItem
{
    
    /** Creates a new instance of Load_Item */
    public Load_Item()
    {
        setText("Load Game");
        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LoadFile.showOpenDialog(null);
            }
        });
    }
    
    /*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
    private final JFileChooser LoadFile=new JFileChooser();
}
