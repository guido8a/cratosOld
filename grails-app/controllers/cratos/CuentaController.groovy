package cratos

import org.springframework.dao.DataIntegrityViolationException

class CuentaController extends cratos.seguridad.Shield {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", delete: "GET"]

    def index() {
        redirect(action: "list", params: params)
    }

    def cuentaResultados() {
//        def cuentas = Cuenta.findAllByNumeroLikeAndMovimiento("3%", "1", [sort: "numero"])
////        Cuenta.findAll("from Cuenta where numero like '3%' and movimiento='1' order by numero ")
//        def cuentaS = Cuenta.findByResultadoAndEmpresa("S", session.empresa)
//        def cuentaD = Cuenta.findByResultado("D", session.empresa)

        def cuentas = Cuenta.withCriteria {
            ilike("numero", "3%")
            eq("movimiento", "1")
            eq("empresa", session.empresa)
        }

        def cuentaS = Cuenta.findByResultadoAndEmpresa("S", session.empresa)
        def cuentaD = Cuenta.findByResultadoAndEmpresa("D", session.empresa)

        return [cuentas: cuentas, cuentaS: cuentaS, cuentaD: cuentaD]
    }

    def grabarCuentaResultado() {
//        println "grabar cuentas resultado " + params

        def supOld = Cuenta.findByResultadoAndEmpresa("S", session.empresa)
        def deficitOld = Cuenta.findByResultadoAndEmpresa("D", session.empresa)

        def sup = Cuenta.get(params.super)
        if (sup.id != supOld.id) {
            supOld.resultado = null
            sup.resultado = "S"
            if (!sup.save(flush: true)) {
                println sup.errors
            }
            if (!supOld.save(flush: true)) {
                println supOld.errors
            }
        }

        def deficit = Cuenta.get(params.deficit)
        if (deficit.id != deficitOld.id) {
            deficitOld.resultado = null
            deficit.resultado = "D"
            if (!deficit.save(flush: true)) {
                println deficit.errors
            }
            if (!deficitOld.save(flush: true)) {
                println deficitOld.errors
            }
        }
        flash.message = "Datos guardados"
        redirect(action: 'cuentaResultados')
    }


    def loadForm() {

//        println ">>>>  " + params

        def padreId = params.padre
        def id = params.id

        def cuenta = new Cuenta()
        def padre

        if (id) {
//            println "1"
            cuenta = Cuenta.get(id)
            padre = cuenta.padre
        }
        if (padreId) {
//            println "2"
            padre = Cuenta.get(padreId)
            cuenta.padre = padre
        }

//        println cuenta
//        println cuenta.nivel

        return [cuentaInstance: cuenta]
    }

    def loadTreePart() {
        render(makeTreeNode(params.id))
    }

    String makeTreeNode(id) {
        String tree = "", clase = "", rel = ""
        Cuenta padre
        Cuenta[] hijos

        if (id == "#") {
            //root
            def hh = Cuenta.countByNivelAndEmpresa(Nivel.get(1), session.empresa, [sort: "numero"])
            if (hh > 0) {
                clase = "hasChildren jstree-closed"
            }
            tree = "<li id='root' class='root ${clase}' data-jstree='{\"type\":\"root\"}' level='0' ><a href='#' class='label_arbol'>Plan de cuentas</a></li>"
        } else if (id == "root") {
            hijos = Cuenta.findAllByNivelAndEmpresa(Nivel.get(1), session.empresa, [sort: "numero"])
        } else {

            def parts = id.split("_")
            def node_id = parts[1].toLong()

            padre = Cuenta.get(node_id)
            if (padre) {
                hijos = Cuenta.findAllByPadreAndEmpresa(padre, session.empresa, [sort: "numero"])
            }
        }

        if (tree == "" && (padre || hijos.size() > 0)) {
            tree += "<ul>"

            hijos.each { hijo ->
                def hijosH = Cuenta.findAllByPadreAndEmpresa(hijo, session.empresa, [sort: "numero"])

                def gestores = Genera.findAllByCuenta(hijo)
                def asientos = Asiento.findAllByCuenta(hijo)

                clase = (hijosH.size() > 0) ? "jstree-closed hasChildren" : ""
                rel = (hijosH.size() > 0) ? "padre" : "hijo"

                if (hijosH.size() > 0 || gestores.size() > 0 || asientos.size() > 0) {
                    clase += " ocupado "
                    if (gestores.size() > 0) {
                        clase += " conGestores "
                    }
                    if (asientos.size() > 0) {
                        clase += " conAsientos "
                    }
                }

                tree += "<li id='li_" + hijo.id + "' class='" + clase + "' data-jstree='{\"type\":\"${rel}\"}' level='" + hijo.nivel.id + "'>"
                tree += "<a href='#' class='label_arbol'>" + hijo + "</a>"
                tree += "</li>"
            }

            tree += "</ul>"
        }

        return tree
    }

    def ajaxSearch() {
//        println "ajax search"
//        println params
        def search = params.search_string
        def ret = "["
        def cuentas
        if (search.size() >= 3) {
            ret += '"#root",'
//            println "BUSCA"
            cuentas = Cuenta.findAllByDescripcionIlikeOrNumeroIlike("%" + search + "%", "%" + search + "%")
//            println "cuentas: " + cuentas
            cuentas.each { cuenta ->
                def p = cuenta.padre
                while (p) {
                    ret += '"#li_' + p.id + '",'
//                    println "\t" + ret
                    p = p.padre
                }
//                ret += '"#li_' + cuenta.id + '",'
//                println ret
            }

            if (ret != "[") {
                ret = ret[0..ret.size() - 2]
            }
        }
        ret += "]"
//        println ret
        render ret
    }

    def list() {
        def hh = Cuenta.countByNivelAndEmpresa(Nivel.get(1), session.empresa, [sort: "numero"])
        return [hh: hh]
    }

    def list_loadAll() {
        def lvl1 = Cuenta.findAllByNivel(Nivel.get(1), [sort: "numero"]).id

        def res = ""
        res += "<ul>"
        lvl1.each {
            res += imprimeHijos(it)
        }
        res += "</ul>"

        return [res: res]
    }

    def imprimeHijos(padre) {
        def band = true
        def t = ""
        def txt = ""

        def cuenta = Cuenta.get(padre)
        def valor = 0
        def l = Cuenta.findAllByPadre(cuenta);
        l.each {
            band = false;
            t += imprimeHijos(it.id)
        }

        if (!band) {
            def clase = "jstree-open"
            if (cuenta.nivel.id >= 3) {
                clase = "jstree-closed"
            }

            if (l.size() > 0) {
                clase += " hasChildren "
            }

            txt += "<li id='li_" + cuenta.id + "' class='padre " + clase + "' rel='padre'>"
            txt += "<a href='#' class='label_arbol'>" + cuenta + "</a>"
            txt += "<ul>"
            txt += t
            txt += "</ul>"
        } else {
//            println "si band"
            txt += "<li id='li_" + cuenta.id + "' class='hijo jstree-leaf' rel='hijo'>"
            txt += "<a href='#' class='label_arbol'>" + cuenta + "</a>"
        }
        txt += "</li>"
        return txt
    }

    def create() {
        [cuentaInstance: new Cuenta(params)]
    }

    def saveAjax() {
        if (!params.estado) {
            params.estado = 'A'
        }

        println "***" + params

        if (params.impuesto.id) {
            params.retencion = "S"
        }

        def cuentaInstance = new Cuenta(params)

        if (params.id) {
            cuentaInstance = Cuenta.get(params.id)
            cuentaInstance.properties = params
        }
        cuentaInstance.empresa = Empresa.get(session.empresa.id)

        if (!cuentaInstance.save(flush: true)) {
            println cuentaInstance.errors
            render "NO_Error al guardar. Por favor espere...."
        } else {
            def padre = cuentaInstance.padre
            if (padre) {
                padre.movimiento = 0
                padre.auxiliar = 'N'
                if (!padre.save(flush: true)) {
                    println "error al poner movimiento=0 en el padre con id " + padre.id
                }
            }
            if (params.id) {
                render "OK_Cuenta actualizada. Por favor espere...."
            } else {
                render "OK_Cuenta creada. Por favor espere...."
            }
        }
    }

    def save() {
        def cuentaInstance = new Cuenta(params)

        if (params.id) {
            cuentaInstance = Cuenta.get(params.id)
            cuentaInstance.properties = params
        }

        cuentaInstance.empresa = Empresa.get(session.empresa.id)

        if (!cuentaInstance.save(flush: true)) {
            if (params.id) {
                render(view: "edit", model: [cuentaInstance: cuentaInstance])
            } else {
                render(view: "create", model: [cuentaInstance: cuentaInstance])
            }
            return
        }

        if (params.id) {
            flash.message = "Cuenta actualizado"
            flash.clase = "success"
            flash.ico = "ss_accept"
        } else {
            flash.message = "Cuenta creado"
            flash.clase = "success"
            flash.ico = "ss_accept"
        }
        redirect(action: "show", id: cuentaInstance.id)
    }

    def show() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
            flash.message = "No se encontró Cuenta con id " + params.id
            flash.clase = "error"
            flash.ico = "ss_delete"
            redirect(action: "list")
            return
        }

        [cuentaInstance: cuentaInstance]
    }

    def edit() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
            flash.message = "No se encontró Cuenta con id " + params.id
            flash.clase = "error"
            flash.ico = "ss_delete"
            redirect(action: "list")
            return
        }

        [cuentaInstance: cuentaInstance]
    }

    def deleteCuenta() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
            render("NO_" + "No se encontró cuenta con id " + params.id)
            return
        }

        try {
            cuentaInstance.delete(flush: true)
            render("OK")
        }
        catch (DataIntegrityViolationException e) {
            render("NO_" + "No se pudo eliminar la cuenta con id " + params.id)
        }
    }

    def cargarCuentas() {
        def file = new File("/home/svt/cuentas4-9.csv")
        def empresa = Empresa.get(1)
        file.eachLine {
            def parts = it.split("&")

            // println "parts "+parts
            def cuenta = new Cuenta()
            cuenta.auxiliar = "S"
            cuenta.numero = parts[1].trim()
            cuenta.descripcion = parts[0].trim()
            if (parts[0].trim() == "")
                cuenta.descripcion = "CAMBIAR DESCRIPCION"
            cuenta.estado = "A"
            if (parts[2]) {
                cuenta.padre = Cuenta.findByNumero(parts[2].trim())
                if (!cuenta.padre) {
                    println "no encontro padre wtf"
                }
            } else {
                cuenta.padre = null
            }
            cuenta.movimiento = parts[4]
            cuenta.empresa = empresa
            cuenta.nivel = Nivel.get(Math.ceil(parts[1].size() / 2))
            if (!cuenta.save(flush: true)) {
                println "error " + cuenta.errors
            } else {
                println "save " + cuenta
            }
        }
    }


    def crearDefaults() {
        def emp = Empresa.get(1)
        recursivoCuentas2(null, null, emp, null)
        render "DONE"
    }

    def recursivoCuentas2(padreOrigen, padreDestino, empresaOrigen, empresaDestino) {
        def originales
        if (padreOrigen) {
            if (empresaOrigen) {
                originales = Cuenta.findAllByPadreAndEmpresa(padreOrigen, empresaOrigen)
            } else {
                originales = Cuenta.findAllByPadreAndEmpresaIsNull(padreOrigen)
            }
        } else {
            if (empresaOrigen) {
                originales = Cuenta.findAllByNivelAndEmpresa(Nivel.get(1), empresaOrigen)
            } else {
                originales = Cuenta.findAllByNivelAndEmpresaIsNull(Nivel.get(1))
            }
        }

        originales.each { cuenta ->
            def esp = ""
            (cuenta.nivelId - 1).times {
                esp += "\t"
            }
            println esp + cuenta
            def nueva = new Cuenta()
            nueva.properties = cuenta.properties
            if (empresaDestino) {
                nueva.empresa = empresaDestino
            } else {
                nueva.empresa = null
            }
            if (padreDestino) {
                nueva.padre = padreDestino
            } else {
                nueva.padre = null
            }
            if (nueva.save(flush: true)) {
                recursivoCuentas2(cuenta, nueva, empresaOrigen, empresaDestino)
            } else {
                println nueva.errors
            }
        }
    }

    def copiarCuentas() {
        def empresa = Empresa.get(session.empresa.id)

        if (Cuenta.countByEmpresa(empresa) == 0) {
            recursivoCuentas2(null, null, null, empresa)
        }
        redirect(action: 'list')
    }

    def show_ajax() {
        if (params.id) {
            def cuentaInstance = Cuenta.get(params.id)
            if (!cuentaInstance) {
                notFound_ajax()
                return
            }
            return [cuentaInstance: cuentaInstance]
        } else {
            notFound_ajax()
        }
    } //show para cargar con ajax en un dialog

    def form_ajax() {
        def cuentaInstance = new Cuenta(params)
        def hijos = 0
        if (params.id) {
            cuentaInstance = Cuenta.get(params.id)
            if (!cuentaInstance) {
                notFound_ajax()
                return
            }
            hijos = Cuenta.countByPadreAndEmpresa(cuentaInstance, session.empresa)
        } else {
            cuentaInstance.padre = Cuenta.get(params.padre)
            cuentaInstance.nivel = Nivel.get(params.lvl.toInteger() + 1)
            def siblings = Cuenta.findAllByPadreAndEmpresa(cuentaInstance.padre, session.empresa, [sort: "numero"])
            if (siblings) {
                def last = siblings.last()
                def parts = last.numero.split("\\.")
                if (parts.size() > 0) {
                    def next = parts.last().toInteger() + 1
                    def num = ""
                    for (def i = 0; i < parts.size() - 1; i++) {
                        num += parts[i] + "."
                    }
                    num += next.toString().padLeft(2, "0")
                    cuentaInstance.numero = num
                }
            } else {
                cuentaInstance.numero = "1"
            }
        }
        return [cuentaInstance: cuentaInstance, hijos: hijos]
    } //form para cargar con ajax en un dialog


    def validarNumero_ajax() {
        params.numero = params.numero.toString().trim()
        if (params.id) {
            def cuenta = Cuenta.get(params.id)
            if (cuenta.numero == params.numero) {
                render true
                return
            } else {
                render Cuenta.countByNumeroAndEmpresa(params.numero, session.empresa) == 0
                return
            }
        } else {
            render Cuenta.countByNumeroAndEmpresa(params.numero, session.empresa) == 0
            return
        }
    }

    def save_ajax() {

        params.each { k, v ->
            if (v != "date.struct" && v instanceof java.lang.String) {
                params[k] = v.toUpperCase()
            }
        }
        params.estado = 'A'
        params.empresa = session.empresa
        def cuentaInstance = new Cuenta()
        if (params.id) {
            cuentaInstance = Cuenta.get(params.id)
            if (!cuentaInstance) {
                notFound_ajax()
                return
            }
        } //update

        cuentaInstance.properties = params

        if (!cuentaInstance.save(flush: true)) {
            def msg = "NO_No se pudo ${params.id ? 'actualizar' : 'crear'} Cuenta."
            msg += renderErrors(bean: cuentaInstance)
            render msg
            return
        }
        render "OK_${params.id ? 'Actualización' : 'Creación'} de Cuenta exitosa."
    } //save para grabar desde ajax

    def delete_ajax() {
        if (params.id) {
            def cuentaInstance = Cuenta.get(params.id)
            if (cuentaInstance) {
                try {
                    cuentaInstance.delete(flush: true)
                    render "OK_Eliminación de Cuenta exitosa."
                } catch (e) {
                    render "NO_No se pudo eliminar Cuenta."
                }
            } else {
                notFound_ajax()
            }
        } else {
            notFound_ajax()
        }
    } //delete para eliminar via ajax

    protected void notFound_ajax() {
        render "NO_No se encontró Cuenta."
    } //notFound para ajax

}
