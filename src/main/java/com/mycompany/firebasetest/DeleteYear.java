/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firebasetest;

import com.firebase.client.Firebase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gil
 */
@WebServlet(name = "DeleteYear", urlPatterns = {"/DeleteYear"})
public class DeleteYear extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
       
        Firebase ref = new Firebase("https://sweltering-heat-3046.firebaseio.com");
        String Year = request.getParameter("year");
        String Building = request.getParameter("building");
        String endYear = request.getParameter("endYear");
        if (endYear.equals(""))
            endYear = Year;
        
//        System.out.println(Year);
//        System.out.println(Building);
//        System.out.println(endYear);
        
        for (Integer year = Integer.parseInt(Year); year <= Integer.parseInt(endYear); year++) {
        Firebase ref1 = ref.child(Building).child(year.toString());
        ref1.removeValue();
        }
        String message = "";
        if (endYear.equals(Year))
            message = "Successfully removed year " + Year + " from " + Building;
        else 
            message = "Successfully removed years " + Year + "-" + endYear + " from " + Building;
        request.setAttribute("message1", message);
        request.getRequestDispatcher("index.jsp").forward(request, response);

        
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
