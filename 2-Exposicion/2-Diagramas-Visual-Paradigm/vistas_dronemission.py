# -*- coding: utf-8 -*-
"""
EJEMPLO COMPLETO · DroneMission Pro (Spring Boot + MongoDB + React).
Genera las 5 vistas 4+1 en un proyecto de Visual Paradigm siguiendo las convenciones del profe.

    python dronemission.py <carpeta_salida> [filtro_de_diagramas...]
"""
import os, sys
sys.path.insert(0, os.path.expanduser(r'~/.claude/skills/vistas-4mas1-visual-paradigm/scripts'))
from vistas import *

OUT = os.path.abspath(sys.argv[1] if len(sys.argv) > 1 else 'salida')
SOLO = sys.argv[2:]
v = Vistas('DroneMission-Pro-Vistas')

# ================================================================== 1. CONTEXTO (estilo DFD del profe)
d1 = v.diagrama('ClassDiagram', '1 - Vista de contexto', 1560, 900)
sis = v.sistema(d1, 'DroneMission Pro', 660, 330, 240)
E = {
    'ad': v.entidad(d1, 'Administrador', 60, 280, 210, 50),
    'op': v.entidad(d1, 'Operador', 60, 430, 210, 50),
    'te': v.entidad(d1, 'Técnico', 60, 580, 210, 50),
    'pi': v.entidad(d1, 'Piloto', 1290, 280, 210, 50),
    'cl': v.entidad(d1, 'Cliente', 1290, 430, 210, 50),
    'vi': v.entidad(d1, 'Visitante (sin cuenta)', 1290, 580, 210, 50),
    'rl': v.entidad(d1, 'Reloj del sistema (tareas programadas)', 640, 40, 280, 50, LILA),
    'db': v.entidad(d1, 'MongoDB (Atlas o local)', 670, 800, 220, 50, GRIS),
}
v.flujo(d1, E['ad'], sis, 'usuarios, roles, permisos, flota', 'auditoría y reportes')
v.flujo(d1, E['op'], sis, 'misiones y asignaciones', 'dashboard, alertas, PDF')
v.flujo(d1, E['te'], sis, 'mantenimientos, estado técnico', 'drones a revisar')
v.flujo(d1, E['pi'], sis, 'chequeo, despegue, incidentes, evidencias', 'misiones asignadas, plan de vuelo')
v.flujo(d1, E['cl'], sis, 'consulta de sus misiones', 'estado, evidencias y PDF')
v.flujo(d1, E['vi'], sis, 'datos de registro', 'cuenta pendiente de aprobación')
v.flujo(d1, E['rl'], sis, 'cada 2 s · 1:00 a. m.', '', sep=0)
v.flujo(d1, E['db'], sis, 'documentos', 'guarda y consulta', sep=60)
v.nota(d1, 'El drone virtual (TelemetrySimulator) es INTERNO al sistema, por eso no es una entidad externa.\n'
           'El reloj dispara las tareas programadas: mover el drone cada 2 s y revisar licencias a la 1:00 a. m.',
       1080, 800, 440, 80)

# ================================================================== 2. FUNCIONAL (componentes)
d2 = v.diagrama('ComponentDiagram', '2 - Vista funcional (componentes)', 1700, 1260)
usr = v.actor(d2, 'Usuario (5 roles)', 690, 20)
fe1 = v.componente(d2, 'Páginas React (pages, components)', 580, 130, color=VERDE)
fe2 = v.componente(d2, 'Cliente API (Axios + token JWT)', 580, 240, color=VERDE)
iApi = v.interfaz(d2, 'API REST /api (JSON)', 700, 345)
ctrl = v.componente(d2, 'Controladores REST', 580, 430)
sec = v.componente(d2, 'Seguridad (filtro JWT, BCrypt, límite de intentos)', 160, 430, 300)
rep = v.componente(d2, 'Reportes PDF y dashboard', 960, 430)
mis = v.componente(d2, 'Gestión de misiones (ciclo de vida, asignación)', 580, 560, 260, 72)
flo = v.componente(d2, 'Flota y personal (drones, pilotos, clientes, usuarios)', 160, 560, 300, 72)
inc = v.componente(d2, 'Incidentes y evidencias', 960, 560)
man = v.componente(d2, 'Mantenimiento', 1300, 560, 220)
iStr = v.interfaz(d2, 'MissionStrategy', 700, 710)
est = v.componente(d2, 'Estrategias de vuelo (4 tipos)', 580, 870)
tel = v.componente(d2, 'Simulador de telemetría (drone virtual, @Scheduled)', 160, 770, 300)
s3d = v.componente(d2, 'Simulador 3D (planes de práctica)', 960, 770, 240)
iObs = v.interfaz(d2, 'MissionEventListener', 1400, 720)
aud = v.componente(d2, 'Auditoría', 1270, 820, 180, 56)
noti = v.componente(d2, 'Notificaciones', 1480, 820, 180, 56)
iRepo = v.interfaz(d2, 'Repositorios (Spring Data)', 300, 990)
per = v.componente(d2, 'Persistencia (MongoDB + transacciones)', 180, 1060, 280)
fil = v.componente(d2, 'Almacenamiento de archivos (uploads/)', 960, 1060, 260)
db2 = v.componente(d2, 'MongoDB', 210, 1170, 220, 60, GRIS, 'external system')
v.usa(d2, usr, fe1, 'usa'); v.usa(d2, fe1, fe2)
v.requiere(d2, fe2, iApi, 'HTTPS + Bearer JWT'); v.provee(d2, ctrl, iApi)
v.usa(d2, ctrl, sec, 'autentica y autoriza'); v.usa(d2, ctrl, mis); v.usa(d2, ctrl, flo); v.usa(d2, ctrl, rep); v.usa(d2, ctrl, inc)
v.usa(d2, ctrl, man, puntos=[(820, 430), (820, 400), (1410, 400), (1410, 560)])
v.usa(d2, mis, flo, 'valida disponibilidad')
v.requiere(d2, mis, iStr, 'pide el plan de vuelo'); v.provee(d2, est, iStr)
v.requiere(d2, tel, iStr, 'velocidad y consumo'); v.requiere(d2, s3d, iStr)
v.requiere(d2, mis, iObs, 'publica eventos', puntos=[(800, 632), (800, 665), (1410, 665), (1410, 720)])
v.provee(d2, aud, iObs); v.provee(d2, noti, iObs)
v.usa(d2, inc, fil, 'guarda archivos', puntos=[(1200, 624), (1200, 645), (1240, 645), (1240, 1092), (1220, 1092)])
v.requiere(d2, tel, iRepo, puntos=[(310, 834), (310, 900), (310, 990)])
v.requiere(d2, flo, iRepo, puntos=[(170, 632), (120, 632), (120, 1000), (300, 1000)])
v.requiere(d2, mis, iRepo, 'lee y guarda', puntos=[(600, 632), (540, 632), (540, 1000), (320, 1000)])
v.provee(d2, per, iRepo)
v.usa(d2, per, db2, 'mongodb:// :27017', kind='Dependency')
v.nota(d2, 'Interfaces reales del código: MissionStrategy (patrón Strategy) y MissionEventListener (patrón Observer).\n'
           'Círculo = interfaz ofrecida (línea continua hacia quien la implementa).\n'
           'Línea punteada hacia el círculo = interfaz requerida (quien la usa).\n'
           'Incidentes, reportes, mantenimiento, auditoría y notificaciones también usan los repositorios (se omiten esas flechas para no saturar).',
       1270, 940, 400, 170)

# ================================================================== 3a. LÓGICA (dominio)
d3 = v.diagrama('ClassDiagram', '3a - Vista lógica (dominio)', 1700, 1180)
LD, LDT, BD = 'LocalDate', 'LocalDateTime', 'BigDecimal'
C = {}
def caja(d, m, x, y, w, h, color=None): C[m.name] = v.figura(d, m, x, y, w, h, color); return C[m.name]
user = v.clase('User', [('id', 'Long'), ('firstName', 'String'), ('lastName', 'String'), ('username', 'String'), ('email', 'String'), ('passwordHash', 'String'),
                        ('active', 'boolean'), ('pendingApproval', 'boolean'), ('failedAttempts', 'int'), ('lockedUntil', LDT)],
               [('isLocked', 'boolean', [('now', LDT)]), ('hasRole', 'boolean', [('role', 'RoleName')])])
role = v.clase('Role', [('id', 'Long'), ('name', 'RoleName'), ('description', 'String')], [('hasPermission', 'boolean', [('code', 'String')])])
perm = v.clase('Permission', [('id', 'Long'), ('code', 'String'), ('description', 'String')])
client = v.clase('Client', [('id', 'Long'), ('type', 'ClientType'), ('name', 'String'), ('document', 'String'), ('email', 'String'), ('city', 'String'), ('active', 'boolean')])
pilot = v.clase('Pilot', [('id', 'Long'), ('firstName', 'String'), ('lastName', 'String'), ('licenseNumber', 'String'), ('licenseExpiresOn', LD), ('flightHours', BD), ('status', 'PilotStatus')],
                [('hasValidLicenseOn', 'boolean', [('date', LD)]), ('addFlightHours', 'void', [('h', BD)])])
cert = v.clase('PilotCertification', [('name', 'String'), ('issuer', 'String'), ('issuedOn', LD), ('expiresOn', LD)])
drone = v.clase('Drone', [('id', 'Long'), ('code', 'String'), ('name', 'String'), ('model', 'String'), ('serialNumber', 'String'), ('type', 'DroneType'), ('status', 'DroneStatus'),
                          ('batteryCapacityMah', 'Integer'), ('batteryLevel', 'Integer'), ('flightHours', BD), ('maxSpeedKmh', 'Integer'), ('autonomyMinutes', 'Integer'),
                          ('lastMaintenanceOn', LD), ('nextMaintenanceOn', LD), ('active', 'boolean')],
                [('isAssignable', 'boolean'), ('hasBatteryAtLeast', 'boolean', [('p', 'int')])])
mission = v.clase('Mission', [('id', 'Long'), ('code', 'String'), ('name', 'String'), ('description', 'String'), ('type', 'MissionType'), ('location', 'String'),
                              ('latitude', 'Double'), ('longitude', 'Double'), ('targetAltitudeM', 'Integer'), ('scheduledDate', LD), ('startTime', 'LocalTime'),
                              ('estimatedEndTime', 'LocalTime'), ('priority', 'MissionPriority'), ('status', 'MissionStatus'), ('objective', 'String'),
                              ('observations', 'String'), ('actualStart', LDT), ('actualEnd', LDT)],
                  [('changeStatus', 'void', [('target', 'MissionStatus')]), ('getActiveAssignment', 'Optional'), ('replaceFlightPlan', 'void', [('points', 'List')])])
wp = v.clase('Waypoint', [('sequence', 'Integer'), ('latitude', 'Double'), ('longitude', 'Double'), ('altitudeM', 'Integer'), ('action', 'WaypointAction')])
asg = v.clase('MissionAssignment', [('id', 'Long'), ('assignedAt', LDT), ('active', 'boolean')])
telm = v.clase('Telemetry', [('id', 'Long'), ('recordedAt', LDT), ('latitude', 'Double'), ('longitude', 'Double'), ('altitudeM', 'Double'), ('speedKmh', 'Double'),
                             ('battery', 'Integer'), ('distanceM', 'Double'), ('simulated', 'boolean')])
incm = v.clase('Incident', [('id', 'Long'), ('date', LDT), ('type', 'IncidentType'), ('severity', 'IncidentSeverity'), ('status', 'IncidentStatus'), ('description', 'String'), ('resolution', 'String')])
ev = v.clase('Evidence', [('id', 'Long'), ('type', 'EvidenceType'), ('name', 'String'), ('url', 'String'), ('visibleToClient', 'boolean'), ('date', LDT)], [('isStoredFile', 'boolean')])
mant = v.clase('Maintenance', [('id', 'Long'), ('type', 'MaintenanceType'), ('date', LD), ('cost', BD), ('status', 'MaintenanceStatus'), ('nextDate', LD),
                               ('observations', 'String'), ('startedAt', LDT), ('completedAt', LDT)])
repm = v.clase('Report', [('id', 'Long'), ('type', 'ReportType'), ('title', 'String'), ('generatedAt', LDT), ('summary', 'String')])
notm = v.clase('Notification', [('id', 'Long'), ('title', 'String'), ('message', 'String'), ('read', 'boolean'), ('date', LDT)])
audm = v.clase('AuditLog', [('id', 'Long'), ('userEmail', 'String'), ('action', 'String'), ('entity', 'String'), ('entityId', 'Long'), ('description', 'String'), ('date', LDT)])
caja(d3, perm, 40, 40, 200, 80); caja(d3, role, 330, 40, 230, 90); caja(d3, user, 330, 200, 260, 230)
caja(d3, notm, 40, 230, 200, 110); caja(d3, audm, 40, 400, 200, 150)
caja(d3, client, 340, 560, 210, 150); caja(d3, repm, 340, 840, 220, 110)
caja(d3, mission, 650, 560, 300, 380, NARANJA); caja(d3, wp, 650, 1040, 230, 110)
caja(d3, pilot, 1060, 40, 290, 180); caja(d3, cert, 1430, 40, 220, 100)
caja(d3, asg, 1060, 330, 210, 80); caja(d3, drone, 1360, 300, 300, 300); caja(d3, mant, 1360, 720, 260, 200)
caja(d3, incm, 1060, 560, 230, 150); caja(d3, telm, 1060, 800, 230, 190); caja(d3, ev, 1060, 1030, 230, 140)
A = lambda *a, **k: v.asociacion(d3, *a, **k)
A(C['User'], C['Role'], '0..*', '1', 'tiene')
A(C['Role'], C['Permission'], '0..*', '0..*', 'agrupa', 'Shared')
A(C['Notification'], C['User'], '0..*', '1', 'para')
A(C['AuditLog'], C['User'], '0..*', '0..1', 'hecho por')
A(C['User'], C['Client'], '0..1', '0..1', 'accede como')
A(C['User'], C['Pilot'], '0..1', '0..1', 'accede como')
A(C['Pilot'], C['PilotCertification'], '1', '0..*', 'embebe', 'Composited')
A(C['Client'], C['Mission'], '1', '0..*', 'contrata')
A(C['User'], C['Mission'], '1', '0..*', 'crea')
A(C['Mission'], C['Waypoint'], '1', '2..*', 'plan de vuelo (embebido)', 'Composited')
A(C['Mission'], C['MissionAssignment'], '1', '0..*', 'historial')
A(C['MissionAssignment'], C['Pilot'], '0..*', '1'); A(C['MissionAssignment'], C['Drone'], '0..*', '1')
A(C['Mission'], C['Incident'], '1', '0..*', 'sufre'); A(C['Mission'], C['Telemetry'], '1', '0..*', 'registra')
A(C['Mission'], C['Evidence'], '1', '0..*', 'contiene'); A(C['Drone'], C['Maintenance'], '1', '0..*', 'recibe')
A(C['Report'], C['Mission'], '0..*', '0..1', 'resume')
A(C['Report'], C['User'], '0..*', '1', 'generado por', puntos=[(340, 880), (300, 880), (300, 410), (330, 410)])
A(C['Notification'], C['Mission'], '0..*', '0..1', 'sobre', puntos=[(240, 320), (270, 320), (270, 760), (650, 760)])
v.nota(d3, 'Rombo negro = va EMBEBIDO dentro del documento padre en MongoDB (Waypoint, PilotCertification).\n'
           'Línea simple = colección aparte unida por referencia (@DBRef): borrar la misión no borra su telemetría ni su historial.\n'
           'Rombo blanco = los permisos se copian dentro del rol (agregación).', 40, 1010, 520, 120)

# ================================================================== 3b. LÓGICA (patrones)
d3b = v.diagrama('ClassDiagram', '3b - Vista lógica (patrones de diseño)', 1700, 1020)
P = {}
def pc(m, x, y, w, h, color=None): P[m.name] = v.figura(d3b, m, x, y, w, h, color); return P[m.name]
st = v.clase('MissionStatus', operaciones=[('canTransitionTo', 'boolean', [('target', 'MissionStatus')])], est='enumeration',
             literales=('BORRADOR', 'PENDIENTE', 'PROGRAMADA', 'EN_PREPARACION', 'EN_VUELO', 'PAUSADA', 'FINALIZADA', 'CANCELADA'))
builder = v.clase('MissionBuilder', [('MAX_ALTITUDE_M', 'int'), ('MIN_ALTITUDE_M', 'int')],
                  [('name', 'MissionBuilder', [('n', 'String')]), ('client', 'MissionBuilder', [('c', 'Client')]), ('altitude', 'MissionBuilder', [('m', 'Integer')]),
                   ('schedule', 'MissionBuilder', [('date', LD), ('start', 'LocalTime'), ('end', 'LocalTime')]), ('build', 'Mission')])
factory = v.clase('DroneFactory', [('SPECS', 'Map<DroneType, DroneSpec>')], [('createDrone', 'Drone', [('request', 'DroneRequest'), ('nextSequence', 'long')])])
v.agregar_miembros(iStr.model, operaciones=[('name', 'String'), ('supportedTypes', 'Set<MissionType>'), ('minimumBatteryPercent', 'int'), ('compatibleDroneTypes', 'Set<DroneType>'),
                                            ('cruiseSpeedKmh', 'double'), ('batteryDrainPerMinute', 'double'), ('generateFlightPlan', 'List<PlannedWaypoint>', [('center', 'GeoPoint'), ('altitudeM', 'int')])])
absst = v.clase('AbstractMissionStrategy', operaciones=[('generateFlightPlan', 'List<PlannedWaypoint>'), ('generatePattern', 'List<PlannedWaypoint>')], est='abstract')
strats = [v.clase(n) for n in ('InspectionStrategy', 'AgricultureStrategy', 'SurveillanceStrategy', 'MappingStrategy')]
resolver = v.clase('MissionStrategyResolver', operaciones=[('forType', 'MissionStrategy', [('type', 'MissionType')])])
fps = v.clase('FlightPlanService', operaciones=[('generate', 'void', [('mission', 'Mission')])])
pub = v.clase('MissionEventPublisher', [('listeners', 'List<MissionEventListener>')], [('publish', 'void', [('event', 'MissionEvent')])])
v.agregar_miembros(iObs.model, operaciones=[('onMissionEvent', 'void', [('event', 'MissionEvent')])])
al = v.clase('AuditMissionListener'); nl = v.clase('NotificationMissionListener')
mlc = v.clase('MissionLifecycleService', operaciones=[('submit', 'MissionResponse', [('id', 'Long')]), ('prepare', 'MissionResponse', [('id', 'Long')]),
                                                     ('start', 'MissionResponse', [('id', 'Long')]), ('pause', 'MissionResponse', [('id', 'Long'), ('reason', 'String')]),
                                                     ('resume', 'MissionResponse', [('id', 'Long')]), ('finish', 'MissionResponse', [('id', 'Long'), ('observations', 'String')]),
                                                     ('cancel', 'MissionResponse', [('id', 'Long'), ('reason', 'String')])])
sim = v.clase('TelemetrySimulator', [('LOW_BATTERY_THRESHOLD', 'int')], [('tick', 'void')], est='Scheduled')
pc(mission, 40, 40, 300, 380, NARANJA); pc(st, 40, 500, 250, 230)
pc(builder, 400, 40, 330, 150); pc(mlc, 400, 260, 340, 190)
pc(fps, 820, 40, 250, 60); pc(resolver, 820, 150, 290, 60); pc(sim, 1200, 40, 280, 90)
pc(iStr.model, 820, 280, 430, 170); pc(absst, 900, 540, 300, 60); pc(factory, 1300, 540, 340, 90)
for i, m in enumerate(strats): pc(m, 760 + i * 230, 700, 200, 50)
pc(pub, 360, 560, 340, 80); pc(iObs.model, 360, 760, 340, 70)
pc(al, 330, 900, 220, 50); pc(nl, 580, 900, 250, 50)
v.usa(d3b, P['Mission'], P['MissionStatus'], 'pregunta si el paso es válido')
v.usa(d3b, P['MissionBuilder'], P['Mission'], '«create» build()')
v.usa(d3b, P['FlightPlanService'], P['MissionStrategyResolver'])
v.usa(d3b, P['MissionStrategyResolver'], P['MissionStrategy'], 'elige según el tipo')
v.usa(d3b, P['TelemetrySimulator'], P['MissionStrategy'], 'consumo y velocidad')
v.realiza(d3b, P['AbstractMissionStrategy'], P['MissionStrategy'])
for m in strats: v.herencia(d3b, P[m.name], P['AbstractMissionStrategy'])
v.usa(d3b, P['MissionLifecycleService'], P['Mission'], 'changeStatus()')
v.usa(d3b, P['MissionLifecycleService'], P['MissionEventPublisher'], 'publish(evento)')
v.asociacion(d3b, P['MissionEventPublisher'], P['MissionEventListener'], '1', '0..*', 'notifica a')
v.realiza(d3b, P['AuditMissionListener'], P['MissionEventListener'])
v.realiza(d3b, P['NotificationMissionListener'], P['MissionEventListener'])
v.nota(d3b, 'STATE: MissionStatus guarda la tabla de transiciones; Mission.changeStatus() rechaza los pasos inválidos (409).\n'
            'BUILDER: MissionBuilder valida altitud 5–120 m, horario, fecha y cliente activo antes de crear.\n'
            'FACTORY: DroneFactory completa batería, velocidad y autonomía según el tipo.\n'
            'STRATEGY: una clase por tipo de misión; agregar un tipo = una clase nueva.\n'
            'OBSERVER: el ciclo de vida publica eventos; auditoría y notificaciones reaccionan sin acoplarse.', 1000, 820, 560, 160)

# ================================================================== 4. DESARROLLO (diagrama de clases por capas)
d4 = v.diagrama('ClassDiagram', '4 - Vista de desarrollo (clases por capas)', 2000, 1360)
COLS = ['Autenticación', 'Misiones', 'Drones', 'Pilotos', 'Mantenimiento', 'Telemetría']
X = [50 + i * 272 for i in range(6)]; W = 250
BANDS = [('Capa de presentación · frontend React (pages)', 20, 210, 'rgb(232, 246, 234)'),
         ('Capa de controladores · @RestController', 240, 200, 'rgb(235, 244, 252)'),
         ('Capa de servicios · @Service (reglas de negocio)', 460, 300, 'rgb(235, 244, 252)'),
         ('Capa de acceso a datos · MongoRepository', 780, 170, 'rgb(235, 244, 252)'),
         ('Capa de modelo · @Document (entidades)', 970, 220, 'rgb(255, 244, 230)')]
capa = {}
for nombre, y, h, color in BANDS:
    capa[nombre[:7] + str(y)] = v.paquete(d4, nombre, 20, y, 1670, h, color)
keys = list(capa)
def en(i_capa, nombre, atributos=(), ops=(), est=None):
    return v.clase(nombre, atributos, ops, est=est, padre=capa[keys[i_capa]].model)
def put(m, col, y, h, x_off=0, w=W): return v.figura(d4, m, X[col] + x_off, y, w, h)
# presentación
pg = [en(0, n, ops=o, est='page') for n, o in [
    ('LoginPage', [('login', 'void')]), ('MissionDetailPage', [('asignar', 'void'), ('despegar', 'void'), ('finalizar', 'void')]),
    ('DronesPage', [('crearDrone', 'void')]), ('PilotsPage', [('crearPiloto', 'void')]),
    ('MaintenancePage', [('iniciar', 'void'), ('completar', 'void')]), ('TelemetryPanel', [('polling 2 s', 'void')])]]
sPg = [put(m, i, 60, 80 if i != 1 else 110) for i, m in enumerate(pg)]
# controladores
ct = [en(1, n, ops=o, est='Controller') for n, o in [
    ('AuthController', [('login', 'LoginResponse'), ('register', 'MessageResponse'), ('me', 'UserInfo')]),
    ('MissionController', [('create', 'MissionResponse'), ('assign', 'MissionResponse'), ('start', 'MissionResponse'), ('finish', 'MissionResponse')]),
    ('DroneController', [('findAll', 'List<DroneResponse>'), ('create', 'DroneResponse'), ('changeStatus', 'DroneResponse')]),
    ('PilotController', [('findAll', 'List<PilotResponse>'), ('create', 'PilotResponse'), ('me', 'PilotResponse')]),
    ('MaintenanceController', [('create', 'MaintenanceResponse'), ('start', 'MaintenanceResponse'), ('complete', 'MaintenanceResponse')]),
    ('TelemetryController', [('feed', 'TelemetryFeed')])]]
sCt = [put(m, i, 280, 120 if i == 1 else 100) for i, m in enumerate(ct)]
# servicios
sv = [en(2, n, ops=o, est='Service') for n, o in [
    ('AuthService', [('login', 'LoginResponse'), ('register', 'MessageResponse'), ('forgotPassword', 'ForgotPasswordResponse')]),
    ('MissionService', [('create', 'MissionResponse'), ('update', 'MissionResponse'), ('flightPlan', 'FlightPlanResponse')]),
    ('DroneService', [('create', 'DroneResponse'), ('changeTechnicalStatus', 'DroneResponse'), ('chargeBattery', 'DroneResponse')]),
    ('PilotService', [('create', 'PilotResponse'), ('refreshLicenseStatuses', 'void')]),
    ('MaintenanceService', [('start', 'MaintenanceResponse'), ('complete', 'MaintenanceResponse'), ('cancel', 'MaintenanceResponse')]),
    ('TelemetryService', [('feed', 'TelemetryFeed'), ('summarize', 'TelemetrySummary')])]]
sSv = [put(m, i, 500, 100) for i, m in enumerate(sv)]
asgs = en(2, 'AssignmentService', ops=[('assign', 'MissionAssignment'), ('droneIneligibility', 'Optional<String>'), ('pilotIneligibility', 'Optional<String>')], est='Service')
sAsg = put(asgs, 1, 625, 110)
tsim = en(2, 'TelemetrySimulator', ops=[('tick', 'void')], est='Scheduled')
sSim = put(tsim, 5, 625, 70)
# repositorios
rp = [en(3, n, ops=o, est='Repository') for n, o in [
    ('UserRepository', [('existsByEmailIgnoreCase', 'boolean')]), ('MissionRepository', [('findByClientIdOrderByScheduledDateDesc', 'List<Mission>')]),
    ('DroneRepository', [('existsBySerialNumber', 'boolean')]), ('PilotRepository', [('existsByDocument', 'boolean')]),
    ('MaintenanceRepository', [('existsByDroneIdAndStatus', 'boolean')]), ('TelemetryRepository', [('findByMissionIdOrderByIdAsc', 'List<Telemetry>')])]]
sRp = [put(m, i, 830, 80) for i, m in enumerate(rp)]
# modelo
mo = [en(4, n, at, est='Document') for n, at in [
    ('User', [('email', 'String'), ('passwordHash', 'String'), ('role', 'Role')]),
    ('Mission', [('code', 'String'), ('status', 'MissionStatus'), ('flightPlan', 'List<Waypoint>')]),
    ('Drone', [('code', 'String'), ('status', 'DroneStatus'), ('batteryLevel', 'Integer')]),
    ('Pilot', [('licenseNumber', 'String'), ('licenseExpiresOn', 'LocalDate'), ('status', 'PilotStatus')]),
    ('Maintenance', [('type', 'MaintenanceType'), ('status', 'MaintenanceStatus'), ('cost', 'BigDecimal')]),
    ('Telemetry', [('altitudeM', 'Double'), ('battery', 'Integer'), ('simulated', 'boolean')])]]
sMo = [put(m, i, 1020, 110) for i, m in enumerate(mo)]
for i in range(6):
    v.depende(d4, sPg[i], sCt[i], 'HTTP /api')
    v.depende(d4, sCt[i], sSv[i], 'usa')
    if i == 1: v.depende(d4, sSv[1], sRp[1], 'usa', puntos=[(X[1], 560), (X[1] - 12, 560), (X[1] - 12, 860), (X[1], 860)])
    else: v.depende(d4, sSv[i] if i != 5 else sSim, sRp[i], 'usa')
    v.depende(d4, sRp[i], sMo[i], 'persiste')
v.depende(d4, sSv[1], sAsg)
# transversal
trans = v.paquete(d4, 'Transversal · seguridad, errores y persistencia', 1710, 20, 270, 1170, 'rgb(240, 236, 250)')
tr = [(n, o, e) for n, o, e in [
    ('SecurityConfig', [('securityFilterChain', 'SecurityFilterChain'), ('passwordEncoder', 'PasswordEncoder')], 'Configuration'),
    ('JwtAuthenticationFilter', [('doFilterInternal', 'void')], 'Component'),
    ('JwtService', [('generateToken', 'String'), ('extractValidSubject', 'Optional<String>')], 'Service'),
    ('GlobalExceptionHandler', [('handleBusiness', 'ResponseEntity<ApiError>'), ('handleNotFound', 'ResponseEntity<ApiError>')], 'RestControllerAdvice'),
    ('MongoUnitOfWorkTransactionManager', [('doBegin', 'void'), ('doCommit', 'void'), ('doRollback', 'void')], None),
    ('SequenceGeneratorService', [('next', 'long')], 'Service')]]
sTr = []
for k, (n, o, e) in enumerate(tr):
    m = v.clase(n, operaciones=o, est=e, padre=trans.model)
    sTr.append(v.figura(d4, m, 1725, 70 + k * 180, 240, 110))
v.depende(d4, sTr[1], sTr[2], 'valida token')
v.nota(d4, 'Diseño por capas: cada petición baja Presentación → Controller → Service → Repository → Modelo y nunca salta capas.\n'
           'Las capas son LÓGICAS: todas corren dentro del mismo app.jar (ver vista de despliegue).\n'
           'Cada módulo del backend (missions, drones, pilots…) repite esta misma estructura; aquí se muestran 6 de ellos.\n'
           'Columna transversal: la usan todas las capas (seguridad JWT, manejo global de errores, transacciones y ids de MongoDB).',
       20, 1220, 1100, 100)

# ================================================================== 5a. DESPLIEGUE (producción)
d5 = v.diagrama('DeploymentDiagram', '5a - Vista de despliegue (producción en Render)', 1660, 820)
eq = v.nodo(d5, 'Equipo del usuario (PC o celular)', 'device', 30, 80, 380, 420)
nav = v.nodo(d5, 'Navegador web', 'executionEnvironment', 60, 150, 320, 320, CELESTE)
v.artefacto(d5, 'SPA React (index.html + JS)', 100, 230)
v.artefacto(d5, 'Service worker (PWA, sw.js)', 100, 330)
srv = v.nodo(d5, 'Servidor de aplicación · Render', 'node', 600, 60, 520, 600)
dock = v.nodo(d5, 'Contenedor Docker', 'executionEnvironment', 630, 130, 460, 500, CELESTE)
jvm = v.nodo(d5, 'JVM · eclipse-temurin 17', 'executionEnvironment', 660, 200, 400, 250, CELESTE2)
jar = v.artefacto(d5, 'app.jar (Spring Boot + frontend en /static)', 690, 290, 340, 60)
upl = v.artefacto(d5, 'uploads/ (evidencias · disco efímero)', 690, 520, 340, 60, 'file')
atl = v.nodo(d5, 'Servicio administrado MongoDB Atlas', 'node', 1280, 160, 340, 330)
mgs = v.nodo(d5, 'MongoDB Server 7', 'executionEnvironment', 1310, 240, 280, 200, CELESTE2)
v.artefacto(d5, 'base de datos «dronemission»', 1330, 320, 240, 60, 'database')
v.comunicacion(d5, eq, srv, 'HTTPS :443')
v.comunicacion(d5, srv, atl, 'TLS :27017')
v.depende(d5, jar, upl, 'escribe archivos')
v.nota(d5, 'Un solo contenedor: el Dockerfile compila el frontend y lo mete en el .jar; Spring Boot lo sirve (SpaWebConfig). No hay Nginx ni Node en producción.\n'
           'Controller, Service y Repository NO son servidores: corren juntos dentro de app.jar.\n'
           'Render recibe HTTPS :443 y lo reenvía al puerto $PORT del contenedor; la conexión a Atlas usa mongodb+srv con TLS.\n'
           'Riesgo: uploads/ vive en disco efímero; en Render gratis las evidencias se pierden al reiniciar.', 30, 560, 520, 170)

# ================================================================== 5b. DESPLIEGUE (desarrollo local)
d5b = v.diagrama('DeploymentDiagram', '5b - Vista de despliegue (desarrollo local)', 1300, 620)
pc_ = v.nodo(d5b, 'Computador del desarrollador (Windows)', 'device', 30, 40, 1220, 470)
br = v.nodo(d5b, 'Navegador', 'executionEnvironment', 60, 110, 240, 160, CELESTE)
vt = v.nodo(d5b, 'Node.js · Vite (dev server :5173)', 'executionEnvironment', 360, 110, 260, 160, CELESTE)
jv = v.nodo(d5b, 'JVM · JDK 21', 'executionEnvironment', 680, 110, 260, 160, CELESTE)
mg = v.nodo(d5b, 'MongoDB Server local (:27017)', 'executionEnvironment', 980, 110, 240, 160, CELESTE)
v.artefacto(d5b, 'SPA React', 80, 180, 200, 50); v.artefacto(d5b, 'frontend/src', 380, 180, 220, 50)
v.artefacto(d5b, 'dronemission-backend (Spring Boot :8080)', 690, 180, 240, 50); v.artefacto(d5b, 'data/ (persistente)', 1000, 180, 200, 50, 'database')
v.comunicacion(d5b, br, vt, 'HTTP :5173'); v.comunicacion(d5b, vt, jv, 'HTTP :8080'); v.comunicacion(d5b, jv, mg, 'TCP :27017')
v.nota(d5b, 'Perfil «mongo»: iniciar-mongodb.bat + iniciar-backend-mongo-local.bat.\nPerfil «embedded»: el backend arranca su propio MongoDB temporal.', 60, 330, 520, 70)

v.guardar(os.path.join(OUT, 'DroneMission-Pro-Vistas.vpp'), os.path.join(OUT, 'png'), SOLO)
