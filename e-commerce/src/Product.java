public class Product implements Shipping
{
    public String name;
    public double price;
    public int quantity;
    public boolean isExpired;
    public int expirationTime;
    public boolean isShipping;
    public double weight;
    public String statue;

//    public Product()
//    {
//        this.isExpired = false;
//        this.isShipping = false;
//    }

    public Product(String name, double price, int quantity, boolean isExpired, int expirationTime, boolean isShipping, double weight, String statue) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isExpired = isExpired;
        this.expirationTime = expirationTime;
        this.isShipping = isShipping;
        this.weight = weight;
        this.statue = statue;
    }

//    public Product(String name, double price, int quantity, boolean isExpired, boolean isShipping, double weight, String statue)
//    {
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.isExpired = isExpired;
//        this.isShipping = isShipping;
//        this.weight = weight;
//        this.statue = statue;
//    }
    @Override
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean getIsShipping() {
        return isShipping;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public String getStatue() {
        return statue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setShipping(boolean shipping) {
        isShipping = shipping;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }
}
