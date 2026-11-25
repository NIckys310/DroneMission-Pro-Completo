// ============================================================================================
// DOCUMENTACIÓN · src/utils/labels.ts
//
// QUÉ ES:        LOS TEXTOS Y COLORES de cada estado.
// QUÉ HACE:      Traduce cada valor del backend (EN_VUELO, DISPONIBLE...) a texto en
//                español y al color de su pastilla.
// SI LO CAMBIAS: Cambia el color de un estado aquí ("success" verde, "error" rojo,
//                "warning" naranja, "info" azul). Valor nuevo en el backend: agrégalo
//                aquí.
// ============================================================================================
// Textos en español y colores para cada valor de los enums del backend.

export type ChipColor = 'default' | 'primary' | 'secondary' | 'success' | 'warning' | 'error' | 'info';

interface Meta { label: string; color: ChipColor }

/** Función meta: Meta. */
const meta = (label: string, color: ChipColor = 'default'): Meta => ({ label, color });

/** Objeto missionStatus: estado de misión. */
export const missionStatus: Record<string, Meta> = {
  /** Borrador. */
  BORRADOR: meta('Borrador'),
  /** Pendiente. */
  PENDIENTE: meta('Pendiente', 'secondary'),
  /** Programada. */
  PROGRAMADA: meta('Programada', 'info'),
  /** Preparacion de en. */
  EN_PREPARACION: meta('En preparación', 'warning'),
  /** Vuelo de en. */
  EN_VUELO: meta('En vuelo', 'primary'),
  /** Pausada. */
  PAUSADA: meta('Pausada', 'warning'),
  /** Finalizada. */
  FINALIZADA: meta('Finalizada', 'success'),
  /** Cancelada. */
  CANCELADA: meta('Cancelada', 'error'),
};

/** Objeto droneStatus: estado de drone. */
export const droneStatus: Record<string, Meta> = {
  DISPONIBLE: meta('Disponible', 'success'),
  EN_MISION: meta('En misión', 'primary'),
  EN_MANTENIMIENTO: meta('En mantenimiento', 'warning'),
  FUERA_DE_SERVICIO: meta('Fuera de servicio', 'error'),
};

/** Objeto pilotStatus: estado de piloto. */
export const pilotStatus: Record<string, Meta> = {
  DISPONIBLE: meta('Disponible', 'success'),
  EN_MISION: meta('En misión', 'primary'),
  NO_DISPONIBLE: meta('No disponible'),
  LICENCIA_VENCIDA: meta('Licencia vencida', 'error'),
};

/** Objeto maintenanceStatus: estado de mantenimiento. */
export const maintenanceStatus: Record<string, Meta> = {
  PROGRAMADO: meta('Programado', 'info'),
  EN_PROCESO: meta('En proceso', 'warning'),
  COMPLETADO: meta('Completado', 'success'),
  CANCELADO: meta('Cancelado', 'error'),
};

/** Objeto incidentStatus: estado de incidente. */
export const incidentStatus: Record<string, Meta> = {
  ABIERTO: meta('Abierto', 'error'),
  EN_REVISION: meta('En revisión', 'warning'),
  RESUELTO: meta('Resuelto', 'success'),
};

/** Objeto severity: gravedad. */
export const severity: Record<string, Meta> = {
  BAJA: meta('Baja', 'default'),
  MEDIA: meta('Media', 'info'),
  ALTA: meta('Alta', 'warning'),
  CRITICA: meta('Crítica', 'error'),
};

/** priority: prioridad. */
export const priority = severity;

/** Objeto droneType: tipo de drone. */
export const droneType: Record<string, string> = {
  INSPECCION: 'Inspección', AGRICULTURA: 'Agricultura', VIGILANCIA: 'Vigilancia', TOPOGRAFIA: 'Topografía', FOTOGRAFIA: 'Fotografía',
};

/** Objeto missionType: tipo de misión. */
export const missionType: Record<string, string> = { ...droneType, MONITOREO: 'Monitoreo' };

/** Objeto incidentType: tipo de incidente. */
export const incidentType: Record<string, string> = {
  BATERIA_BAJA: 'Batería baja', PERDIDA_SENAL: 'Pérdida de señal', OBSTACULO: 'Obstáculo detectado',
  ERROR_TECNICO: 'Error técnico', CLIMA: 'Condiciones climáticas', ATERRIZAJE_EMERGENCIA: 'Aterrizaje de emergencia', OTRO: 'Otro',
};

/** Objeto evidenceType: tipo de evidencia. */
export const evidenceType: Record<string, string> = {
  FOTOGRAFIA: 'Fotografía', VIDEO: 'Video', DOCUMENTO: 'Documento', ARCHIVO: 'Archivo', OBSERVACION: 'Observación',
};

/** Objeto maintenanceType: tipo de mantenimiento. */
export const maintenanceType: Record<string, string> = {
  PREVENTIVO: 'Preventivo', CORRECTIVO: 'Correctivo', CALIBRACION: 'Calibración', REPARACION: 'Reparación', INSPECCION: 'Inspección',
};

/** Objeto roleName: nombre de rol. */
export const roleName: Record<string, string> = {
  ADMINISTRADOR: 'Administrador', OPERADOR: 'Operador', PILOTO: 'Piloto', TECNICO: 'Técnico', CLIENTE: 'Cliente',
};

/** Objeto clientType: tipo de cliente. */
export const clientType: Record<string, string> = { PERSONA: 'Persona natural', EMPRESA: 'Empresa' };

/** Objeto waypointAction: acción de punto de ruta. */
export const waypointAction: Record<string, string> = {
  DESPEGUE: 'Despegue', PASO: 'Paso', FOTO: 'Foto', VIDEO: 'Video', ESCANEO: 'Escaneo', FUMIGACION: 'Fumigación', ATERRIZAJE: 'Aterrizaje',
};

/** Convierte un diccionario de etiquetas en opciones para un <select>. */
export function options(dict: Record<string, string | Meta>): { value: string; label: string }[] {
  return Object.entries(dict).map(([value, v]) => ({ value, label: typeof v === 'string' ? v : v.label }));
}
