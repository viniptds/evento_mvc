<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="erros" class="util.Erros" scope="request"/>
<jsp:useBean id="configuracao" class="util.ConfigPagina" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>${configuracao.titulo}</title>
    </head>
    <body>
        <div class="p-2">
            <h1>${configuracao.titulo}</h1>
            <div class="menu">
                <jsp:include page="index.jsp" />
            </div>
            <c:if test="${erros.possuiMensagem}">
                <div class="bg-warning p-1 border border-danger">
                    <p class="h3 text-center">Erros</p>
                    <ul>
                        <c:forEach var="msg" items="${erros.mensagens}">
                            <li>${msg}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <jsp:include page="${configuracao.includeURL}"/>

        </div>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>