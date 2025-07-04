import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Customer
{
    static Scanner scanner = new Scanner(System.in);
    public String username;
    private String password;
    private String email;
    private double balance;
    public Map<Product , Integer> chart = new HashMap<>();
    public ArrayList<Shipping> ShippingProducts = new ArrayList<>();
    private int chartQuantity;

    public Customer()
    {
        this.chartQuantity = 0;
        this.balance = 0;
    }
    public Customer(String username, String password, String email,int chartQuantity , double balance)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.chartQuantity = chartQuantity;
        this.balance = balance;
    }
    public Customer(String username, String password, String email, Map<Product, Integer> cart , int charTQuantity , double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.chart = cart != null ? cart : new HashMap<>(); // Ensure cart is never null
        this.chartQuantity = charTQuantity;
        this.balance = balance;
    }


    public String getPassword() {
        return password;
    }
//    public String getEmail() {
//        return email;
//    }
    public double getChartQuantity() {
        return chartQuantity;
    }
//    public String getUsername() {
//        return username;
//    }

    public Map<Product, Integer> getChart() {
        return chart;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setChart(Map<Product, Integer> chart) {
        this.chart = chart;
    }

    //    public void setEmail(String email) {
//        this.email = email;
//    }
//    public void setChartQuantity(double chartQuantity)
//    {
//        this.chartQuantity = chartQuantity;
//    }
    public static Customer Register()
    {
        String name , password , confirmPassword ,email;
        double balance;
        System.out.println("Please,Enter Your UserName:");
        name = scanner.next();
        while(true)
        {
            System.out.println("Please,Enter Password:");
            password = scanner.next();
            if(Admin.validatePassword(password))
            {
                break;
            }
            else
            {
                System.out.println("Invalid Password!!!");
                System.out.println("Your password should contain at least one lowercase character , one uppercase character , any number[0-9] and special character[@#$%^&+=!]");
                System.out.println("Please,try again ^^");
                System.out.println("===================");
            }
        }
        while(true)
        {
            System.out.println("Please,Enter Confirmation Password:");
            confirmPassword = scanner.next();
            if(confirmPassword.equals(password))
            {
                break;
            }
            else
            {
                System.out.println("Incorrect Confirmation Password!!!");
                System.out.println("Please,try again ^^");
                System.out.println("===================");
            }
        }
        while(true)
        {
            System.out.println("Please,Enter Email:");
            email = scanner.next();
            if(Admin.validateEmail(email))
            {
                break;
            }
            else
            {
                System.out.println("Invalid Email!!!");
                System.out.println("Your email should be in this format(example@gmail.com)");
                System.out.println("Please,try again ^^");
                System.out.println("===================");
            }
        }
        System.out.println("Please,Enter Your Balance:");
        balance = scanner.nextDouble();
        System.out.println("Registration completed successfully ^^");
        Customer cust = new Customer(name , password ,  email , Admin.charTQuantity , balance);
        Admin.addCustomer(cust);
        return cust;
    }
    public static Customer LogIn()
    {
        String name , password , ans;
        Customer cust = new Customer();
        while(true)
        {
            System.out.println("===========================");
            System.out.println("Please,Enter Your UserName:");
            name = scanner.next();
            System.out.println("Please,Enter Password:");
            password = scanner.next();
            cust.setUsername(name);
            cust.setPassword(password);
            cust = Admin.validateLogIN(cust);
            if(cust.email != null)
            {
                System.out.println("LogIn completed successfully ^^");
                break;
            }
            else
            {
                System.out.println("Invalid UserName or Password!!!");
                System.out.println("Please,try again ^^");
                while(true)
                {
                    System.out.println("Do you continue or create new account?");
                    System.out.println("Please, Enter Continue or Create");
                    ans = scanner.next();
                    if(ans.toLowerCase().equals("continue"))
                    {
                        break;
                    }
                    else if (ans.toLowerCase().equals("create"))
                    {
                        Register();
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Answer!!!");
                        System.out.println("Please,try again ^^");
                        System.out.println("===================");
                    }
                }
            }
        }
        return cust;
    }
    public void logout()
    {
        System.out.println("Log out completed successfully ^^");
    }
    public static void add_To_chart(Product item , int quantity , Customer cust)
    {
        String answer;
        boolean bool;
        if(cust.chartQuantity > 0)
        {
            if(quantity > item.getQuantity())
            {
                System.out.println("The Exist Quantity is only" + item.getQuantity());
                while(true)
                {
                    System.out.println("Do you want take them?");
                    System.out.println("================================");
                    System.out.println("User Answer: ");
                    answer = scanner.next();
                    if(answer.toLowerCase().equals("no") | answer.toLowerCase().equals("yes"))
                    {
                        bool = true;
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Answer!!!");
                        System.out.println("Please, try again ^^");
                        System.out.println("=====================");
                    }
                }
                if(bool && answer.toLowerCase().equals("yes"))
                {
                    Map<Product , Integer> newlist = cust.getChart();
                    newlist.put(item , quantity);
                    cust.setChart(newlist);
                    cust.chartQuantity -= item.getQuantity();
                    if(cust.chartQuantity < 0)
                    {
                        cust.chartQuantity = 0;
                    }
                    Admin.changeProductQuantity(item , item.getQuantity());
                }
            }
            else
            {
                Map<Product , Integer> newlist = cust.getChart();
                newlist.put(item , quantity);
                cust.setChart(newlist);
                cust.chartQuantity -= quantity;
                if(cust.chartQuantity < 0)
                {
                    cust.chartQuantity = 0;
                }
                Admin.changeProductQuantity(item , quantity);
            }
        }
    }
    public void CheckOut()
    {
        double totalprice = Admin.calculateSumPrice(chart);
        double totalshipping = Admin.calculateSumShippingPrice(chart);
        double reminder = Main.customer.getBalance() - (totalprice + totalshipping);
        if(reminder < 0)
        {
            System.out.println("Your Balance is insufficient, you should add more balance ^^");
            // in main ask if he/she wants to add balance then call function add balance.
            return;
        }
        if(chart.size() == 0)
        {
            System.out.println("Your Chart is empty , you  should add products first ^^");
            return;
        }
        Main.customer.setBalance(reminder);
        System.out.println("===========================");
        System.out.println("  ** Checkout receipt **  ");
        System.out.println("==========================");
        for (Product p : chart.keySet())
        {
            System.out.println(chart.get(p)+ "x " + p.getName() + "          " + p.getPrice() + "$");
        }
        System.out.println("=======================================================");
        System.out.println("Subtotal                " + totalprice + "$");
        System.out.println("Shipping                " + totalshipping +"$");
        System.out.println("Total Amount            " + (totalprice + totalshipping) + "$");
    }
    @Override
    public String toString() {
        StringBuilder cartDetails = new StringBuilder();
        cartDetails.append("Cart{");
        for (Map.Entry<Product, Integer> entry : chart.entrySet()) {
            cartDetails.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append(", ");
        }
        if (!chart.isEmpty()) {
            cartDetails.setLength(cartDetails.length() - 2); // Remove trailing comma
        }
        cartDetails.append("}");
        return "Customer{username='" + username + "', email='" + email +
                "', cartWeightLimit=" + chartQuantity + ", " + cartDetails + "}";
    }
}
