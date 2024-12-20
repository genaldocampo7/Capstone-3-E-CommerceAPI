## EasyShop

EasyShop is a demo e-commerce website that sells products from 3 different categories: Electronics, Fashion, and Home & Kitchen. 
The website is a frontend project and uses a Springboot API project for the backend server. 

## Resources

* Visual Studio Code (run web application, `index.html`)
* IntelliJ (Spring Boot API)
* MySQL workbench (data storage)
* Postman (run collections and tests)

## Features

### Homepage

<img width="1439" alt="Screenshot 2024-12-19 at 11 45 05 PM" src="https://github.com/user-attachments/assets/f6c738fb-e67b-44d5-9e05-d73210f456eb" />

* Products are listed and can be filtered by category, price, and color

### Login
<img width="517" alt="Screenshot 2024-12-19 at 11 45 55 PM" src="https://github.com/user-attachments/assets/adebdc45-d16b-44ca-ab4f-fb9d6f50ac97" />

* Users and admin that registered an account can login into their profile in EasyShop

### Update profile
<img width="1440" alt="Screenshot 2024-12-19 at 11 51 19 PM" src="https://github.com/user-attachments/assets/eddce591-0465-4d49-b8f0-921f8804f5b3" />

* Users are able to view their profile and update their personal information 

## Interesting piece of code

an interesting piece of code is The MapRow method that is featured in spring framework. 
It acts as a bridge between data retrieved from a database using a ResultSet and objects used in a Java application. 

```java
private Profile mapRow(ResultSet row) throws SQLException {
        int userId = row.getInt("user_id");
        String first_name = row.getString("first_name");
        String last_name = row.getString("last_name");
        String phone = row.getString("phone");
        String email = row.getString("email");
        String address = row.getString("address");
        String city = row.getString("city");
        String state = row.getString("state");
        String zip = row.getString("zip");

        Profile profile = new Profile() {
            {
                    setUserId(userId);
                    setFirstName(first_name);
                    setLastName(last_name);
                    setPhone(phone);
                    setEmail(email);
                    setAddress(address);
                    setCity(city);
                    setState(state);
                    setZip(zip);

                }};
        return profile;

    }
```

## Future Versions

* **Admin page:**  allows admin to add, update, and delete categories and products
* **View orders:** users can view order history and leave reviews
* **Save for later:** users can save products and purchase them at a separate time


