package hospital.models;

/** Medicine model class */
public class Medicine {

    private int    medicineId;
    private String name;
    private String category;
    private int    quantity;
    private double price;
    private String expiryDate;    // String for simplicity (YYYY-MM-DD)
    private String manufacturer;

    public Medicine() {}

    public Medicine(int medicineId, String name, String category,
                    int quantity, double price, String expiryDate,
                    String manufacturer) {
        this.medicineId   = medicineId;
        this.name         = name;
        this.category     = category;
        this.quantity     = quantity;
        this.price        = price;
        this.expiryDate   = expiryDate;
        this.manufacturer = manufacturer;
    }

    public int    getMedicineId()   { return medicineId; }
    public String getName()         { return name; }
    public String getCategory()     { return category; }
    public int    getQuantity()     { return quantity; }
    public double getPrice()        { return price; }
    public String getExpiryDate()   { return expiryDate; }
    public String getManufacturer() { return manufacturer; }

    public void setMedicineId(int id)         { this.medicineId   = id; }
    public void setName(String name)           { this.name         = name; }
    public void setCategory(String category)   { this.category     = category; }
    public void setQuantity(int quantity)      { this.quantity     = quantity; }
    public void setPrice(double price)         { this.price        = price; }
    public void setExpiryDate(String date)     { this.expiryDate   = date; }
    public void setManufacturer(String mfr)    { this.manufacturer = mfr; }

    @Override
    public String toString() { return name + " (" + category + ")"; }
}
