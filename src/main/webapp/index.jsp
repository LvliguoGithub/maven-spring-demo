<%
    String path = request.getContextPath();
    String redirectURL = path + "/home";
    response.sendRedirect(redirectURL);
%>
