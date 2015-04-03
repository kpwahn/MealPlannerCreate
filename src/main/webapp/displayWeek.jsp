<%-- 
    Document   : displayWeek
    Created on : Apr 3, 2015, 9:39:59 AM
    Author     : Gil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link href="view.css" type="text/css" rel="stylesheet" />

    </head>
    <body>
        <header>
            <h1>Meal Planner - Adage</h1>
            <a href="index.jsp">Back</a>
        </header>
        
        <table align="center">
            <tr>
                <th>Breakfast</th>
                <th>Lunch</th>
                <th>Dinner</th>
            </tr>
            <tr>
                <td>Entree: ${breakfast.Entree} <br />
                    Side 1: ${breakfast.Side1}  <br />
                    Side 2: ${breakfast.Side2}  <br />
                    Cereal: ${breakfast.Cereal} <br />
                    Fruit:  ${breakfast.Fruit}  <br />
                </td>
                <td>Entree:    ${lunch.Entree} <br />
                    Salad:     ${lunch.Salad}  <br />
                    Vegetable: ${lunch.Vegetable}  <br />
                    Starch:    ${lunch.Starch} <br />
                    Dessert:   ${lunch.Dessert}  <br />
                </td>
                <td>Entree:  ${dinner.Entree} <br />
                    Soup:    ${dinner.Soup}  <br />
                    Garnish: ${dinner.Garnish}  <br />
                    Dessert: ${dinner.Dessert} <br />
                </td>
            </tr>
        </table>
    </body>
</html>
