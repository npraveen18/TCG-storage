import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;


/*
COMPONENTS CLASS
This class holds all of the individual
Java Swing components that my app will
use.
Gave the components its own class. This is
slightly unconventional, and the reason I
chose to do this is because it makes
it easier to keep track of all of the
components that I am using.
This is also similar to the style we used
to code in CS180, thus another reason why
I chose to do this - familiarity
 */
public class Components {
    public static JTextField nameField;
    public static JTextField typeField;
    public static JTextField damageField;
    public static JTextField setField;
    public static JTextField variantField;


    public static JList<String> cardsList;

    public static JButton addButton;
    public static JButton removeButton;

    public static JLabel nameLabel;
    public static JLabel typeLabel;
    public static JLabel damageLabel;
    public static JLabel setLabel;
    public static JLabel variantLabel;

    public static JFrame frame;
    public static JPanel panel;

}
