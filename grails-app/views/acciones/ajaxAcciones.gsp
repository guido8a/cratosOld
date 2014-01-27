<style type="text/css">
th, td {
    padding: 2px !important;
}
</style>

<g:form action="grabar" method="post">
    <!-- <div style="height: 400px; overflow: auto;"> -->
    <input type="hidden" id="mdlo__id" value="${mdlo__id}">
    <input type="hidden" id="tpac__id" value="${tpac__id}">
    <g:if test="${datos?.size() > 0}">
        <div class="ui-corner-all"
             style="height: 480px;overflow: auto; margin-bottom: 5px; background-color: #efeff8;
             border-style: solid; border-color: #AAA; border-width: 1px; ">
            <table border="0" cellpadding="0" width="800px" class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <g:each in="${titulos}" var="tt">
                            <th style="padding:4px;" width="100px">${tt[0]}</th>
                            <th width="150px">${tt[1]}</th>
                            <th width="300px">${tt[2]}</th>
                            <th width="200px">${tt[3]}</th>
                        </g:each>
                    </tr>
                </thead>
                <tbody>
                <!-- <hr>Hola ${lista}</hr> -->
                    <g:each in="${datos}" status="i" var="d">
                        <tr>
                            <td><input type="checkbox" name="cdgo" class="chkAccn" value="${d[0].encodeAsHTML()}"></td>
                            <td>${d[1]}</td>
                            <td><input type="text" id="mn${d[0]}" value="${d[2]?.encodeAsHTML()}">
                                <input class="ok btn btn-success" type="button" id="${d[0]}" value="Grabar"></td>
                            <td>${d[3]?.encodeAsHTML()}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </div>
    </g:if>
    <!-- modulo:${mdlo__id} -->
    <g:if test="${mdlo__id == '0'}">
        Enviar al módulo:
        <g:select optionKey="id" from="${cratos.seguridad.Modulo.list()}" name="modulo" value="${modulo?.id}"></g:select>
        <input id="mueveAJX" type="button" class="grabaPrms" value="Agregar al Módulo">
    </g:if>
    <g:else>
        <input id="aceptaAJX" type="button" class="grabaPrms" value="Eliminar del Módulo">
    </g:else>
    <input id="cambia" type="button" class="grabaPrms" value="Cambiar Menú <- -> Proceso">
%{--<input id="borraAccn" type="button" class="grabaPrms" value="Eliminar la Acci&oacute;n">--}%
</g:form>

<script type="text/javascript">

    $(".ok").click(function () {
        //var datos = armar()
        var id = $(this).attr('id');
        //alert("dscr=" + $('#mn'+id).val() + ", id de la accion=" + id)
        $.ajax({
            type    : "POST", url : "${createLink(controller:'acciones', action:'grabaAccn')}",
            data    : "dscr=" + $('#mn' + id).val() + "&id=" + id,
            success : function (msg) {
                alert(msg)
            }
        });
    })

</script>