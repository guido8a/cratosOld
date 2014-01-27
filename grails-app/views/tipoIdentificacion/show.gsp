

<ol class="property-list tipoIdentificacion">
    
    <g:if test="${tipoIdentificacionInstance?.codigo}">
        <li class="fieldcontain">
            <span id="codigo-label" class="property-label"><g:message code="tipoIdentificacion.codigo.label" default="Codigo" /></span>
            
            <span class="property-value" aria-labelledby="codigo-label"><g:fieldValue bean="${tipoIdentificacionInstance}" field="codigo"/></span>
            
        </li>
    </g:if>
    
    <g:if test="${tipoIdentificacionInstance?.descripcion}">
        <li class="fieldcontain">
            <span id="descripcion-label" class="property-label"><g:message code="tipoIdentificacion.descripcion.label" default="Descripcion" /></span>
            
            <span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${tipoIdentificacionInstance}" field="descripcion"/></span>
            
        </li>
    </g:if>
    
    <g:if test="${tipoIdentificacionInstance?.tipoAnexo}">
        <li class="fieldcontain">
            <span id="tipoAnexo-label" class="property-label"><g:message code="tipoIdentificacion.tipoAnexo.label" default="Tipo Anexo" /></span>
            
            <span class="property-value" aria-labelledby="tipoAnexo-label"><g:fieldValue bean="${tipoIdentificacionInstance}" field="tipoAnexo"/></span>
            
        </li>
    </g:if>
    
</ol>
