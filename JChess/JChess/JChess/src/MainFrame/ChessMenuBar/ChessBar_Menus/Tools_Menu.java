 /*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus;

import javax.swing.JMenu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Tool_MenuItems.Options;
/**
 *
 * @author sami
 */
public class Tools_Menu extends JMenu
{
    
    /** Creates a new instance of Tools_Menu */
    public Tools_Menu()
    {
        setText("Tools");
        add(GameOptions);
    }
    
    private final Options GameOptions=new Options();
}
/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */