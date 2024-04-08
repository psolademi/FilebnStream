import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private RandomAccessFile file;
    private int recordCount = 0;

    public RandProductMaker() {
        super("Random Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        JButton addButton = new JButton("Add Record");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addRecord();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(addButton);

        recordCountField = new JTextField();
        recordCountField.setEditable(false);
        add(new JLabel("Record Count:"));
        add(recordCountField);

        try {
            file = new RandomAccessFile("products.dat", "rw");
            recordCount = (int) (file.length() / 128); // Assuming fixed length record size
            recordCountField.setText(Integer.toString(recordCount));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void addRecord() throws IOException {
        String name = padString(nameField.getText(), 35);
        String description = padString(descriptionField.getText(), 75);
        String id = padString(idField.getText(), 6);
        double cost = Double.parseDouble(costField.getText());

        Product product = new Product(id, name, description, cost);
        byte[] data = product.toByteArray();

        file.seek(file.length());
        file.write(data);
        recordCount++;
        recordCountField.setText(Integer.toString(recordCount));

        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
    }

    private String padString(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        } else {
            StringBuilder padded = new StringBuilder(str);
            while (padded.length() < length) {
                padded.append(" ");
            }
            return padded.toString();
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
