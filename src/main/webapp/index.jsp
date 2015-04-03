<%-- 
    Document   : index
    Created on : Mar 25, 2015, 8:53:59 AM
    Author     : Gil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Meal Planner</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="view.css" type="text/css" rel="stylesheet" />
    </head>
    <body >
        <header>
            <h1>Meal Planner - Adage</h1>
        </header>
        <h1 style="text-align: center">Add a new Year</h1>
        <div style="text-align: center">
        <form action="createYear" method="post">
            <span style="font-size:24px">Building:</span>
            <select name="building">
                <option value="Fort Worth">Fort Worth</option>
                <option value="El Paso">El Paso</option>
                <option value="Amarillo">Amarillo</option>
                <option value="Artesia">Artesia</option>
                <option value="Carlsbad">Carlsbad</option>
                <option value="Hobbs">Hobbs</option>
                <option value="Los Lunas">Los Lunas</option>
                <option value="Lovington">Lovington</option>
                <option value="Snyder">Snyder</option>
            </select> <span style="margin-left: 25px"></span>
            <span style="font-size:24px">Begin Year:</span>
            <input type="text" style="font-size:20px" maxlength="4" placeholder="2016" name="year" required/>
            <span style="font-size:24px; margin-left: 20px">End Year:</span>
            <input type="text" style="font-size:20px" maxlength="4" placeholder="2020" name="endYear" />

            <input type="submit" name="submit" value="Add" style="font-size:24px" />
        </form>
        </div>
        <div>
            <p style="text-align: center; font-size: 32px">${message}</p>
        </div>
        
        
        <div style="text-align: center">
            <h1>Delete a year</h1>
            <form action="DeleteYear" method="POST">
                <span style="font-size: 24px">Building: </span>
                <select name="building">
                <option value="Fort Worth">Fort Worth</option>
                <option value="El Paso">El Paso</option>
                <option value="Amarillo">Amarillo</option>
                <option value="Artesia">Artesia</option>
                <option value="Carlsbad">Carlsbad</option>
                <option value="Hobbs">Hobbs</option>
                <option value="Los Lunas">Los Lunas</option>
                <option value="Lovington">Lovington</option>
                <option value="Snyder">Snyder</option>
            </select>
                </select> <span style="margin-left: 25px"></span>
            <span style="font-size:24px">Year to Delete:</span>
            <input type="text" style="font-size:20px" maxlength="4" placeholder="2016" name="year" required/>
<span style="font-size:24px; margin-left: 20px">End Year:</span>
            <input type="text" style="font-size:20px" maxlength="4" placeholder="2020" name="endYear" />

            <input type="submit" name="submit" value="Delete" style="font-size:24px" />
        
            </form>
            <p style="text-align: center; font-size: 32px">${message1}</p>
        </div>
        
         <div style="text-align:center; margin-top:20px; font-size: 18px">
            <b>Note: End year is not necessary. If no end year is specified, it will only handle the specified year.</b>
        </div>
        <div style="text-align: center">
            <h1>Look up a Day</h1>
            <form action="Test" method="POST">
                <span style="font-size: 24px">Building: </span>
                <select name="building">
                <option value="Fort Worth">Fort Worth</option>
                <option value="El Paso">El Paso</option>
                <option value="Amarillo">Amarillo</option>
                <option value="Artesia">Artesia</option>
                <option value="Carlsbad">Carlsbad</option>
                <option value="Hobbs">Hobbs</option>
                <option value="Los Lunas">Los Lunas</option>
                <option value="Lovington">Lovington</option>
                <option value="Snyder">Snyder</option>
            </select>
            <span style="margin-left: 25px; font-size:24px">Year: </span>
            <input type="text" name="year" style="font-size:20px"  maxlength="4" placeholder="2015" required/>
             <span style="font-size: 24px">Month: </span>
                <select name="month">
                <option value="January">January</option>
                <option value="February">February</option>
                <option value="March">March</option>
                <option value="April">April</option>
                <option value="May">May</option>
                <option value="June">June</option>
                <option value="July">July</option>
                <option value="August">August</option>
                <option value="September">September</option>
                <option value="October">October</option>
                <option value="November">November</option>
                <option value="December">December</option>

            </select>
            <span style="font-size:24px; margin-left: 25px">Day:</span>
            <input type="text" style="font-size:20px" maxlength="2" placeholder="13" name="day" required/>
                <input type="submit" value="Look Up" style="font-size:24px" />
            </form>
        </div>
    </body>
</html>
