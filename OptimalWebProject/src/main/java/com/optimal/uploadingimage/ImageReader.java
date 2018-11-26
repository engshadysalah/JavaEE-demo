/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.uploadingimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class ImageReader extends HttpServlet {

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

        try {
            //http://localhost:8080/OptimalWebProject/ImageReader?img=img_lights.jpg48995.78555912372

            ServletContext context = request.getServletContext();
            String imagePath = context.getRealPath("/");

            String imageName = "" + request.getParameter("img");//(String) request.getSession().getAttribute("imageName");
            //   String imageName="imagename from db";

            System.out.println("real paaaaaaaaaaaath of img" + imagePath + imageName);

            String containtType = getServletContext().getMimeType(imageName);

            System.out.println("Countent Type" + containtType);

            response.setContentType(containtType);
///home/shady/(1)Optimal/(3)Work/OldNetBeansProjects/JavaApplication5/OptimalWebProject/target/OptimalWebProject-1.0-SNAPSHOT/images.jpeg85683.10708260514
            FileInputStream in = new FileInputStream(new File(imagePath + "WEB-INF/" + imageName));

            OutputStream out = response.getOutputStream();

            byte[] buffer = new byte[1024];

            int lenth;

            while ((lenth = in.read(buffer)) > 0) {

                out.write(buffer, 0, lenth);

            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

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
