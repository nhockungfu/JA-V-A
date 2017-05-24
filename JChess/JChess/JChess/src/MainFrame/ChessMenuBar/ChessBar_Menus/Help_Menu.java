 /*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus;

import javax.swing.JMenu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Help_MenuItems.About_Chess_Game;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Help_MenuItems.About_Game_Programmers;
/**
 *
 * @author sami
 */
public class Help_Menu extends JMenu
{
    
    /** Creates a new instance of Help_Menu */
    public Help_Menu()
    {
        setText("Help");
        add(AboutGame);
        add(AboutUs);
    }
    
    private final About_Chess_Game AboutGame=new About_Chess_Game();
    private final About_Game_Programmers AboutUs=new About_Game_Programmers();
    
}/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
