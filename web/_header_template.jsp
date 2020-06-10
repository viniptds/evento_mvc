
<h1>${configuracao.titulo}</h1>
    
<div class="menu">    
    <p>Logado como 
            <span><b>
                    ${configuracao.log}
                </b></span>
        </p>
    </c:if>
    <p> 
        <a href="ApplicationController?action=logout">Sair</a>
    </p>

</div>