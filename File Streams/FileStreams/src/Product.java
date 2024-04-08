import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ID;
    private String name;
    private String description;
    private double cost;

    // Constructor
    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    // Getters and setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Method to serialize Product object to byte array
    public byte[] toByteArray() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s", ID));
        sb.append(String.format("%-35s", name));
        sb.append(String.format("%-75s", description));
        sb.append(String.format("%.2f", cost));
        return sb.toString().getBytes();
    }

    // Method to deserialize byte array to Product object
    public static Product fromByteArray(byte[] data) {
        String record = new String(data).trim();
        String ID = record.substring(0, 6).trim();
        String name = record.substring(6, 41).trim();
        String description = record.substring(41, 116).trim();
        double cost = Double.parseDouble(record.substring(116).trim());
        return new Product(ID, name, description, cost);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
