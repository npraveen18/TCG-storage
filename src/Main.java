import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
/*
Main class

This holds all of the UI elements, and the save/load features.

I tried to group together all actions done to similar style
components (all labels in one place, all text fields in one
place, etc.) to increase readability.

Commit 2: I added the save button, which uses PrintWriter
to save to data file, data.txt

Commit 3: Added the load button, which uses BufferedReader to
clear the current list and populate it with content from
the data file, data.txt

Commit 4: Made the app load up the content in the data.txt
file on app startup.
 */
public static void main(String[] args) {
    ArrayList<Card> container = new ArrayList<>();
    //Use an EDT to handle UI stuff. This
    //helps future-proof this by adding room for threading
    //if implemented in the future
    SwingUtilities.invokeLater(() -> {
        //basic initialization and component creation
       Components.frame = new JFrame("Pokemon Database");
        Components.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Components.frame.setLayout(new BorderLayout());
        Components.frame.setSize(700,800);


        Components.panel = new JPanel(new FlowLayout());
        Components.lowerPanel = new JPanel(new FlowLayout());

        Components.nameLabel = new JLabel("Name: ");
        Components.typeLabel = new JLabel("Type: ");
        Components.damageLabel = new JLabel("Damage: ");
        Components.setLabel = new JLabel("Set: ");
        Components.variantLabel = new JLabel("Variant: ");


        Components.nameField = new JTextField();
        Components.nameField.setColumns(5);
        Components.typeField = new JTextField();
        Components.typeField.setColumns(5);
        Components.damageField = new JTextField();
        Components.damageField.setColumns(5);
        Components.setField = new JTextField();
        Components.setField.setColumns(5);
        Components.variantField = new JTextField();
        Components.variantField.setColumns(5);

        DefaultListModel<String> model = new DefaultListModel<>();
        Components.cardsList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(Components.cardsList);
        //basic initialization and component creation - end

        //load at start
        ArrayList<String> presentWhileStart = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("src/data.txt"))) {
            String input = in.readLine();
            while (input != null) {
                presentWhileStart.add(input);
                input = in.readLine();
            }
        } catch (IOException e) {
            //logic for this explained below
            System.out.println("Failed to load at start");
        }
        for (int i = 0; i < presentWhileStart.size(); i++) {
            String[] currentItem = presentWhileStart.get(i).split(",");
            container.add(new Card(currentItem[0], currentItem[1], currentItem[2], currentItem[3], currentItem[4]));
        }
        for (int i = 0; i < container.size(); i++) {
            model.addElement(container.get(i).toString());
        }
        //load at start - end

        //buttons and listeners
        Components.addButton = new JButton("Add");
        Components.addButton.addActionListener(e -> {
            String name = Components.nameField.getText();
            String type = Components.typeField.getText();
            String damage = Components.damageField.getText();
            /*
            I decided here that name, type, and damage are the
            most important facets of a Pokemon card, at least
            in my experience. This is where I addressed the concerns
            talked about in the card class. Variant and set are
            optional.

            This is error-handling - errors in input
            are "caught" and not allowed to manifest
            in the actual database.
             */
            if (!name.equals("") && !type.equals("") && !damage.equals("")){
                String set = Components.setField.getText();
                if (set.equals("")){
                    set = "Unknown";
                }
                String variant = Components.variantField.getText();
                if (variant.equals("")) {
                    variant = "Unknown";
                }
                Card cardToAdd = new Card(name, type, damage, set, variant);
                /*
                This is why I chose to give Card a toString. Having a string
                representation of the card allows me to skip dealing with
                DefaultListModels with non-string types, something I was unfamiliar
                with. This workaround allows me to have a way to display
                the card using the knowledge I already had.
                 */
                container.add(cardToAdd);
                model.addElement(cardToAdd.toString());

                Components.nameField.setText("");
                Components.typeField.setText("");
                Components.damageField.setText("");
                Components.setField.setText("");
                Components.variantField.setText("");
            }
        });


        Components.removeButton = new JButton("Del");
        Components.removeButton.addActionListener(e -> {
            int selectedIndex = Components.cardsList.getSelectedIndex();
            if (selectedIndex != -1){
                container.remove(selectedIndex);
                model.remove(selectedIndex);
            }
        });

        /*
        This controls the save feature. I chose, instead
        of making the card object serializable, to convert
        the contents of each card into a string format,
        then (later) reconstruct the cards by reading the
        data file line by line (now done). I did
        this because I feel it is easier to do, and if need
        be the user can go into the data.txt file and edit
        what they need to themselves
         */
        Components.saveButton = new JButton("Save");
        Components.saveButton.addActionListener(e -> {
            try (PrintWriter out = new PrintWriter(new FileOutputStream("src/data.txt"))) {
                for (int i = 0; i < container.size(); i++) {
                    out.println(container.get(i).toData());
                }
            } catch (FileNotFoundException ex) {
                /*
                we print an error message to log instead of throwing
                an exception. since saving isn't integral to the
                actual visual structure of the app, and the app
                can still run if the file for saving is not found,
                we don't need to stop the entire program as this
                can be ignored up to a certain point (though, saving
                IS still a very important feature)
                 */
                System.out.println("cannot find file data.txt for saving");
            }
        });


        Components.loadButton = new JButton("Load");
        Components.loadButton.addActionListener(e -> {
            ArrayList<String> blueprintOfCards = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new FileReader("src/data.txt"))) {
                String input = in.readLine();
                while (input != null) {
                    blueprintOfCards.add(input);
                    input = in.readLine();
                }
                /*
                I chose to keep the same method of reading/writing
                taught in CS180. Here, I clear the two lists that
                hold the cards, container for the card objects and
                model for the card display. I chose to do this because
                it overall looks cleaner when the items loaded in
                are the only ones populating the display, as opposed
                to populating on top of unsaved cards that the user has
                added. If the user wises to add cards, they can just
                load, add their cards, then save.
                 */
                container.clear();
                model.clear();
            } catch (Exception ex) {
                 System.out.println("failed to load data");
            }

            /*
            I chose to create a new object each time, as it is
            easy to re-sync an object whose instance fields
            are merely strings. This allows me to avoid
            dealing with complex readers, and just use the
            simple BufferedReader
             */
            for (int i = 0; i < blueprintOfCards.size(); i++) {
                String[] splitData = blueprintOfCards.get(i).split(",");
                container.add(new Card(splitData[0], splitData[1], splitData[2], splitData[3], splitData[4]));
            }

            for (int i = 0; i < container.size(); i++) {
                model.addElement(container.get(i).toString());
            }
        });


        //buttons and listeners - end

        //adding components to panel/frame
        Components.panel.add(Components.nameLabel);
        Components.panel.add(Components.nameField);
        Components.panel.add(Components.typeLabel);
        Components.panel.add(Components.typeField);
        Components.panel.add(Components.damageLabel);
        Components.panel.add(Components.damageField);
        Components.panel.add(Components.setLabel);
        Components.panel.add(Components.setField);
        Components.panel.add(Components.variantLabel);
        Components.panel.add(Components.variantField);
        Components.panel.add(Components.addButton);
        Components.panel.add(Components.removeButton);

        Components.lowerPanel.add(Components.saveButton);
        Components.lowerPanel.add(Components.loadButton);

        /*
        Here, I could have used any layout in order
        to structure the page. The specific layout I chose
        was BorderLayout, and the reasoning lies in the fact
        that this layout type allows for a simple way
        to create a "heading" kind of feel for the
        panel with the buttons and the text fields.
        The user's eye should naturally be drawn to
        where all of the information is, thus why I used
        the center for the card display.
         */
        Components.frame.add(Components.panel, BorderLayout.NORTH);
        Components.frame.add(scrollPane, BorderLayout.CENTER);
        Components.frame.add(Components.lowerPanel, BorderLayout.SOUTH);
        //adding components to panel/frame - end

        Components.frame.setVisible(true);

    });
}

