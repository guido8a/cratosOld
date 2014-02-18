package cratos
class RubroTipoContrato implements Serializable {
    double valor
    double porcentaje
    String editable
    String decimo
    String iess
    String gravable
    TipoContrato tipoContrato
    Rubro rubro
    String observaciones
    Empresa empresa
    static mapping = {
        table 'rbtc'
        cache usage: 'read-write', include: 'non-lazy'
        id column: 'rbtc__id'
        id generator: 'identity'
        version false
        columns {
            id column: 'rbtc__id'
            valor column: 'rbtcrovlor'
            porcentaje column: 'rbtcpcnt'
            editable column: 'rbtcedit'
            decimo column: 'rbtcdcmo'
            iess column: 'rbtciess'
            gravable column: 'rbtcgrvb'
            tipoContrato column: 'tpct__id'
            rubro column: 'rbro__id'
            observaciones column: 'rbtcobsr'
            empresa column: 'empr__id'
        }
    }
    static constraints = {
        tipoContrato(blank: false, nullable: false, attributes: [title: 'Tipo de Contrato'])
        rubro(blank: false, nullable: false, attributes: [title: 'rubro'])
        porcentaje(blank: true, nullable: true, attributes: [title: 'porcentaje'])
        editable(size: 1..1, blank: false, nullable: false, attributes: [title: 'editable'])
        decimo(size: 1..1, blank: false, nullable: false, attributes: [title: 'decimo'])
        iess(size: 1..1, blank: false, nullable: false, attributes: [title: 'iess'])
        gravable(size: 1..1, blank: false, nullable: false, attributes: [title: 'grabable'])
        valor(blank: true, nullable: true, attributes: [title: 'valor'])
        observaciones(blank: true, nullable: true, size: 1..127, attributes: [title: 'observaciones'])
        empresa(nullable: false,blank:false)
    }
}