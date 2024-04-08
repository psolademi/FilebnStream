import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;
    private RandomAccessFile file;

    public RandProductSearch() {
        super("Random Product Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Enter Partial Product Name:"));
        searchPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchProducts();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        pack(); // Adjust frame size to fit components
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        try {
            file = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProducts() throws IOException {
        String searchTerm = searchField.getText().toLowerCase();
        resultArea.setText(""); // Clear previous results

        List<Product> matchingProducts = new ArrayList<>();
        long fileSize = file.length();
        long currentPosition = 0;

        while (currentPosition < fileSize) {
            byte[] buffer = new byte[128]; // Assuming fixed length record size
            file.seek(currentPosition);
            file.read(buffer);

            Product product = Product.fromByteArray(buffer);
            if (product.getName().toLowerCase().contains(searchTerm)) {
                matchingProducts.add(product);
            }

            currentPosition += 128; // Assuming fixed length record size
        }

        if (matchingProducts.isEmpty()) {
            resultArea.setText("No products found matching the search term.");
        } else {
            for (Product p : matchingProducts) {
                resultArea.append(p.toString() + "\n"); // Use toString method instead of toCSVDataRecord
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RandProductSearch();
            }
        });
    }
}
