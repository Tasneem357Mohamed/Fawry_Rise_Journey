import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main
{
    static  Scanner scanner = new Scanner(System.in);
    static Customer customer;
    static ArrayList<Product> Products = new ArrayList<>();
    static ArrayList<Customer> Customers = new ArrayList<>();

    //fill data
    static {
        // Initialize Products with 5 instances
        Products = new ArrayList<>();
        Products.add(new Product("Laptop", 999.99, 10, false, 365, true, 2.5, "in stock"));
        Products.add(new Product("Smartphone", 499.99, 20, false, 730, true, 0.2, "in stock"));
        Products.add(new Product("Headphones", 79.99, 50, false, 0, true, 0.3, "in stock"));
        Products.add(new Product("Milk", 3.49, 100, true, 7, false, 1.0, "in stock"));
        Products.add(new Product("Book", 19.99, 30, false, 0, true, 0.5, "out of stock"));

        // Initialize Customers with 3 instances, each with a populated cart
        Customers = new ArrayList<>();
        // Alice's cart: Laptop (1), Smartphone (2)
        Map<Product, Integer> aliceCart = new HashMap<>();
        aliceCart.put(Products.get(0), 1); // Laptop
        aliceCart.put(Products.get(1), 2); // Smartphone
        Customers.add(new Customer("alice123", "passW%123", "alice@example.com",aliceCart , Admin.charTQuantity - aliceCart.size() , 150.0));
        // Bob's cart: Headphones (3), Book (1)
        Map<Product, Integer> bobCart = new HashMap<>();
        bobCart.put(Products.get(2), 3); // Headphones
        bobCart.put(Products.get(4), 1); // Book
        Customers.add(new Customer("bob456", "passW^456", "bob@example.com", bobCart ,Admin.charTQuantity - bobCart.size() , 300.0));
        // Charlie's cart: Smartphone (1), Headphones (2), Milk (5)
        Map<Product, Integer> charlieCart = new HashMap<>();
        charlieCart.put(Products.get(1), 1); // Smartphone
        charlieCart.put(Products.get(2), 2); // Headphones
        charlieCart.put(Products.get(3), 5); // Milk
        Customers.add(new Customer("charlie789", "passW*789", "charlie@example.com", charlieCart ,Admin.charTQuantity - charlieCart.size() , 500.0));
    }

    public static void main(String[] args)
    {
        String answer;
        while(true)
        {
            System.out.println("Welcome To Our E-commerce System ^^");
            System.out.println("===================================");
            int ans;
            // called the customer functions
            while(true)
            {
                System.out.println("What do you want to do?");
                System.out.println("=======================");
                System.out.println("1- Register");
                System.out.println("2- LogIn");
                System.out.println("3- Add Product To Your Chart");
                System.out.println("4- CheckOut");
                System.out.println("5- log out");
                ans = scanner.nextInt();
                if(ans == 1 || ans == 2 || ans == 3 || ans == 4 || ans == 5)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid Number!!!");
                    System.out.println("Please,try again ^^");
                    System.out.println("===================");
                }
            }
            //register
            if(ans == 1)
            {
                customer = Customer.Register();
                System.out.println("You Current Chart Load: " + customer.getChartQuantity());
                while(true)
                {
                    System.out.println("What do you want to do?");
                    System.out.println("=======================");
                    System.out.println("1- LogIn");
                    System.out.println("2- Add Product To Your Chart");
                    System.out.println("3- CheckOut");
                    System.out.println("4- log out");
                    ans = scanner.nextInt();
                    if(ans == 1 || ans == 2 || ans == 3 || ans == 4)
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Number!!!");
                        System.out.println("Please,try again ^^");
                        System.out.println("===================");
                    }
                }
                // Log IN
                if(ans == 1)
                {
                    customer = Customer.LogIn();
                    while(true)
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("=======================");
                        System.out.println("1- Add Product To Your Chart");
                        System.out.println("2- CheckOut");
                        System.out.println("3- log out");
                        ans = scanner.nextInt();
                        if(ans == 1 || ans == 2 || ans == 3)
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid Number!!!");
                            System.out.println("Please,try again ^^");
                            System.out.println("===================");
                        }
                    }
                    if(ans == 1)
                    {
                        //add product
                        if(customer == null)
                        {
                            customer = Customer.LogIn();
                        }
                        int enter;
                        Product pro;
                        while(true)
                        {
                            if(customer.getChartQuantity() ==  0)
                            {
                                System.out.println("Sorry,You have reached the maximum number of available products ^^");
                                break;
                            }
                            Admin.display_products(Products);
                            System.out.println("Enter The Number Of Product That You Want Add It ^^");
                            enter = scanner.nextInt();
                            pro = Products.get(enter - 1);
                            System.out.println("Enter The Number Of Quantity That You Want From This Product ^^");
                            enter = scanner.nextInt();
                            Customer.add_To_chart(pro , enter , customer);
                            boolean finished;
                            while(true)
                            {
                                System.out.println("Do you want add another product?");
                                System.out.println("================================");
                                System.out.println("User Answer: ");
                                answer = scanner.next();
                                if(answer.toLowerCase().equals("no") | answer.toLowerCase().equals("yes"))
                                {
                                    finished = true;
                                    break;
                                }
                                else
                                {
                                    System.out.println("Invalid Answer!!!");
                                    System.out.println("Please, try again ^^");
                                    System.out.println("=====================");
                                }
                            }
                            if(finished && answer.toLowerCase().equals("no"))
                            {
                                System.out.println("You have finished adding all the products to the list ^^");
                                break;
                            }
                        }
                        Admin.collectShippingProducts(customer);
                    }
                    else if(ans == 2)
                    {
                        //check out
                        if(customer == null)
                        {
                            System.out.println("You Should Log In First ^^");
                            customer = Customer.LogIn();
                        }
                        Admin.ShippingService(customer.ShippingProducts , customer);
                        customer.CheckOut();
                        System.out.println("You receipt Checked out successfully ^^");
                        customer.chart.clear();
                    }
                    else
                    {
                       // log out
                        customer.logout();
                    }
                }
                // ADD Product
                else if(ans == 2)
                {
                    if(customer == null)
                    {
                        System.out.println("You Should Log In First ^^");
                        customer = Customer.LogIn();
                    }
                    int enter;
                    Product pro;
                    while(true)
                    {
                        if(customer.getChartQuantity() ==  0)
                        {
                            System.out.println("Sorry,You have reached the maximum number of available products ^^");
                            break;
                        }
                        Admin.display_products(Products);
                        System.out.println("Enter The Number Of Product That You Want Add It ^^");
                        enter = scanner.nextInt();
                        pro = Products.get(enter - 1);
                        System.out.println("Enter The Number Of Quantity That You Want From This Product ^^");
                        enter = scanner.nextInt();
                        customer.add_To_chart(pro , enter , customer);
                        boolean finished;
                        while(true)
                        {
                            System.out.println("Do you want add another product?");
                            System.out.println("================================");
                            System.out.println("User Answer: ");
                            answer = scanner.next();
                            if(answer.toLowerCase().equals("no") | answer.toLowerCase().equals("yes"))
                            {
                                finished = true;
                                break;
                            }
                            else
                            {
                                System.out.println("Invalid Answer!!!");
                                System.out.println("Please, try again ^^");
                                System.out.println("=====================");
                            }
                        }
                        if(finished && answer.toLowerCase().equals("no"))
                        {
                            System.out.println("You have finished adding all the products to the list ^^");
                            break;
                        }
                    }
                    Admin.collectShippingProducts(customer);
                }
                // Check Out
                else if(ans == 3)
                {
                    if(customer == null)
                    {
                        System.out.println("You Should Log In First ^^");
                        customer = Customer.LogIn();
                    }
                    Admin.ShippingService(customer.ShippingProducts , customer);
                    customer.CheckOut();
                    System.out.println("You receipt Checked out successfully ^^");
                    customer.chart.clear();
                }
                // Log Out
                else
                {
                    customer.logout();
                }
            }
            //log in
            else if(ans == 2)
            {
                customer = Customer.LogIn();
                boolean isout;
                while(true)
                {
                    while(true)
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("=======================");
                        System.out.println("1- Add Product To Your Chart");
                        System.out.println("2- CheckOut");
                        ans = scanner.nextInt();
                        if(ans == 1 || ans == 2)
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid Number!!!");
                            System.out.println("Please,try again ^^");
                            System.out.println("===================");
                        }
                    }
                    int enter;
                    Product pro;
                    // add to chart
                    if(ans == 1)
                    {
                        while(true)
                        {
                            if(customer.getChartQuantity() ==  0)
                            {
                                System.out.println("Sorry,You have reached the maximum number of available products ^^");
                                break;
                            }
                            Admin.display_products(Products);
                            System.out.println("Enter The Number Of Product That You Want Add It ^^");
                            enter = scanner.nextInt();
                            pro = Products.get(enter - 1);
                            System.out.println("Enter The Number Of Quantity That You Want From This Product ^^");
                            enter = scanner.nextInt();
                            customer.add_To_chart(pro , enter , customer);
                            boolean bool;
                            while(true)
                            {
                                System.out.println("Do you want add another product?");
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
                            if(bool && answer.toLowerCase().equals("no"))
                            {
                                System.out.println("You have finished adding all the products to the list ^^");
                                break;
                            }
                        }
                        Admin.collectShippingProducts(customer);
                        boolean another;
                        while(true)
                        {
                            System.out.println("Do you want to Check Out | Log out ?");
                            System.out.println("================================");
                            System.out.println("User Answer: ");
                            answer = scanner.next();
                            if(answer.toLowerCase().equals("checkout") | answer.toLowerCase().equals("logout"))
                            {
                                another = true;
                                break;
                            }
                            else
                            {
                                System.out.println("Invalid Answer!!!");
                                System.out.println("Please, try again ^^");
                                System.out.println("=====================");
                            }
                        }
                        if(another && answer.toLowerCase().equals("checkout"))
                        {
                            //call checkout
                            Admin.ShippingService(customer.ShippingProducts , customer);
                            customer.CheckOut();
                            System.out.println("You receipt Checked out successfully ^^");
                            customer.chart.clear();
                        }
                        else if(answer.toLowerCase().equals("logout"))
                        {
                            customer.logout();
                        }
                    }
                    //check out
                    else
                    {
                        Admin.ShippingService(customer.ShippingProducts , customer);
                        customer.CheckOut();
                        System.out.println("You receipt Checked out successfully ^^");
                        customer.chart.clear();
                    }
                    while(true)
                    {
                        System.out.println("Do you want to do another thing?");
                        System.out.println("================================");
                        System.out.println("User Answer: ");
                        answer = scanner.next();
                        if(answer.toLowerCase().equals("no") || answer.toLowerCase().equals("yes"))
                        {
                            isout = true;
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid Answer!!!");
                            System.out.println("Please, try again ^^");
                            System.out.println("=====================");
                        }
                    }
                    if(isout && answer.toLowerCase().equals("no"))
                    {
                        customer.logout();
                        break;
                    }
                }
            }
            //add product
            else if(ans == 3)
            {
                if(customer == null)
                {
                    System.out.println("You Should Log in First ^^");
                    customer = Customer.LogIn();
                }
                int enter;
                Product pro;
                while(true)
                {
                    if(customer.getChartQuantity() ==  0)
                    {
                        System.out.println("Sorry,You have reached the maximum number of available products ^^");
                        break;
                    }
                    Admin.display_products(Products);
                    System.out.println("Enter The Number Of Product That You Want Add It ^^");
                    enter = scanner.nextInt();
                    pro = Products.get(enter - 1);
                    System.out.println("Enter The Number Of Quantity That You Want From This Product ^^");
                    enter = scanner.nextInt();
                    Customer.add_To_chart(pro , enter , customer);
                    boolean finished;
                    while(true)
                    {
                        System.out.println("Do you want add another product?");
                        System.out.println("================================");
                        System.out.println("User Answer: ");
                        answer = scanner.next();
                        if(answer.toLowerCase().equals("no") | answer.toLowerCase().equals("yes"))
                        {
                            finished = true;
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid Answer!!!");
                            System.out.println("Please, try again ^^");
                            System.out.println("=====================");
                        }
                    }
                    if(finished && answer.toLowerCase().equals("no"))
                    {
                        System.out.println("You have finished adding all the products to the list ^^");
                        break;
                    }
                    Admin.collectShippingProducts(customer);
                }
            }
            //check out
            else if(ans == 4)
            {
                if(customer == null)
                {
                    System.out.println("You Should Log in First ^^");
                    customer = Customer.LogIn();
                }
                Admin.ShippingService(customer.ShippingProducts , customer);
                customer.CheckOut();
                System.out.println("You receipt Checked out successfully ^^");
                customer.chart.clear();
            }
            else
            {
                customer.logout();
            }

            //finish the program
            boolean close;
            while(true)
            {
                System.out.println("Do you want to Close System?");
                System.out.println("================================");
                System.out.println("User Answer: ");
                answer = scanner.next();
                if(answer.toLowerCase().equals("no") | answer.toLowerCase().equals("yes"))
                {
                    close = true;
                    break;
                }
                else
                {
                    System.out.println("Invalid Answer!!!");
                    System.out.println("Please, try again ^^");
                    System.out.println("=====================");
                }
            }
            if(close && answer.toLowerCase().equals("yes"))
            {
                System.out.println("Thank You For Using Our System,come again ^^");
                break;
            }
        }
    }
}