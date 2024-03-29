<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- los favicon de todos los tamaños -->
        <elm:favicon/>

        <title><g:layoutTitle default="cratos"/></title>

        <!-- Bootstrap core CSS -->
        %{--<link href="${resource(dir: 'bootstrap-3.0.1/css', file: 'bootstrap.happy1.css')}" rel="stylesheet">--}%
        <link href="${resource(dir: 'bootstrap-3.0.1/css', file: 'bootstrap.happy1.css')}" rel="stylesheet">

        <!-- FontAwsome y mFizz: las fuentes con dibujitos para los iconos -->
        <link href="${resource(dir: 'font-awesome-4.0.3/css', file: 'font-awesome.min.css')}" rel="stylesheet">
        <link href="${resource(dir: 'font-mfizz-1.2', file: 'font-mfizz.css')}" rel="stylesheet">

        <!-- JQuery -->
        <script src="${resource(dir: 'js/jquery/js', file: 'jquery-1.9.1.js')}"></script>
        <script src="${resource(dir: 'js/jquery/js', file: 'jquery-ui-1.10.3.custom.min.js')}"></script>
        <link href="${resource(dir: 'js/jquery/css/ui-lightness', file: 'jquery-ui-1.10.3.custom.min.css')}" rel="stylesheet">

        <!-- funciones de JS -->
        <!-- funciones de strings y formats sacados de internet (pad, starts y ends with, capitalize, format_number, str_replace) -->
        <script src="${resource(dir: 'js', file: 'functions.js')}"></script>
        <!-- funciones custom (validar int, validar dec, mensajes) -->
        <script src="${resource(dir: 'js', file: 'funciones.js')}"></script>
        <script src="${resource(dir: 'js', file: 'loader.js')}"></script>

        <!-- plugins -->
        <!-- para los alerts y confirms y dialogs -->
        <script src="${resource(dir: 'js/plugins/bootbox/js', file: 'bootbox.js')}"></script>

        %{--<!-- el datepicker -->--}%
        <script src="${resource(dir: 'js/plugins/bootstrap-datepicker/js', file: 'bootstrap-datepicker.js')}"></script>
        <script src="${resource(dir: 'js/plugins/bootstrap-datepicker/js/locales', file: 'bootstrap-datepicker.es.js')}"></script>
        <link href="${resource(dir: 'js/plugins/bootstrap-datepicker/css', file: 'datepicker.css')}" rel="stylesheet">

        %{--<!-- el datetime picker no vale bien -->--}%
        %{--<script src="${resource(dir: 'js/plugins/bootstrap-datetimepicker/js', file: 'bootstrap-datetimepicker.min.js')}"></script>--}%
        %{--<link href="${resource(dir: 'js/plugins/bootstrap-datetimepicker/css', file: 'bootstrap-datetimepicker.min.css')}" rel="stylesheet">--}%

        <!-- lo q muestra la cantidad restante de caracteres en los texts -->
        <script src="${resource(dir: 'js/plugins/bootstrap-maxlength/js', file: 'bootstrap-maxlength.js')}"></script>

        <!-- la validacion del lado del cliente -->
        <script src="${resource(dir: 'js/plugins/jquery-validation-1.11.1/js', file: 'jquery.validate.min.js')}"></script>
        <script src="${resource(dir: 'js/plugins/jquery-validation-1.11.1/localization', file: 'messages_es.js')}"></script>
        <script src="${resource(dir: 'js', file: 'jquery.validate.custom.js')}"></script>

        <!-- las alertas growl -->
        <script src="${resource(dir: 'js/plugins/pines/js', file: 'jquery.pnotify.js')}"></script>
        <link href="${resource(dir: 'js/plugins/pines/css', file: 'jquery.pnotify.default.css')}" rel="stylesheet">

        <!-- context menu para el click derecho -->
        <script src="${resource(dir: 'js/plugins/context/js', file: 'context.js')}"></script>
        <link href="${resource(dir: 'js/plugins/context/css', file: 'context.css')}" rel="stylesheet">

        <!-- el timer para cerrar la sesion -->
        <script src="${resource(dir: 'js/plugins/jquery.countdown', file: 'jquery.countdown.min.js')}"></script>
        <link href="${resource(dir: 'js/plugins/jquery.countdown', file: 'jquery.countdown.css')}" rel="stylesheet">

        <link href='${resource(dir: "font/open", file: "stylesheet.css")}' rel='stylesheet' type='text/css'>
        <link href='${resource(dir: "font/tulpen", file: "stylesheet.css")}' rel='stylesheet' type='text/css'>

        <!-- el manager de fechas -->
        <script src="${resource(dir: 'js/plugins', file: 'date.js')}"></script>
        %{--<script src="${resource(dir: 'js/plugins/homeycombs', file: '/js/jquery.homeycombs.js')}"></script>--}%

        <!-- Custom styles -->
        <link href="${resource(dir: 'css', file: 'custom.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/loader.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/modals.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/tablas.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/datepicker.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/context.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'custom/forms.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'CustomSvt.css')}" rel="stylesheet">
        <link href="${resource(dir: 'css', file: 'footer.css')}" rel="stylesheet">
        %{--Buscador--}%
        <link href='${resource(dir: 'css', file: 'buscador.css')}' rel='stylesheet'>
        %{--<link href='${resource(dir: '/js/plugins/homeycombs/css', file: 'homeycombs.css')}' rel='stylesheet'>--}%

        <script type="text/javascript">
            var spinner24Url = "${resource(dir:'images/spinners', file:'spinner_24.GIF')}";
            var spinner64Url = "${resource(dir:'images/spinners', file:'spinner_64.GIF')}";
            var spinnerSquare64Url = "${resource(dir: 'images/spinners', file: 'loading_new.GIF')}";

            var spinner = $("<img src='" + spinner24Url + "' alt='Cargando...'/>");
            var spinner64 = $("<img src='" + spinner64Url + "' alt='Cargando...'/>");
            var spinnerSquare64 = $("<img src='" + spinnerSquare64Url + "' alt='Cargando...'/>");
        </script>

        <g:layoutHead/>

    </head>

    <body>

        <div id="modalTabelGray"></div>

        <div id="modalDiv" class="ui-corner-all">
            <div class="loading-title">Procesando</div>
            <img src="${resource(dir: 'images/spinners', file: 'loading_new.GIF')}">

            <div class="loading-footer">Espere por favor</div>
        </div>
        <mn:menu title="${g.layoutTitle(default: 'cratos')}"/>

        <div class="container" style="min-width: 1000px !important;">
            <g:layoutBody/>
        </div> <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
        <script src="${resource(dir: 'bootstrap-3.0.1/js', file: 'bootstrap.min.js')}"></script>

        <!-- funciones de ui (tooltips, maxlength, bootbox, contextmenu, validacion en keydown para los numeros) -->
        <script type="text/javascript" src="${resource(dir: 'js', file: 'ui.js')}"></script>
        <script type="text/javascript">


            var ot = document.title;

            //    openLoader()
            //    openLoader("Con mensaje")

            function resetTimer() {
                var ahora = new Date();
                var fin = ahora.clone().add(20).minute();
                $("#countdown").countdown('option', {
                    until : fin
                });
                $(".countdown_amount").removeClass("highlight");
                document.title = ot;
            }

            function validarSesion() {
                $.ajax({
                    url     : '${createLink(controller: "login", action: "validarSesion")}',
                    success : function (msg) {
                        if (msg == "NO") {
                            location.href = "${g.createLink(controller: 'login', action: 'login')}";
                        } else {
                            resetTimer();
                        }
                    }
                });
            }

            function highlight(periods) {
                if ((periods[5] == 5 && periods[6] == 0) || (periods[5] < 5)) {
                    document.title = "Fin de sesión en " + (periods[5].toString().lpad('0', 2)) + ":" + (periods[6].toString().lpad('0', 2)) + " - " + ot;
                    $(".countdown_amount").addClass("highlight");
                }
            }

            $(function () {
                var ahora = new Date();
                var fin = ahora.clone().add(20).minute();

                $('#countdown').countdown({
                    until    : fin,
                    format   : 'MS',
                    compact  : true,
                    onExpiry : validarSesion,
                    onTick   : highlight
                });

                $(".btn-ajax").click(function () {
                    resetTimer();
                });
            });

        </script>

    </body>
</html>
