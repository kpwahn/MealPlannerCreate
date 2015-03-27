/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firebasetest;

import com.firebase.client.Firebase;
import java.io.IOException;
//import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gil
 */
@WebServlet(name = "createYear", urlPatterns = {"/createYear"})
public class createYear extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Firebase ref = new Firebase("https://sweltering-heat-3046.firebaseio.com");
        
        String Year = request.getParameter("year");
        Integer year = Integer.parseInt(Year);
        String Building = request.getParameter("building");
        String endYear = request.getParameter("endYear");
        if (endYear.equals("")) 
            endYear = Year;
        else
            request.setAttribute("endYear", endYear);
        
        System.out.println("!!" + endYear);


        //once working, use building here
        Firebase building = ref.child(Building);

        System.out.println("Year" + Year);
        System.out.println("endYear" + endYear);

        createYear(building, year, Integer.parseInt(endYear));
         String message = "";
        if (endYear.equals(Year))
            message = "Successfully added year meal plans for year: " + Year + " to " + Building;
        else 
            message = "Successfully added meal plans for years " + Year + "-" + endYear + " to " + Building;

        request.setAttribute("message", message);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
   
    
    private void createYear(Firebase building, Integer year, int endYear) {
        
        
        ArrayList<String> breakfastEntree = parseBreakEntree();
        ArrayList<String> breakfastSide1 = parseBreakSide1();
        ArrayList<String> breakfastSide2 = new ArrayList<>();
        breakfastSide2.add("Bacon");
        breakfastSide2.add("Sausage");
        ArrayList<String> breakfastCereal = new ArrayList<>();
        breakfastCereal.add("Oatmeal");
        breakfastCereal.add("Cream of the West");
        ArrayList<String> breakfastFruit = new ArrayList<>();
        breakfastFruit.add("Seasonal");
        Collections.shuffle(breakfastEntree);
        Collections.shuffle(breakfastSide1);
        Collections.shuffle(breakfastSide2);
        Collections.shuffle(breakfastCereal);
        Collections.shuffle(breakfastFruit);
     
        ArrayList<String> lunchEntree = parseLunchEntree();
        ArrayList<String> lunchSalad = parseLunchSalad();
        ArrayList<String> lunchStarch = parseLunchStarch();
        ArrayList<String> lunchVeg = parseLunchVeg();
        ArrayList<String> lunchDessert = parseLunchDessert();
        Collections.shuffle(lunchEntree);
        Collections.shuffle(lunchSalad);
        Collections.shuffle(lunchStarch);
        Collections.shuffle(lunchVeg);
        Collections.shuffle(lunchDessert);

        ArrayList<String> dinnerEntree = parseDinnerEntree();
        ArrayList<String> dinnerSoup = parseDinnerSoup();
        ArrayList<String> dinnerGarnish = parseDinnerGarnish();
        ArrayList<String> dinnerDessert = parseDinnerDessert();
        Collections.shuffle(dinnerEntree);
        Collections.shuffle(dinnerSoup);
        Collections.shuffle(dinnerGarnish);
        Collections.shuffle(dinnerDessert);
                int total = 0;

                
        
        for (; year <= endYear; year++) {
        Firebase yearRef = building.child(year.toString());

        for (Integer i = 1; i < 13; i++) { //each month
            int day = 0;
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    day = 30;
                    break;
                case 2:
                    //checking to see if its a leap year, if so add a day to Feb
                    if (isLeapYear(year))
                        day = 29;
                    else
                        day = 28;
                    break;
            }

            Firebase month = yearRef.child(new DateFormatSymbols().getMonths()[i - 1]);
            for (Integer i1 = 1; i1 <= day; i1++, total++) { //each day
                Firebase Day = month.child(i1.toString());

                //creates breakfast for every day
                Firebase meal = Day.child("Breakfast");
                Firebase serving = meal.child("Entree");
                String breakfastE = breakfastEntree.get(total % breakfastEntree.size());
                serving.setValue(breakfastE);

                String breakfastS1 = breakfastSide1.get(total % breakfastSide1.size());
                serving = meal.child("Side1");
                serving.setValue(breakfastS1);

                String breakfastS2 = breakfastSide2.get(total % breakfastSide2.size());
                serving = meal.child("Side2");
                serving.setValue(breakfastS2);

                String breakfastC = breakfastCereal.get(total % breakfastCereal.size());
                serving = meal.child("Cereal");
                serving.setValue(breakfastC);

                String breakfastF = breakfastFruit.get(total % breakfastFruit.size());
                serving = meal.child("Fruit");
                serving.setValue(breakfastF);


                //Creates lunch for every day
                meal = Day.child("Lunch");
                String lunchE = lunchEntree.get(total % lunchEntree.size());
                serving = meal.child("Entree");
                serving.setValue(lunchE);

                String lunchS1 = lunchSalad.get(total % lunchSalad.size());
                serving = meal.child("Salad");
                serving.setValue(lunchS1);

                String lunchS2 = lunchStarch.get(total % lunchStarch.size());
                serving = meal.child("Starch");
                serving.setValue(lunchS2);

                String lunchV = lunchVeg.get(total % lunchVeg.size());
                serving = meal.child("Vegetable");
                serving.setValue(lunchV);

                String lunchD = lunchDessert.get(total % lunchDessert.size());
                serving = meal.child("Dessert");
                serving.setValue(lunchD);


                //Creates dinner for each day
                meal = Day.child("Dinner");
                String dinnerE = dinnerEntree.get(total % dinnerEntree.size());
                serving = meal.child("Entree");
                serving.setValue(dinnerE);

                String dinnerS = dinnerSoup.get(total % dinnerSoup.size());
                serving = meal.child("Soup");
                serving.setValue(dinnerS);

                String dinnerG = dinnerGarnish.get(total % dinnerGarnish.size());
                serving = meal.child("Garnish");
                serving.setValue(dinnerG);

                String dinnerD = dinnerDessert.get(total % dinnerDessert.size());
                serving = meal.child("Dessert");
                serving.setValue(dinnerD);


                //reshuffles if they hit the end of the array
                if (total == breakfastEntree.size())
                    Collections.shuffle(breakfastEntree);
                if (total == breakfastSide1.size())
                    Collections.shuffle(breakfastSide1);
                if (total == breakfastSide2.size())
                    Collections.shuffle(breakfastSide2);
                if (total == breakfastCereal.size())
                    Collections.shuffle(breakfastCereal);
                if (total == breakfastFruit.size())
                    Collections.shuffle(breakfastFruit);


                if (total == lunchEntree.size())
                    Collections.shuffle(lunchEntree);
                if (total == lunchSalad.size())
                    Collections.shuffle(lunchSalad);
                if (total == lunchStarch.size())
                    Collections.shuffle(lunchStarch);
                if (total == lunchVeg.size())
                    Collections.shuffle(lunchVeg);
                if (total == lunchDessert.size())
                    Collections.shuffle(lunchDessert);

                if (total == dinnerEntree.size())
                    Collections.shuffle(dinnerEntree);
                if (total == dinnerSoup.size())
                    Collections.shuffle(dinnerSoup);
                if (total == dinnerGarnish.size())
                    Collections.shuffle(dinnerGarnish);
                if (total == dinnerDessert.size())
                    Collections.shuffle(dinnerDessert);
             }
           }
        }
    }
        
        private ArrayList<String> parseBreakEntree() {
            ArrayList<String> BreakfastEntree = new ArrayList<>();
            BreakfastEntree.add("Apple Belgian Waffle");
            BreakfastEntree.add("Apple Sauce Pancakes"); 
            BreakfastEntree.add("Bacon Breakfast Croissant");
            BreakfastEntree.add("Bacon Breakfast Sandwich");
            BreakfastEntree.add("Bacon Stratta");
            BreakfastEntree.add("Banana Pancakes");
            BreakfastEntree.add("Belgian Waffle");
            BreakfastEntree.add("Biscuits and Gravy");
            BreakfastEntree.add("Blueberry Belgian Waffle");
            BreakfastEntree.add("Blueberry Pancakes");
            BreakfastEntree.add("Cheesy Eggs");
            BreakfastEntree.add("Denver Bake");
            BreakfastEntree.add("Denver Breakfast Omelet");
            BreakfastEntree.add("Denver Breakfast Wrap");
            BreakfastEntree.add("Denver Strata");
            BreakfastEntree.add("Diced Ham and Scrambled Eggs");
            BreakfastEntree.add("Eggs Benedict");
            BreakfastEntree.add("French Toast");
            BreakfastEntree.add("Ham Breakfast Croissant");
            BreakfastEntree.add("Ham Breakfast Sandwich");
            BreakfastEntree.add("Ham Breakfast Wrap");
            BreakfastEntree.add("Ham Stratta");
            BreakfastEntree.add("Pancakes");
            BreakfastEntree.add("Peach Belgian Waffle");
            BreakfastEntree.add("Pigs In A Blanket");
            BreakfastEntree.add("Sausage Breakfast Sandwich");
            BreakfastEntree.add("Sausage Breakfast Wrap");
            BreakfastEntree.add("Sausage in a Blanket");
            BreakfastEntree.add("Vegetable Breakfast Sandwich");
            BreakfastEntree.add("Vegetable Stratta");
        return BreakfastEntree;
        }
        private ArrayList<String> parseBreakSide1() {
             ArrayList<String> breakfastS1 = new ArrayList<>();
             breakfastS1.add("Eggs");
             breakfastS1.add("Hash Browns");
             return breakfastS1;
        }
        
        private ArrayList<String> parseLunchEntree() {
            ArrayList<String> lunch = new ArrayList<>();
            lunch.add("Baked Cod");
            lunch.add("Baked Ham");
            lunch.add("BBQ Chicken");
            lunch.add("BBQ Pork");
            lunch.add("Beef Brisket");
            lunch.add("Beef Stir Fry");
        lunch.add("Beef Stroganoff");
        lunch.add("Breaded Cod");
        lunch.add("Breaded Veal Cutlets");
        lunch.add("Chicken Alfredo");
        lunch.add("Chicken and Dumplings");
        lunch.add("Chicken Cordon Bleu");
        lunch.add("Chicken Fried Steak");
        lunch.add("Chicken in a Hunter Sauce");
        lunch.add("Chicken in Apricot");
        lunch.add("Chicken in Mushroom Sauce");
        lunch.add("Chicken Stew");
        lunch.add("Corned Beef and Cabbage");
        lunch.add("Country Fried Steak");
        lunch.add("Cran Dijon Chix");
        lunch.add("Cranberry Dijon Chicken");
        lunch.add("Crunchy Baked Cod");
        lunch.add("Fried Chicken");
        lunch.add("German Sausage and Sauerkraut");
        lunch.add("Grilled Ham Steak");
        lunch.add("Grilled Pork Chops");
        lunch.add("Ham Steak");
        lunch.add("Home Style Beef Stew");
        lunch.add("Italian Ham and Peas");
        lunch.add("Lasagna");
        lunch.add("Lemon Pepper Cod");
        lunch.add("Liver and Onions");
        lunch.add("Meat Loaf");
        lunch.add("New York Steak");
        lunch.add("Open Faced Turkey Sandwich");
        lunch.add("Orange Beef");
        lunch.add("Pepper Steak");
        lunch.add("Poached Salmon/Bearnaise");
        lunch.add("Pork Chops");
        lunch.add("Pork Loin Chops with Pears");
        lunch.add("Pot Roast");
        lunch.add("Pot Roast of Beef with Yorkshire Pudding");
        lunch.add("Prime Rib");
        lunch.add("Roast Beef");
        lunch.add("Roast Beef with Gravy");
        lunch.add("Roast Chicken Thighs");
        lunch.add("Roast Pork");
        lunch.add("Roast Pork with Apple Stuffing");
        lunch.add("Roast Pork with Dressing");
        lunch.add("Seafood Alfredo");
        lunch.add("Shepherd's Pie");
        lunch.add("Shrimp and Beef Medallions");
        lunch.add("Spanish Meatloaf");
        lunch.add("Swedish Meatballs");
        lunch.add("Swiss Steak");
        lunch.add("Teriyaki Chicken Thighs");
        lunch.add("Teriyaki Salmon Fillet");
        lunch.add("Tilapia");
        lunch.add("Turkey Dinner");
        lunch.add("Turkey Pot Pie");
        lunch.add("Veal Parmesan");
            return lunch;
        }
        private ArrayList<String> parseLunchSalad() {
            ArrayList<String> lunch = new ArrayList<>();
        lunch.add("Asaparagus");
        lunch.add("Broccoli and Tomato");
        lunch.add("Butter Lettuce");
        lunch.add("Caesar");
        lunch.add("Carrot");
        lunch.add("Cauliflower and Pea");
        lunch.add("Chef");
        lunch.add("Coleslaw");
        lunch.add("Corn");
        lunch.add("Cranberry");
        lunch.add("Creamy Tomato and Cucumber");
        lunch.add("Cucumber");
        lunch.add("Dai");
        lunch.add("Dill Cucumber");
        lunch.add("Garden Green");
        lunch.add("Greek");
        lunch.add("Green");
        lunch.add("Ham and Pineapple");
        lunch.add("Jell-O");
        lunch.add("Macaroni");
        lunch.add("Marinated");
        lunch.add("Marinated ASP");
        lunch.add("Marinated Mushroom");
        lunch.add("Marinated Vegetable");
        lunch.add("Orange and Romaine");
        lunch.add("Oriental");
        lunch.add("Piccadilly Peach");
        lunch.add("Pickled Beet");
        lunch.add("Red Potato");
        lunch.add("Seafood");
        lunch.add("Selection");
        lunch.add("Shrimp Cocktail");
        lunch.add("Spinach");
        lunch.add("Steamed Asparagus");
        lunch.add("Strawberry and Romane");
        lunch.add("Summer Pasta");
        lunch.add("Sunshine");
        lunch.add("Three Bean");
        lunch.add("Tomato and Onion");
        lunch.add("Tossed");
        lunch.add("Tossed Green");
        lunch.add("Waldorf");
            return lunch;
        }
        private ArrayList<String> parseLunchStarch() {
            ArrayList<String> lunch = new ArrayList<>();
        lunch.add("Angel Hair Pasta");
        lunch.add("Au Gratin");
        lunch.add("Baked");
        lunch.add("Baked Potato and Sour Cream");
        lunch.add("Baked Potato With Trimmings");
        lunch.add("Boiled Red Potatoes");
        lunch.add("Butter Noodle");
        lunch.add("Corn Bread With Honey Butter");
        lunch.add("Fettuccine");
        lunch.add("Garlic and Herb Mashed Potatoes");
        lunch.add("Homemade Biscuits");
        lunch.add("Johnny Spuds");
        lunch.add("Linguine Noodles with Tomato Sauce");
        lunch.add("Mashed");
        lunch.add("Mashed Potatoes");
        lunch.add("Oven Roasted Potatoes");
        lunch.add("Potato Roasted");
        lunch.add("Potato Salad");
        lunch.add("Potato with Onions");
        lunch.add("Potato with Vegetables");
        lunch.add("Rice");
        lunch.add("Roasted Red");
        lunch.add("Scalloped Potatoes");
        lunch.add("Steamed Potatoes");
        lunch.add("Steamed Rice");
        lunch.add("Whipped Potatoes");
        lunch.add("Yams");
            return lunch;
        }
        private ArrayList<String> parseLunchVeg() {
            ArrayList<String> lunch = new ArrayList<>();
        lunch.add("Asparagus");
        lunch.add("Asparagus with Hollandaise Sauce");
        lunch.add("Baby Carrots");
        lunch.add("Bean and Carrot Medley");
        lunch.add("Bean Casserole");
        lunch.add("Beet Slices");
        lunch.add("Broccoli");
        lunch.add("Broccoli Cheese");
        lunch.add("Broccoli/Cauliflower with Cheese Sauce");
        lunch.add("Carrot and Bean Medley");
        lunch.add("Carrot Coins");
        lunch.add("Carrots");
        lunch.add("Cauliflower Cheese");
        lunch.add("Chopped Spinach");
        lunch.add("Corn");
        lunch.add("Corn Carrots");
        lunch.add("French Cut Green Beans");
        lunch.add("Gingered Carrots");
        lunch.add("Green Beans");
        lunch.add("Harvard Beets");
        lunch.add("Italian Vegetables");
        lunch.add("Medley");
        lunch.add("Mixed Vegetables");
        lunch.add("Niblet Corn");
        lunch.add("Niblet Corn or Corn on the Cob");
        lunch.add("Oriental");
        lunch.add("Peas");
        lunch.add("Peas and Onions");
        lunch.add("Seasonal Vegetable Garnish");
        lunch.add("Select Vegetable");
        lunch.add("Squash");
        lunch.add("Steamed Corn");
        lunch.add("Steamed Sliced Carrots");
        lunch.add("Stir Fry");
        lunch.add("Stuffed Green Peppers");
        lunch.add("Vegetable/ASP");
        lunch.add("Yellow Beans");
        lunch.add("Zucchini");
        lunch.add("Zucchini Bake");
        lunch.add("Zucchini with Red Peppers");
            return lunch;
        }
        private ArrayList<String> parseLunchDessert() {
            ArrayList<String> lunch = new ArrayList<>();
       lunch.add("Ambrosia Salad");
       lunch.add("Angel Food");
       lunch.add("Apple Crisp");
       lunch.add("Apple Pie");
       lunch.add("Apple Spice Cake");
       lunch.add("Apple Tarts");
       lunch.add("Applie Pie a la Mode");
       lunch.add("Apricot Crisp");
       lunch.add("Blueberry Pie");
       lunch.add("Bread Pudding");
       lunch.add("Brownie Sundae");
       lunch.add("Carrot Cake");
       lunch.add("Chocolate Cake");
       lunch.add("Chocolate Cream Pie");
       lunch.add("Chocolate Cream Puffs");
       lunch.add("Chocolate Zucchini Cake");
       lunch.add("Chocolate/Zucchini Cake");
       lunch.add("Coconut Cream Pie");
       lunch.add("Cranberry Ice");
       lunch.add("Cream Pie");
       lunch.add("Cream Puffs");
       lunch.add("Date Squares");
       lunch.add("Fruit Pie");
       lunch.add("Fruited Jell-O");
       lunch.add("Grasshopper Delight");
       lunch.add("Ice Cream with Toppings");
       lunch.add("Iced Cupcakes");
       lunch.add("Italian Cream Cake");
       lunch.add("Lemon Loaf with Lemon Sauce");
       lunch.add("Lemon Meringue");
       lunch.add("Lemon Meringue Pie");
       lunch.add("Mandarin Oranges");
       lunch.add("Mocha Dessert");
       lunch.add("Mocha Mousse");
       lunch.add("Peach Melba");
       lunch.add("Peach Pie");
       lunch.add("Peach Pie with Ice Cream");
       lunch.add("Pumpkin Custard Pie");
       lunch.add("Pumpkin Pie");
       lunch.add("Raisin Bars");
       lunch.add("Rhubarb Pie");
       lunch.add("Rice Pudding");
       lunch.add("Selection");
       lunch.add("Sherbet");
       lunch.add("Sliced Peaches with Cookie");
       lunch.add("S'more Delight");
       lunch.add("Spice Cake");
       lunch.add("Stewed Rhubarb with Ice Cream");
       lunch.add("Strawberry Cheesecake");
       lunch.add("Tiramisu");
       lunch.add("Tropical Chiffon");
       lunch.add("Upside Down Cake");
       lunch.add("Walnut Cream Layer");
       lunch.add("White Cake");
       lunch.add("Yellow Cake");
            return lunch;
        }
        
        private ArrayList<String> parseDinnerEntree() {
            ArrayList<String> dinner = new ArrayList<>();
        dinner.add("Assorted Sandwiches");
        dinner.add("Bacon Lettuce and Tomato");
        dinner.add("Baked Potato Bar");
        dinner.add("BBQ Chicken Thighs");
        dinner.add("Breaded Chicken Drumstick");
        dinner.add("Cheese Burger");
        dinner.add("Chicken and Dumplings");
        dinner.add("Chicken Casserole");
        dinner.add("Chicken Dummies");
        dinner.add("Chicken Pot Pie");
        dinner.add("Chicken Salad Sandwich on Croissant");
        dinner.add("Chicken Strips");
        dinner.add("Chicken Tenders");
        dinner.add("Chicken Tenders and Fries");
        dinner.add("Club Sandwich");
        dinner.add("Cold Meat Plate with Multigrain Roll");
        dinner.add("Delux Pizza");
        dinner.add("Denver Omlette with Texas Toast");
        dinner.add("Egg Salad Sandwich");
        dinner.add("Fish and Chips");
        dinner.add("French Dip");
        dinner.add("Grilled Cheese Sandwich");
        dinner.add("Grilled Ham and Cheese");
        dinner.add("Ham Salad Sandwich");
        dinner.add("Ham Sandwich");
        dinner.add("Ham Swiss Cheese and Tomato Melt");
        dinner.add("Hot Dog");
        dinner.add("Hot Dog or Chilli Dog with French Fries");
        dinner.add("Hot Pastrami Sandwich");
        dinner.add("Hot Roast Beef");
        dinner.add("Macaroni and Beef Casserole");
        dinner.add("Macaroni and Cheese");
        dinner.add("Meatloaf Sandwich");
        dinner.add("Mini Subs");
        dinner.add("Open Faced Turkey");
        dinner.add("Philly Steak Sandwich");
        dinner.add("Pizza");
        dinner.add("Pork Fried Rice and Egg Roll");
        dinner.add("Quiche");
        dinner.add("Quiche Lorraine");
        dinner.add("Reuben Sandwich");
        dinner.add("Roast Beef Sandwich");
        dinner.add("Roast Pork Sandwich");
        dinner.add("Salmon Salad Sandwich");
        dinner.add("Scalloped Potatoes and Ham");
        dinner.add("Shepherds Pie");
        dinner.add("Sloppy Joes");
        dinner.add("Smoked Turkey Sandwich");
        dinner.add("Spaghetti");
        dinner.add("Stew");
        dinner.add("Tater Tot Hot Dish");
        dinner.add("Toasted Tuna Salad Sandwich");
        dinner.add("Tuna Salad Sandwich");
        dinner.add("Turkey Noodle Casserole");
        dinner.add("Turkey Salad Sandwich");
        dinner.add("Turkey Tomato and Swiss");
            return dinner;
        }
        private ArrayList<String> parseDinnerSoup() {
            ArrayList<String> dinner = new ArrayList<>();
        dinner.add("Bean and Bacon");
        dinner.add("Beef Barley");
        dinner.add("Beef Consomme");
        dinner.add("Beef Noodle");
        dinner.add("Beef Vegetable");
        dinner.add("Broccoli Cheese");
        dinner.add("Cauliflower and Cheese");
        dinner.add("Chicken Florentine");
        dinner.add("Chicken Gumbo ");
        dinner.add("Chicken Noodle ");
        dinner.add("Chicken Vegetable ");
        dinner.add("Chinese Egg Drop ");
        dinner.add("Chunky Tomato ");
        dinner.add("Clam Chowder ");
        dinner.add("Corn Chowder ");
        dinner.add("Cream of Broccoli ");
        dinner.add("Cream of Carrot ");
        dinner.add("Cream of Mushroom ");
        dinner.add("Cream of Tomato ");
        dinner.add("Creamy Chicken Noodle ");
        dinner.add("Ham and Bean ");
        dinner.add("Hearty Vegetable ");
        dinner.add("Italian Wedding ");
        dinner.add("Lentil ");
        dinner.add("Minestrone with Baby Pasta Shells ");
        dinner.add("Muligataway ");
        dinner.add("Potato ");
        dinner.add("Potato and Leek ");
        dinner.add("Potato Sausage ");
        dinner.add("Roasted Vegetable and Garlic ");
        dinner.add("Santa Fe Fiesta ");
        dinner.add("Sausage and Bean ");
        dinner.add("Scotch Broth ");
        dinner.add("Split Pea ");
        dinner.add("Split Pea and Ham ");
        dinner.add("Tomato Rice ");
        dinner.add("Turkey Rice ");
        dinner.add("Vegetable ");
        dinner.add("Vegetable Noodle ");
        dinner.add("Vegetable Pasta ");
        dinner.add("Yellow Split Pea ");
            return dinner;
        }
        private ArrayList<String> parseDinnerGarnish() {
            ArrayList<String> dinner = new ArrayList<>();
        dinner.add(" B and B Pickles ");
        dinner.add(" Baked Beans ");
        dinner.add(" Bread and Butter Pickle Slices ");
        dinner.add(" Burger Garnish ");
        dinner.add(" Caesar Salad ");
        dinner.add(" Caesar Salad and Bun ");
        dinner.add(" Carrots and Celery Sticks ");
        dinner.add(" Chips and Salsa ");
        dinner.add(" Coleslaw ");
        dinner.add(" Coleslaw with Whole Wheat Biscuit or Bun ");
        dinner.add(" Dill Pickle ");
        dinner.add(" Dill Pickle Slice ");
        dinner.add(" Egg Roll ");
        dinner.add(" French Fries ");
        dinner.add(" Fruit ");
        dinner.add(" Fruit Slices ");
        dinner.add(" Fuit/Cottage Cheese ");
        dinner.add(" Half Dill Pickle ");
        dinner.add(" Imitation Crab Salad ");
        dinner.add(" Macaroni Salad ");
        dinner.add(" Mashed and Gravy ");
        dinner.add(" Melon Wedge ");
        dinner.add(" Orange Slices ");
        dinner.add(" Oranges ");
        dinner.add(" Pea Salad ");
        dinner.add(" Pickled Beet Slices ");
        dinner.add(" Potato Chips ");
        dinner.add(" Potato Salad ");
        dinner.add(" Salad ");
        dinner.add(" Sausage Chunks ");
        dinner.add(" Seasonal Fruit ");
        dinner.add(" Seasonal Vegetables ");
        dinner.add(" Side Garden Salad ");
        dinner.add(" Side Green Salad ");
        dinner.add(" Side Potato Salad ");
        dinner.add(" Side Salad ");
        dinner.add(" Side Salad Orange Slices ");
        dinner.add(" Sliced Fruit ");
        dinner.add(" Tomato and Cucumber Slices ");
        dinner.add(" Tomato and Dill Pickle ");
        dinner.add(" Tossed Salad ");
        dinner.add(" Vegetable Slices ");
        dinner.add(" Vegetable Sticks and Dip ");
            return dinner;
        }
        private ArrayList<String> parseDinnerDessert() {
            ArrayList<String> dinner = new ArrayList<>();
        dinner.add(" Apple Blueberry Crumble ");
        dinner.add(" Apple Cake ");
        dinner.add(" Apple Crisp ");
        dinner.add(" Apple Spice Cake ");
        dinner.add(" Baked Custard ");
        dinner.add(" Banana Pudding ");
        dinner.add(" Berries and Whipped Cream ");
        dinner.add(" Blonde Brownie ");
        dinner.add(" Broken Glass ");
        dinner.add(" Brownies ");
        dinner.add(" Butterscotch Pudding ");
        dinner.add(" Chocolate Cake ");
        dinner.add(" Chocolate Mousse ");
        dinner.add(" Chocolate Pudding ");
        dinner.add(" Coconut Pudding ");
        dinner.add(" Cookie ");
        dinner.add(" Diced Pears with Cookie ");
        dinner.add(" Double Deck Delight ");
        dinner.add(" Fruit Cocktail in Jell-O ");
        dinner.add(" Ice Cream ");
        dinner.add(" Ice Cream Sundaes ");
        dinner.add(" Ice Cream with Cookie ");
        dinner.add(" Jell-O Whip ");
        dinner.add(" Lemon Bars ");
        dinner.add(" Lemon Meringue Tarts ");
        dinner.add(" Mandarin Fiesta ");
        dinner.add(" Peach Crisp ");
        dinner.add(" Peaches ");
        dinner.add(" Pineapple Delight ");
        dinner.add(" Pineapple Tidbits ");
        dinner.add(" Pineapple with Cookie ");
        dinner.add(" Rainbow Jell-O with Whipped Topping ");
        dinner.add(" Rainbow Sherbet ");
        dinner.add(" Raspberry Chiffon ");
        dinner.add(" Raspberry Fluff ");
        dinner.add(" Rhubarb Crisp ");
        dinner.add(" Rice Krispee ");
        dinner.add(" Selection ");
        dinner.add(" Selection/Fruited Salad ");
        dinner.add(" Selection/Jell-O ");
        dinner.add(" Sherbet ");
        dinner.add(" Sherbet with Wafers ");
        dinner.add(" Sherbet/Cookie ");
        dinner.add(" Sliced Peach/Cookie ");
        dinner.add(" Strawberry Mousse ");
        dinner.add(" Tapioca Pudding ");
        dinner.add(" Upside Down Cake ");
        dinner.add(" Vanilla Mousse ");
            return dinner;
        }

    public boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
