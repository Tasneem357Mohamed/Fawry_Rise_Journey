import java.util.ArrayList;
import java.util.Map;

public class Admin
{
    private final static String PasswordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    private final static String EmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    // Pay 10$ for each kilogram.
    private final static double Priceforshipping = 10;
    public final static int charTQuantity = 20;
    private String username;
    private String password;
    private String email;

    public Admin(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static boolean validatePassword(String password)
    {
        if(password.matches(PasswordRegex))
        {
            return true;
        }
        return false;
    }
    public static boolean validateEmail(String email)
    {
        if(email.matches(EmailRegex))
        {
            return true;
        }
        return false;
    }
    public static Customer validateLogIN(Customer cust)
    {
        for(Customer customer:Main.Customers)
        {
            if(customer.username.equals(cust.username) && customer.getPassword().equals(cust.getPassword()))
            {
                cust = customer;
                break;
            }
        }
        return cust;
    }
    public static void addCustomer(Customer cust)
    {
        Main.Customers.add(cust);
    }
    public static void display_products(ArrayList<Product> Products )
    {
        int count = 1;
        System.out.println("The Products That Exist In Ouer System Are:");
        for(Product pro:Products)
        {
            System.out.println("Product number " + count + " : ");
            System.out.println("===============================");
            System.out.print("The Product Name : ");
            System.out.println(pro.getName());
            System.out.print("The Product Price : ");
            System.out.println(pro.getPrice());
            System.out.print("The Product Quantity : ");
            System.out.println(pro.getQuantity());
            System.out.print("The Product Is Shipping : ");
            if(pro.getIsShipping())
            {
                System.out.println("Yes");
                System.out.print("The Product Shipping Weight(in kg): ");
                System.out.println(pro.getWeight());
                System.out.println("You Should Know You will Pay 10$ For Each Kilogram");
            }
            else
            {
                System.out.println("No");
            }
            System.out.print("The Product Is Expired : ");
            if(pro.getIsShipping())
            {
                System.out.println("Yes");
                System.out.print("The Product Expiration Time: ");
                System.out.println(pro.getExpirationTime());
            }
            else
            {
                System.out.println("No");
            }
            System.out.print("The Product Availability Statue: ");
            System.out.println(pro.getStatue());
            count++;
        }
    }
    public static void collectShippingProducts(Customer cust)
    {
        Map<Product, Integer> chart = cust.getChart();
        for (Product pro : chart.keySet())
        {
            cust.ShippingProducts.add((Shipping) pro);
        }
    }
    //this function i put them here instead class ShippingService.
    public static void ShippingService(ArrayList<Shipping> products , Customer cust)
    {
        if(cust.ShippingProducts.size() == 0)
        {
            System.out.println("Your Shipping Chart is empty , you  should add products first ^^");
            return;
        }
        System.out.println(" ** Shipment Notice ** ");
        System.out.println("=======================");
        Map<Product, Integer> chart = cust.getChart();
        double totalweight = 0.0;
        for (Shipping pro : products) {
            int quantity = 1; // default quantity
            for (Product p : chart.keySet()) {
                if (pro.getName().equals(p.getName())) {
                    quantity = chart.get(p);
                    break;
                }
            }
            System.out.println(quantity + "x " + pro.getName() + "          " + pro.getWeight()/1000.0 + "g");
            totalweight += pro.getWeight();
        }
        System.out.println("Total package weight  " + totalweight + "kg");
    }
    public static double calculateSumPrice(Map<Product,Integer> chartpro)
    {
        double sumPrice = 0.0;
        Map<Product, Integer> chart = chartpro;
        for(Map.Entry<Product, Integer> entry : chart.entrySet())
        {
            sumPrice += (entry.getKey().getPrice() * entry.getValue());
        }
        return sumPrice;
    }
    public static double calculateSumShippingPrice(Map<Product,Integer> chartpro)
    {
        double sumShippingPrice = 0.0 ;
        Map<Product, Integer> chart = chartpro;
        for(Map.Entry<Product, Integer> entry : chart.entrySet())
        {
            if(entry.getKey().isShipping)
            {
                sumShippingPrice += (entry.getKey().weight * Admin.Priceforshipping * entry.getValue());
            }
        }
        return sumShippingPrice;
    }
    public static void changeProductQuantity(Product p , int quantity)
    {
        for(Product pro:Main.Products)
        {
            if(pro.getName().equals(p.getName()))
            {
                if((pro.getQuantity() - quantity) <= 0)
                {
                    pro.setQuantity(0);
                    pro.setStatue("Out Of Stock");
                }
                if(pro.getQuantity() > 0)
                {
                    pro.setQuantity((pro.getQuantity() - quantity));
                    if(pro.getQuantity() <= 0)
                    {
                        pro.setQuantity(0);
                        pro.setStatue("Out Of Stock");
                    }
                }
            }
        }
    }
}
