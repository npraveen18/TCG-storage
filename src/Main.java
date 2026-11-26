import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
Main class

This holds all of the UI elements, and the save/load features.

I tried to group together all actions done to similar style
components (all labels in one place, all text fields in one
place, etc.) to increase readability.
 */
public static void main(String[] args) {
    //I chose to keep the container out of the UI
    //thread, since this will be used later for saving/loading
    //which shouldn't happen in the UI thread
    ArrayList<Card> container = new ArrayList<>();
    //Use an EDT to handle UI stuff. This
    //helps future-proof this by adding room for threading
    //if implemented in the future
    SwingUtilities.invokeLater(() -> {
       Components.frame = new JFrame("Pokemon Database");
        Components.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Components.frame.setLayout(new BorderLayout());
        Components.frame.setSize(700,800);


        Components.panel = new JPanel(new FlowLayout());

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

        Components.frame.setVisible(true);

    });
}

