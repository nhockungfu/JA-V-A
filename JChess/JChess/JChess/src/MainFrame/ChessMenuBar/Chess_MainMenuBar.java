/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import MainFrame.ChessMenuBar.ChessBar_Menus.File_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Edit_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Tools_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Help_Menu;
import MainFrame.ChessFrame.MainFrame;
/**
 *
 * @author sami
 */
public class Chess_MainMenuBar extends JMenuBar
{
    
    /** Creates a new instance of Chess_MainMenuBar */
    public Chess_MainMenuBar(MainFrame ff)
    {
        Fmenu=new File_Menu(ff);
        add(Fmenu);
        add(Emenu);
        add(Tmenu);
        add(Hmenu);
        
    }
    public String getIpAddress()
    {
        return Fmenu.getIPaddress();
    }
    public String getPortnumber()
    {
        return Fmenu.getportNumber();
    }
    
    private final File_Menu Fmenu;
    private final Edit_Menu Emenu=new Edit_Menu();
    private final Tools_Menu Tmenu=new Tools_Menu();
    private final Help_Menu Hmenu=new Help_Menu();
    /*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
}
