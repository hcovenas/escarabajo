package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.mvc.Controller;
import controllers.Application;

public class ReporteRecorrido extends Reporte{
	
	private Long idRecorrido = null;
	private List<String> subReports;

	public ReporteRecorrido(){
		subReports = new ArrayList<String>();
		subReports.add("./app/reports/SureporteRecorrido_Metricas");
		subReports.add("./app/reports/SubreporteRecorrido_Participantes");
	}
	
	public ReporteRecorrido(Long idRecorrido){
		this.idRecorrido = idRecorrido;
	}

	@Override
	public String generarConsulta() {
		String consulta = "select re.tipo, " +
					      "re.nombre as nombre_recorrido, " +
					      "re.descripcion as descripcion_recorrido, " +
					      "re.hora_frecuente, " +
					      "re.dia_frecuente, " +
					      "ru.fecha_inicio_ruta, " +
					      "ru.fecha_fin_ruta, " +
					      "ru.lugar_inicio, " +
					      "ru.lugar_fin, " +
					      "ru.latitud_inicio, " +
					      "ru.longitud_incio, " +
					      "ru.latitud_fin, " +
					      "ru.longitud_fin " +
					"from recorrido re, " +
					     "ruta ru " +
					"where re.id_recorrido = ru.recorrido_id_recorrido " +
					      "and re.id_recorrido = $P{p_recorrido}"; 

		return consulta;
	}

	@Override
	public void asignarParametros() {
		
		this.setFileName("./app/reports/ReporteRecorrido");
		
		List<String> subReports = new ArrayList<String>();
		subReports.add("./app/reports/SubreporteRecorrido_Participantes");
		subReports.add("./app/reports/SureporteRecorrido_Metricas");
		this.setSubReports(subReports);
		
		User usuario = Application.getLocalUser(Controller.session());
		
		this.parameters.put("p_usuario", usuario.id);
		this.parameters.put("p_recorrido", idRecorrido);
		this.parameters.put("p_fecha", new Date());
		this.parameters.put("p_consulta", generarConsulta());
	}
	
	public Long getIdRecorrido() {
		return idRecorrido;
	}

	public void setIdRecorrido(Long idRecorrido) {
		this.idRecorrido = idRecorrido;
	}

}
