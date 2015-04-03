/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firebasetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gil
 */
@WebServlet(name = "Test", urlPatterns = {"/Test"})
public class Test extends HttpServlet {

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
        
        String building = request.getParameter("building");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        building = building.replace(" ", "%20");

        System.out.println(building);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        building = building.trim();
        URL url = new URL("https://sweltering-heat-3046.firebaseio.com/" + building + "/" + year 
                + "/" + month + "/"+ day + "/.json?print=pretty");

        String temp = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                temp += line;
            }
        }
        temp = temp.replace("\"", "");
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        temp = temp.replace(",", ":");
        String[] temps = temp.split(":");
                
        for (int i = 0; i < temps.length;) {
            temps[i] = temps[i].trim();
            String mealName = temps[i];

            if (temps[i].equals("Breakfast") || temps[i].equals("Dinner") || temps[i].equals("Lunch")) {
                i++;
                HashMap<String, String> meal = new HashMap<>();

                for (; i < temps.length; i +=2) {
                    temps[i] = temps[i].trim();
                    temps[i+1] = temps[i+1].trim();

                    if (temps[i].equals("Dinner") || temps[i].equals("Lunch")) {
                        break;
                    }
                    meal.put(temps[i], temps[i+1]);

                }
                switch (mealName) {
                    case "Breakfast":
                        request.setAttribute("breakfast", meal);
                        break;
                    case "Lunch":
                        request.setAttribute("lunch", meal);
                        break;
                    case "Dinner":
                        request.setAttribute("dinner", meal);
                        break;
                }

            }
        }
                
        request.getRequestDispatcher("displayWeek.jsp").forward(request, response);
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
