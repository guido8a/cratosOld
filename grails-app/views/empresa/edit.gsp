<g:form name="frm-empresa" action="save" method="post" >
    <g:hiddenField name="id" value="${empresaInstance?.id}" />
    <g:hiddenField name="version" value="${empresaInstance?.version}" />
    <fieldset class="form">
        <g:render template="form"/>
    </fieldset>
</g:form>

<script type="text/javascript">
    $(function() {
        $("#frm-empresa").validate();

        $("#frm-empresa").find("input, select, textarea").keypress(function (evt) {
            if (evt.keyCode == 13) {
                submitForm();
                return false;
            }
            return true;
        });
    });
</script>
