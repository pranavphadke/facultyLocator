/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
 */
package facultylocator;

import javax.swing.UIManager;

/**
 *
 * @author Pranav Phadke
 */
public class WindowUtilities {
    public static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
    }
}
