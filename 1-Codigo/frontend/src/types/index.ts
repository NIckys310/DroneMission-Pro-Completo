// ============================================================================================
// DOCUMENTACIÓN · src/types/index.ts
//
// QUÉ ES:        El "diccionario" de datos del frontend.
// QUÉ HACE:      Interfaces y tipos que reflejan EXACTAMENTE los DTOs y enums del
//                backend.
// SI LO CAMBIAS: Si cambias un DTO o un enum en el backend, cámbialo aquí con el MISMO
//                nombre. npm run build te marca lo que falte.
// ============================================================================================
// Tipos que reflejan exactamente los DTOs del backend (paquetes */dto).

export type RoleName = 'ADMINISTRADOR' | 'OPERADOR' | 'PILOTO' | 'TECNICO' | 'CLIENTE';

export interface UserInfo {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Nombre completo. */
  fullName: string;
  /** Correo. */
  email: string;
  /** Rol. */
  role: RoleName;
  /** Permisos del rol (copiados dentro del documento). */
  permissions: string[];
  /** Identificador de piloto. */
  pilotId: number | null;
  /** Identificador de cliente. */
  clientId: number | null;
}

export interface LoginResponse {
  /** Token. */
  token: string;
  /** Tipo de token. */
  tokenType: string;
  /** Vence en minutos. */
  expiresInMinutes: number;
  /** Usuario. */
  user: UserInfo;
}

export interface ApiError {
  /** Timestamp. */
  timestamp: string;
  /** Estado. */
  status: number;
  /** Error. */
  error: string;
  /** Mensaje. */
  message: string;
  /** Ruta. */
  path: string;
  /** Errores de campo (opcional). */
  fieldErrors?: Record<string, string>;
}

export type DroneType = 'INSPECCION' | 'AGRICULTURA' | 'VIGILANCIA' | 'TOPOGRAFIA' | 'FOTOGRAFIA';
export type DroneStatus = 'DISPONIBLE' | 'EN_MISION' | 'EN_MANTENIMIENTO' | 'FUERA_DE_SERVICIO';

export interface Drone {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Código legible (por ejemplo, drn-001 o msn-0001). */
  code: string;
  /** Nombre. */
  name: string;
  /** Fabricante. */
  manufacturer: string;
  /** Modelo. */
  model: string;
  /** Número de serie (único). */
  serialNumber: string;
  /** Tipo. */
  type: DroneType;
  /** Estado. */
  status: DroneStatus;
  /** Capacidad de la batería (mah). */
  batteryCapacityMah: number;
  /** Nivel de batería actual (0 a 100 %). */
  batteryLevel: number;
  /** Horas de vuelo acumuladas. */
  flightHours: number;
  /** Velocidad máxima (km/h). */
  maxSpeedKmh: number;
  /** Autonomía de vuelo (minutos). */
  autonomyMinutes: number;
  /** Fecha de adquisición. */
  acquiredOn: string;
  /** Fecha del último mantenimiento. */
  lastMaintenanceOn: string | null;
  /** Fecha del próximo mantenimiento. */
  nextMaintenanceOn: string | null;
  /** Vencido de mantenimiento. */
  maintenanceDue: boolean;
  /** Descripción. */
  description: string | null;
  /** True si está activo (baja lógica). */
  active: boolean;
}

export interface DroneHistory {
  missions: { missionId: number; code: string; name: string; status: MissionStatus; scheduledDate: string; pilotName: string; assignedAt: string }[];
  maintenance: { id: number; type: string; status: string; date: string; description: string; cost: number; technicianName: string }[];
}

export type PilotStatus = 'DISPONIBLE' | 'EN_MISION' | 'NO_DISPONIBLE' | 'LICENCIA_VENCIDA';

export interface Certification {
  /** Identificador numérico (lo asigna sequencegeneratorservice) (opcional). */
  id?: number | null;
  /** Nombre. */
  name: string;
  /** Entidad emisora. */
  issuer: string;
  /** Emitido el. */
  issuedOn: string;
  /** Vence el. */
  expiresOn: string | null;
}

export interface Pilot {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Nombre. */
  firstName: string;
  /** Apellidos. */
  lastName: string;
  /** Nombre completo. */
  fullName: string;
  /** Documento. */
  document: string;
  /** Correo. */
  email: string;
  /** Teléfono. */
  phone: string;
  /** Número de licencia de piloto. */
  licenseNumber: string;
  /** Fecha de vencimiento de la licencia. */
  licenseExpiresOn: string;
  /** Licencia valid. */
  licenseValid: boolean;
  /** Horas de vuelo acumuladas. */
  flightHours: number;
  /** Estado. */
  status: PilotStatus;
  /** True si está activo (baja lógica). */
  active: boolean;
  /** Correo del usuario que hizo la acción. */
  userEmail: string | null;
  /** Certificaciones (embebidas en el piloto). */
  certifications: Certification[];
  /** Cantidad de misión. */
  missionCount: number;
}

export interface PilotMissionEntry {
  /** Identificador de misión. */
  missionId: number;
  /** Código legible (por ejemplo, drn-001 o msn-0001). */
  code: string;
  /** Nombre. */
  name: string;
  /** Estado. */
  status: MissionStatus;
  /** Fecha programada. */
  scheduledDate: string;
  /** Código de drone. */
  droneCode: string;
}

export interface Client {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Tipo. */
  type: 'PERSONA' | 'EMPRESA';
  /** Nombre. */
  name: string;
  /** Documento. */
  document: string;
  /** Correo. */
  email: string;
  /** Teléfono. */
  phone: string;
  /** Dirección. */
  address: string;
  /** Ciudad. */
  city: string;
  /** True si está activo (baja lógica). */
  active: boolean;
  /** Cantidad de misión. */
  missionCount: number;
  /** Correo del usuario que hizo la acción. */
  userEmail: string | null;
}

export type MissionType = 'INSPECCION' | 'AGRICULTURA' | 'VIGILANCIA' | 'TOPOGRAFIA' | 'FOTOGRAFIA' | 'MONITOREO';
export type MissionPriority = 'BAJA' | 'MEDIA' | 'ALTA' | 'CRITICA';
export type MissionStatus =
  | 'BORRADOR' | 'PENDIENTE' | 'PROGRAMADA' | 'EN_PREPARACION' | 'EN_VUELO' | 'PAUSADA' | 'FINALIZADA' | 'CANCELADA';
export type MissionAction =
  | 'EDIT' | 'DELETE' | 'SUBMIT' | 'ASSIGN' | 'CANCEL' | 'PREPARE' | 'START' | 'PAUSE' | 'RESUME' | 'FINISH'
  | 'OBSERVE' | 'INCIDENT' | 'EVIDENCE' | 'REPORT';

export interface Mission {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Código legible (por ejemplo, drn-001 o msn-0001). */
  code: string;
  /** Nombre. */
  name: string;
  /** Descripción. */
  description: string | null;
  /** Tipo. */
  type: MissionType;
  client: { id: number; name: string };
  /** Ubicación. */
  location: string;
  /** Latitud. */
  latitude: number;
  /** Longitud. */
  longitude: number;
  /** Altitud objetivo del vuelo (metros, 5 a 120). */
  targetAltitudeM: number;
  /** Fecha programada. */
  scheduledDate: string;
  /** Hora de inicio. */
  startTime: string;
  /** Hora estimada de fin. */
  estimatedEndTime: string;
  /** Prioridad. */
  priority: MissionPriority;
  /** Estado. */
  status: MissionStatus;
  /** Objetivo. */
  objective: string;
  /** Observaciones. */
  observations: string | null;
  drone: { id: number; code: string; name: string; type: DroneType; status: DroneStatus; batteryLevel: number } | null;
  pilot: { id: number; fullName: string; status: PilotStatus; licenseExpiresOn: string } | null;
  /** Creación por. */
  createdBy: string;
  /** Fecha y hora real de despegue. */
  actualStart: string | null;
  /** Fecha y hora real de finalización. */
  actualEnd: string | null;
  /** Fecha y hora de creación. */
  createdAt: string;
  /** Acciones disponibles. */
  availableActions: MissionAction[];
}

export interface Waypoint {
  /** Orden. */
  sequence: number;
  /** Latitud. */
  latitude: number;
  /** Longitud. */
  longitude: number;
  /** Altitud (metros). */
  altitudeM: number;
  /** Acción. */
  action: string;
}

export interface FlightPlan {
  /** Estrategia. */
  strategy: string;
  /** Porcentaje de batería mínima. */
  minimumBatteryPercent: number;
  /** Tipos de drone compatibles. */
  compatibleDroneTypes: DroneType[];
  /** Velocidad de crucero (km/h). */
  cruiseSpeedKmh: number;
  /** Distancia estimada (m). */
  estimatedDistanceM: number;
  /** Duración estimada mínima. */
  estimatedDurationMin: number;
  /** Porcentaje de use de batería estimada. */
  estimatedBatteryUsePercent: number;
  /** Puntos de ruta. */
  waypoints: Waypoint[];
}

export interface AssignmentOptions {
  /** Porcentaje de batería mínima. */
  minimumBatteryPercent: number;
  drones: { id: number; code: string; name: string; type: DroneType; status: DroneStatus; batteryLevel: number; eligible: boolean; reason: string | null }[];
  pilots: { id: number; fullName: string; status: PilotStatus; licenseExpiresOn: string; eligible: boolean; reason: string | null }[];
}

export interface TelemetryPoint {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Momento de la lectura. */
  recordedAt: string;
  /** Latitud. */
  latitude: number;
  /** Longitud. */
  longitude: number;
  /** Altitud (metros). */
  altitudeM: number;
  /** Velocidad (km/h). */
  speedKmh: number;
  /** Batería. */
  battery: number;
  /** Distancia recorrida (metros). */
  distanceM: number;
  /** Temperatura (°c). */
  temperatureC: number;
  /** Tiempo de vuelo (segundos). */
  flightTimeS: number;
  /** Punto de ruta actual. */
  currentWaypoint: number;
  /** Simulada. */
  simulated: boolean;
}

export interface TelemetrySummary {
  /** Lecturas. */
  readings: number;
  /** Altitud máxima (m). */
  maxAltitudeM: number;
  /** Velocidad de promedio (km/h). */
  avgSpeedKmh: number;
  /** Velocidad máxima (km/h). */
  maxSpeedKmh: number;
  /** Distancia recorrida (metros). */
  distanceM: number;
  /** Tiempo de vuelo (segundos). */
  flightTimeS: number;
  /** Batería de inicio. */
  startBattery: number;
  /** Batería de fin. */
  endBattery: number;
  /** Usada de batería. */
  batteryUsed: number;
  /** Temperatura máxima (°c). */
  maxTemperatureC: number;
}

export interface TelemetryFeed {
  /** Simulada. */
  simulated: boolean;
  /** Aviso. */
  notice: string;
  /** Estado de misión. */
  missionStatus: MissionStatus;
  /** En vivo. */
  live: boolean;
  /** Completado de route. */
  routeCompleted: boolean;
  /** Puntos de ruta total. */
  totalWaypoints: number;
  /** Resumen. */
  summary: TelemetrySummary;
  /** Puntos. */
  points: TelemetryPoint[];
}

export type IncidentType =
  | 'BATERIA_BAJA' | 'PERDIDA_SENAL' | 'OBSTACULO' | 'ERROR_TECNICO' | 'CLIMA' | 'ATERRIZAJE_EMERGENCIA' | 'OTRO';
export type Severity = 'BAJA' | 'MEDIA' | 'ALTA' | 'CRITICA';
export type IncidentStatus = 'ABIERTO' | 'EN_REVISION' | 'RESUELTO';

export interface Incident {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Identificador de misión. */
  missionId: number;
  /** Código de misión. */
  missionCode: string;
  /** Nombre de misión. */
  missionName: string;
  /** Fecha. */
  date: string;
  /** Tipo. */
  type: IncidentType;
  /** Gravedad. */
  severity: Severity;
  /** Descripción. */
  description: string;
  /** Reportado por. */
  reportedBy: string;
  /** Estado. */
  status: IncidentStatus;
  /** Resolución. */
  resolution: string | null;
}

export type EvidenceType = 'FOTOGRAFIA' | 'VIDEO' | 'DOCUMENTO' | 'ARCHIVO' | 'OBSERVACION';

export interface Evidence {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Identificador de misión. */
  missionId: number;
  /** Tipo. */
  type: EvidenceType;
  /** Nombre. */
  name: string;
  /** Url. */
  url: string;
  /** Archivo stored. */
  storedFile: boolean;
  /** Descripción. */
  description: string | null;
  /** True si el cliente puede ver esta evidencia. */
  visibleToClient: boolean;
  /** Subido por. */
  uploadedBy: string;
  /** Fecha. */
  date: string;
}

export type MaintenanceType = 'PREVENTIVO' | 'CORRECTIVO' | 'CALIBRACION' | 'REPARACION' | 'INSPECCION';
export type MaintenanceStatus = 'PROGRAMADO' | 'EN_PROCESO' | 'COMPLETADO' | 'CANCELADO';

export interface Maintenance {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Identificador de drone. */
  droneId: number;
  /** Código de drone. */
  droneCode: string;
  /** Nombre de drone. */
  droneName: string;
  /** Estado de drone. */
  droneStatus: DroneStatus;
  /** Identificador de técnico. */
  technicianId: number;
  /** Nombre de técnico. */
  technicianName: string;
  /** Tipo. */
  type: MaintenanceType;
  /** Fecha. */
  date: string;
  /** Descripción. */
  description: string;
  /** Costo. */
  cost: number;
  /** Estado. */
  status: MaintenanceStatus;
  /** Fecha del próximo mantenimiento. */
  nextDate: string | null;
  /** Observaciones. */
  observations: string | null;
  /** Cuándo se inició. */
  startedAt: string | null;
  /** Cuándo se completó. */
  completedAt: string | null;
}

export interface ReportEntry {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Tipo. */
  type: 'MISION' | 'DRONES' | 'PILOTOS' | 'MANTENIMIENTO';
  /** Título. */
  title: string;
  /** Identificador de misión. */
  missionId: number | null;
  /** Código de misión. */
  missionCode: string | null;
  /** Generado por. */
  generatedBy: string;
  /** Fecha y hora de generación. */
  generatedAt: string;
  /** Resumen. */
  summary: string;
}

export interface MissionReport {
  /** Identificador de misión. */
  missionId: number;
  /** Código legible (por ejemplo, drn-001 o msn-0001). */
  code: string;
  /** Nombre. */
  name: string;
  /** Tipo. */
  type: MissionType;
  /** Estado. */
  status: MissionStatus;
  /** Minutos de duración. */
  durationMinutes: number | null;
  client: { name: string; type: string; document: string; email: string; phone: string; city: string };
  drone: { code: string; name: string; manufacturer: string; model: string; type: string } | null;
  pilot: { fullName: string; licenseNumber: string; licenseExpiresOn: string } | null;
  /** Telemetría. */
  telemetry: TelemetrySummary;
  /** Incidentes. */
  incidents: Incident[];
  /** Evidencias. */
  evidences: Evidence[];
}

export interface DroneReport {
  total: number; available: number; inMission: number; inMaintenance: number; outOfService: number; totalFlightHours: number;
  drones: { code: string; name: string; model: string; type: DroneType; status: DroneStatus; batteryLevel: number; flightHours: number; nextMaintenanceOn: string | null }[];
}

export interface PilotReport {
  total: number; available: number; expiredLicenses: number; totalFlightHours: number;
  pilots: { fullName: string; licenseNumber: string; licenseExpiresOn: string; licenseValid: boolean; status: PilotStatus; flightHours: number; missions: number; finishedMissions: number }[];
}

export interface MaintenanceReport {
  total: number; scheduled: number; inProgress: number; completed: number; cancelled: number; totalCost: number;
  costByDrone: { droneCode: string; droneName: string; count: number; cost: number }[];
  history: { date: string; droneCode: string; type: MaintenanceType; status: MaintenanceStatus; technician: string; cost: number; description: string }[];
}

export interface DashboardData {
  drones: { total: number; available: number; inMission: number; inMaintenance: number; outOfService: number; totalFlightHours: number };
  pilots: { total: number; available: number; inMission: number; expiredLicenses: number };
  /** Misiones por estado. */
  missionsByStatus: Record<MissionStatus, number>;
  missionsByType: { label: MissionType; count: number }[];
  flightHoursByDrone: { drone: string; hours: number }[];
  missionsPerDay: { date: string; scheduled: number; finished: number }[];
  /** Misiones activo. */
  activeMissions: DashboardMission[];
  /** Misiones próximas. */
  upcomingMissions: DashboardMission[];
  /** Incidentes recientes. */
  recentIncidents: Incident[];
  /** Mantenimiento pendiente. */
  pendingMaintenance: Maintenance[];
  alerts: { level: 'info' | 'warning' | 'error'; message: string; link: string }[];
  /** Incidentes abierto. */
  openIncidents: number;
}

export interface DashboardMission {
  id: number; code: string; name: string; status: MissionStatus; type: MissionType; date: string; startTime: string;
  clientName: string; droneCode: string | null; pilotName: string | null;
}

export interface User {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Nombre. */
  firstName: string;
  /** Apellidos. */
  lastName: string;
  /** Nombre completo. */
  fullName: string;
  /** Nombre de usuario. */
  username: string;
  /** Correo. */
  email: string;
  /** Rol. */
  role: RoleName;
  /** True si está activo (baja lógica). */
  active: boolean;
  /** True si la cuenta espera aprobación del administrador. */
  pendingApproval: boolean;
  /** Hasta cuándo está bloqueada la cuenta (null = no bloqueada). */
  lockedUntil: string | null;
  /** Identificador de piloto. */
  pilotId: number | null;
  /** Identificador de cliente. */
  clientId: number | null;
  /** Último inicio de sesión. */
  lastLoginAt: string | null;
  /** Fecha y hora de creación. */
  createdAt: string;
}

export interface Role {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Nombre. */
  name: RoleName;
  /** Descripción. */
  description: string;
  /** Permisos del rol (copiados dentro del documento). */
  permissions: string[];
}

export interface Permission {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Código legible (por ejemplo, drn-001 o msn-0001). */
  code: string;
  /** Descripción. */
  description: string;
}

export interface AuditEntry {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Correo del usuario que hizo la acción. */
  userEmail: string;
  /** Acción. */
  action: string;
  /** Entidad. */
  entity: string;
  /** Id de la entidad afectada. */
  entityId: number | null;
  /** Descripción. */
  description: string;
  /** Fecha. */
  date: string;
}

export interface Notification {
  /** Identificador numérico (lo asigna sequencegeneratorservice). */
  id: number;
  /** Título. */
  title: string;
  /** Mensaje. */
  message: string;
  /** Identificador de misión. */
  missionId: number | null;
  /** Leída. */
  read: boolean;
  /** Fecha. */
  date: string;
}

export interface AuthConfig {
  /** Activado de usuarios de demo. */
  demoUsersEnabled: boolean;
  /** Activado de registro. */
  registrationEnabled: boolean;
  /** Token de recuperación de mostrar. */
  showResetToken: boolean;
  /** Política de contraseña. */
  passwordPolicy: string;
}
