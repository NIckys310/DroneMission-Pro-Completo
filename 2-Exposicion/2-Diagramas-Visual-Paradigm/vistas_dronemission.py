# -*- coding: utf-8 -*-
"""
DroneMission Pro · 5 vistas 4+1 (versión corregida para nota 5).
    python vistas_dronemission.py <carpeta_salida> [filtro...]
"""
import os, sys
sys.path.insert(0, os.path.expanduser(r'~/.claude/skills/vistas-4mas1-visual-paradigm/scripts'))
from vistas import *
from vpxml import nid

OUT = os.path.abspath(sys.argv[1] if len(sys.argv) > 1 else 'salida')
SOLO = sys.argv[2:]
v = Vistas('DroneMission-Pro-Vistas')
LD, LDT, BD, LT = 'LocalDate', 'LocalDateTime', 'BigDecimal', 'LocalTime'


# ------------------------------------------------------------------ ayudas propias
def ent(d, nombre, x, y, w, est, color=AZUL):
    """Entidad externa con estereotipo (queda como un rectángulo sin compartimentos vacíos)."""
    m = v.modelo('Class', nombre); v.etiquetar(m, est, base='Class')
    return v.figura(d, m, x, y, w, 34, color)


def _st(nombre, base):
    if not nombre: return ''
    s = v.estereotipo(nombre, base)
    return f'<Stereotypes><Stereotype Idref="{s.id}" Name={q_(nombre)}/></Stereotypes>'


def q_(t):
    from xml.sax.saxutils import quoteattr
    return quoteattr(t)


def dep(d, a, b, nombre='', puntos=None, est=None):
    """Dependencia punteada, opcionalmente con estereotipo («create», «use»...)."""
    r = v.rel('Dependency', a.model, b.model, nombre, inner=_st(est, 'Dependency') + '\n' if est else '')
    return v.linea(d, r, a, b, puntos)


def ruta(d, a, b, nombre, est, ma='', mb='', puntos=None):
    """Ruta de comunicación entre nodos con estereotipo de protocolo y multiplicidades."""
    inner = (_st(est, 'Association') +
             f'<FromEnd><AssociationEnd AggregationKind="None" EndModelElement="{a.model.id}" Id="{nid("ae")}" Multiplicity="{ma or "Unspecified"}" Navigable="Unspecified"/></FromEnd>'
             f'<ToEnd><AssociationEnd AggregationKind="None" EndModelElement="{b.model.id}" Id="{nid("ae")}" Multiplicity="{mb or "Unspecified"}" Navigable="Unspecified"/></ToEnd>\n')
    return v.linea(d, v.rel('Association', a.model, b.model, nombre, inner=inner), a, b, puntos)


def protegida(m, op, abstracta=False):
    """Marca una operación como protected (#) y, si se pide, abstracta (cursiva)."""
    nuevo = f'Name="{op}" Visibility="protected"' + (' Abstract="true"' if abstracta else '')
    m.extra = m.extra.replace(f'Name="{op}" Visibility="public"', nuevo)


def estatica(m, op):
    m.extra = m.extra.replace(f'Name="{op}" Visibility="public"', f'Name="{op}" Visibility="public" Scope="classifier"')


def flujo_recto(d, e, si, entra, sale, sep):
    import math
    cx, cy = e.x + e.w / 2, e.y + e.h / 2; sx, sy = si.x + si.w / 2, si.y + si.h / 2
    dx, dy = cx - sx, cy - sy; L = math.hypot(dx, dy); ux, uy = dx / L, dy / L; nx, ny = -uy, ux
    R = si.w / 2; t = min((e.w / 2) / abs(ux) if ux else 1e9, (e.h / 2) / abs(uy) if uy else 1e9)
    for nombre, hacia, off in ((entra, True, -sep), (sale, False, sep)):
        if not nombre: continue
        ps = (sx + ux * R + nx * off, sy + uy * R + ny * off); pe = (cx - ux * t + nx * off, cy - uy * t + ny * off)
        mid = ((ps[0] + pe[0]) / 2, (ps[1] + pe[1]) / 2)
        if hacia: v.linea(d, v._flecha(e.model, si.model, nombre), e, si, [pe, mid, ps])
        else: v.linea(d, v._flecha(si.model, e.model, nombre), si, e, [ps, mid, pe])


# ================================================================== 1. CONTEXTO
d1 = v.diagrama('ClassDiagram', '1 - Vista de contexto', 1560, 900)
sis = v.sistema(d1, 'DroneMission Pro', 660, 330, 240)
E = {
    'ad': ent(d1, 'Administrador', 40, 170, 230, 'persona'),
    'op': ent(d1, 'Operador', 40, 430, 230, 'persona'),
    'te': ent(d1, 'Técnico', 40, 690, 230, 'persona'),
    'pi': ent(d1, 'Piloto', 1290, 170, 230, 'persona'),
    'cl': ent(d1, 'Cliente', 1290, 430, 230, 'persona'),
    'vi': ent(d1, 'Visitante (sin cuenta)', 1290, 690, 230, 'persona'),
    'rl': ent(d1, 'Reloj del sistema', 665, 40, 230, 'evento temporal', LILA),
    'db': ent(d1, 'MongoDB Atlas (servicio administrado)', 630, 800, 300, 'sistema externo', GRIS),
}
S = 40
flujo_recto(d1, E['ad'], sis, 'usuarios, roles, permisos, flota', 'auditoría y reportes', sep=S)
flujo_recto(d1, E['op'], sis, 'misiones y asignaciones', 'dashboard, alertas, PDF', sep=S)
flujo_recto(d1, E['te'], sis, 'mantenimientos, estado técnico', 'drones a revisar', sep=S)
flujo_recto(d1, E['pi'], sis, 'chequeo, despegue, incidentes, evidencias', 'misiones asignadas, plan de vuelo', sep=S)
flujo_recto(d1, E['cl'], sis, 'consulta de sus misiones', 'estado, evidencias y PDF', sep=S)
flujo_recto(d1, E['vi'], sis, 'datos de registro', 'cuenta pendiente de aprobación', sep=S)
flujo_recto(d1, E['rl'], sis, 'señal de tiempo', '', sep=0)
flujo_recto(d1, E['db'], sis, 'documentos consultados', 'documentos a guardar', sep=60)
v.nota(d1, '«persona»: los 5 roles con cuenta y el visitante que pide una.\n'
           '«sistema externo»: MongoDB Atlas es un servicio de terceros que el sistema usa pero no controla (en desarrollo se cambia por MongoDB local).\n'
           '«evento temporal»: el reloj dispara las tareas @Scheduled (mover el drone virtual cada 2 s y revisar licencias a la 1:00 a. m.).\n'
           'El drone virtual (TelemetrySimulator) es INTERNO, por eso no es una entidad externa.',
       990, 760, 540, 120)

# ================================================================== 2. FUNCIONAL (componentes)
d2 = v.diagrama('ComponentDiagram', '2 - Vista funcional (componentes)', 1980, 960)
spa = v.componente(d2, 'Aplicación web · SPA React (páginas + cliente API Axios con token JWT)', 40, 30, 1860, 60, VERDE)
R1 = [('Seguridad y cuentas', '/api/auth · /api/users'), ('Flota y personal', '/api/drones · pilots · clients'),
      ('Gestión de misiones', '/api/missions'), ('Mantenimiento', '/api/maintenance'),
      ('Incidentes y evidencias', '/api/…/incidents · evidences')]
R2 = [('Telemetría (drone virtual @Scheduled)', '/api/missions/{id}/telemetry'), ('Reportes PDF y dashboard', '/api/reports · /api/dashboard'),
      ('Simulador 3D (planes de práctica)', '/api/simulator'), ('Auditoría', '/api/audit'), ('Notificaciones', '/api/notifications')]
K = {}
for fila, (lista, y, x0) in enumerate(((R1, 230, 40), (R2, 430, 220))):
    for i, (nom, api) in enumerate(lista):
        x = x0 + i * 360; cx = x + 120
        c = v.componente(d2, nom, x, y, 240, 64)
        b = v.interfaz(d2, api, cx - 10, y - 65)
        v.provee(d2, c, b)
        v.requiere(d2, spa, b, puntos=[(cx, 90), (cx, (90 + y - 65) / 2), (cx, y - 65)])
        K[nom.split(' (')[0].split(' ')[0]] = (c, cx)
seg, flo, mis, man, inc = (K[k] for k in ('Seguridad', 'Flota', 'Gestión', 'Mantenimiento', 'Incidentes'))
tel, rep, s3d, aud, noti = (K[k] for k in ('Telemetría', 'Reportes', 'Simulador', 'Auditoría', 'Notificaciones'))
# persistencia (bus a la izquierda)
iRepo = v.interfaz(d2, 'MongoRepository<T, Long>', 130, 610)
per = v.componente(d2, 'Persistencia (Spring Data MongoDB + transacciones)', 30, 700, 300, 64)
atl = v.componente(d2, 'MongoDB Atlas', 70, 830, 220, 60, GRIS, 'external system')
v.provee(d2, per, iRepo)
v.usa(d2, per, atl, 'mongodb+srv', kind='Dependency')
for c, cx in (seg, flo, mis, man, inc, tel, rep, s3d, aud, noti):
    yb = c.y + c.h
    v.requiere(d2, c, iRepo, puntos=[(cx - 40, yb), (cx - 40, 530), (140, 530), (140, 610)])
# estrategia (centro)
iStr = v.interfaz(d2, 'MissionStrategy', 890, 640)
pla = v.componente(d2, 'Planificación de vuelo (4 estrategias por tipo)', 780, 730, 240, 64)
v.provee(d2, pla, iStr)
for (c, cx), xf in ((mis, 900), (tel, 900), (rep, 900), (s3d, 900)):
    v.requiere(d2, c, iStr, puntos=[(cx + 40, c.y + c.h), (cx + 40, 600), (xf, 600), (xf, 640)])
# observador (derecha)
iObs = v.interfaz(d2, 'MissionEventListener', 1590, 600)
v.provee(d2, aud[0], iObs, puntos=[(aud[1] + 40, 494), (aud[1] + 40, 610), (1590, 610)])
v.provee(d2, noti[0], iObs, puntos=[(noti[1] - 40, 494), (noti[1] - 40, 610), (1610, 610)])
v.requiere(d2, mis[0], iObs, '', puntos=[(mis[0].x + 240, 280), (1090, 280), (1090, 330), (1240, 330), (1240, 565), (1600, 565), (1600, 600)])
# archivos
iFile = v.interfaz(d2, 'FileStorageService', 1770, 660)
fil = v.componente(d2, 'Almacenamiento de archivos (uploads/)', 1660, 760, 240, 64)
v.provee(d2, fil, iFile)
v.requiere(d2, inc[0], iFile, 'guarda fotos y videos', puntos=[(1720, 262), (1940, 262), (1940, 670), (1790, 670)])
v.nota(d2, 'Notación UML 2: círculo = interfaz OFRECIDA (línea continua hacia el componente que la implementa); flecha punteada hacia un círculo = interfaz REQUERIDA (dependencia «use»).\n'
           'Cada componente funcional ofrece su parte de la API REST y la SPA las consume.\n'
           'MissionStrategy (Strategy) y MissionEventListener (Observer) son interfaces reales del código; todos los componentes guardan a través de MongoRepository.\n'
           'Seguridad también filtra cada petición con el token JWT antes de que llegue a cualquier componente.',
       1060, 730, 560, 130)

# ================================================================== 3a. LÓGICA (dominio)
d3 = v.diagrama('ClassDiagram', '3a - Vista lógica (dominio)', 1960, 1770)
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
                              ('latitude', 'Double'), ('longitude', 'Double'), ('targetAltitudeM', 'Integer'), ('scheduledDate', LD), ('startTime', LT),
                              ('estimatedEndTime', LT), ('priority', 'MissionPriority'), ('status', 'MissionStatus'), ('objective', 'String'),
                              ('observations', 'String'), ('actualStart', LDT), ('actualEnd', LDT)],
                  [('changeStatus', 'void', [('target', 'MissionStatus')]), ('getActiveAssignment', 'Optional<MissionAssignment>'),
                   ('replaceFlightPlan', 'void', [('points', 'List<Waypoint>')])])
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

caja(d3, perm, 20, 40, 200, 80); caja(d3, role, 360, 40, 250, 90); caja(d3, user, 330, 190, 280, 230)
caja(d3, audm, 20, 190, 200, 150); caja(d3, notm, 700, 250, 220, 110)
caja(d3, client, 360, 620, 230, 150); caja(d3, repm, 360, 860, 230, 110)
caja(d3, mission, 700, 560, 330, 400, NARANJA); caja(d3, wp, 700, 1050, 240, 110)
caja(d3, pilot, 1150, 40, 300, 180); caja(d3, cert, 1560, 40, 240, 100)
caja(d3, asg, 1150, 330, 230, 80); caja(d3, drone, 1500, 290, 300, 300); caja(d3, mant, 1500, 700, 280, 200)
caja(d3, incm, 1150, 520, 250, 150); caja(d3, telm, 1150, 720, 250, 190); caja(d3, ev, 1150, 960, 250, 140)
A = lambda *a, **k: v.asociacion(d3, *a, **k)
A(C['User'], C['Role'], '0..*', '1', 'tiene')
A(C['Role'], C['Permission'], '0..*', '0..*', 'agrupa', 'Shared', puntos=[(220, 80), (290, 80), (360, 80)])
A(C['AuditLog'], C['User'], '0..*', '0..1', 'hecho por', puntos=[(220, 265), (275, 265), (330, 265)])
A(C['Notification'], C['User'], '0..*', '1', 'para', puntos=[(700, 300), (655, 300), (610, 300)])
A(C['Notification'], C['Mission'], '0..*', '0..1', 'sobre', puntos=[(880, 360), (880, 460), (880, 560)])
A(C['User'], C['Client'], '0..1', '0..1', 'accede como', puntos=[(460, 420), (460, 520), (460, 620)])
A(C['User'], C['Pilot'], '0..1', '0..1', 'accede como', puntos=[(610, 210), (880, 210), (1150, 210)])
A(C['User'], C['Mission'], '1', '0..*', 'crea', puntos=[(610, 400), (730, 400), (730, 560)])
A(C['Pilot'], C['PilotCertification'], '1', '0..*', 'embebe', 'Composited')
A(C['Client'], C['Mission'], '1', '0..*', 'contrata', puntos=[(590, 700), (645, 700), (700, 700)])
A(C['Report'], C['Mission'], '0..*', '0..1', 'resume', puntos=[(590, 910), (645, 910), (700, 910)])
A(C['Report'], C['User'], '0..*', '1', 'generado por', puntos=[(360, 915), (290, 915), (290, 400), (330, 400)])
A(C['Mission'], C['Waypoint'], '1', '2..*', 'plan de vuelo (embebido)', 'Composited')
A(C['Mission'], C['MissionAssignment'], '1', '0..*', 'historial')
A(C['MissionAssignment'], C['Pilot'], '0..*', '1'); A(C['MissionAssignment'], C['Drone'], '0..*', '1')
A(C['Mission'], C['Incident'], '1', '0..*', 'sufre'); A(C['Mission'], C['Telemetry'], '1', '0..*', 'registra')
A(C['Mission'], C['Evidence'], '1', '0..*', 'contiene'); A(C['Drone'], C['Maintenance'], '1', '0..*', 'recibe')
# enumeraciones (tipos de los atributos)
enp = v.paquete(d3, 'Enumeraciones · tipos de los atributos en MAYÚSCULAS', 20, 1190, 1920, 560, 'rgb(245, 245, 245)')
ENUMS = [('RoleName', 'ADMINISTRADOR OPERADOR PILOTO TECNICO CLIENTE'), ('ClientType', 'PERSONA EMPRESA'),
         ('PilotStatus', 'DISPONIBLE EN_MISION NO_DISPONIBLE LICENCIA_VENCIDA'), ('DroneType', 'INSPECCION AGRICULTURA VIGILANCIA TOPOGRAFIA FOTOGRAFIA'),
         ('DroneStatus', 'DISPONIBLE EN_MISION EN_MANTENIMIENTO FUERA_DE_SERVICIO'), ('MissionType', 'INSPECCION AGRICULTURA VIGILANCIA TOPOGRAFIA FOTOGRAFIA MONITOREO'),
         ('MissionPriority', 'BAJA MEDIA ALTA CRITICA'), ('WaypointAction', 'DESPEGUE PASO FOTO VIDEO ESCANEO FUMIGACION ATERRIZAJE'),
         ('IncidentType', 'BATERIA_BAJA PERDIDA_SENAL OBSTACULO ERROR_TECNICO CLIMA ATERRIZAJE_EMERGENCIA OTRO'), ('IncidentSeverity', 'BAJA MEDIA ALTA CRITICA'),
         ('IncidentStatus', 'ABIERTO EN_REVISION RESUELTO'), ('EvidenceType', 'FOTOGRAFIA VIDEO DOCUMENTO ARCHIVO OBSERVACION'),
         ('MaintenanceType', 'PREVENTIVO CORRECTIVO CALIBRACION REPARACION INSPECCION'), ('MaintenanceStatus', 'PROGRAMADO EN_PROCESO COMPLETADO CANCELADO'),
         ('ReportType', 'MISION DRONES PILOTOS MANTENIMIENTO')]
st = v.clase('MissionStatus', [('TRANSITIONS', 'Map<MissionStatus, Set<MissionStatus>>')],
             [('canTransitionTo', 'boolean', [('target', 'MissionStatus')]), ('allowedTransitions', 'Set<MissionStatus>'),
              ('isEditable', 'boolean'), ('isInOperation', 'boolean'), ('isTerminal', 'boolean')], est='enumeration',
             literales=('BORRADOR', 'PENDIENTE', 'PROGRAMADA', 'EN_PREPARACION', 'EN_VUELO', 'PAUSADA', 'FINALIZADA', 'CANCELADA'), padre=enp.model)
estatica(st, 'TRANSITIONS')
for i, (n, lits) in enumerate(ENUMS):
    m = v.clase(n, est='enumeration', literales=tuple(lits.split()), padre=enp.model)
    fila, col = divmod(i, 8)
    v.figura(d3, m, 40 + col * 222, 1230 + fila * 205, 205, 40 + 15 * len(lits.split()) + 4)
sST = v.figura(d3, st, 1594, 1435, 330, 285)
v.nota(d3, 'Rombo negro (composición) = embebido y sin vida propia: Waypoint vive dentro de Mission y PilotCertification dentro de Pilot.\n'
           'Rombo blanco (agregación) = Role guarda copias embebidas de Permission, pero Permission también existe sola en su catálogo (PermissionRepository).\n'
           'Línea simple = colección aparte unida por referencia (@DBRef o id): borrar una misión no borra su telemetría.',
       1440, 950, 380, 200)

# ================================================================== 3b. LÓGICA (patrones)
d3b = v.diagrama('ClassDiagram', '3b - Vista lógica (patrones de diseño)', 2060, 1300)
P = {}
def pc(m, x, y, w, h, color=None): P[m.name] = v.figura(d3b, m, x, y, w, h, color); return P[m.name]
builder = v.clase('MissionBuilder', [('MIN_ALTITUDE_M', 'int'), ('MAX_ALTITUDE_M', 'int')],
                  [('code', 'MissionBuilder', [('code', 'String')]), ('name', 'MissionBuilder', [('name', 'String')]), ('type', 'MissionBuilder', [('type', 'MissionType')]),
                   ('client', 'MissionBuilder', [('client', 'Client')]), ('altitude', 'MissionBuilder', [('altitudeM', 'Integer')]),
                   ('priority', 'MissionBuilder', [('priority', 'MissionPriority')]),
                   ('schedule', 'MissionBuilder', [('date', LD), ('start', LT), ('estimatedEnd', LT)]), ('build', 'Mission')])
msvc = v.clase('MissionService', operaciones=[('create', 'MissionResponse', [('req', 'MissionRequest')])], est='Service')
mlc = v.clase('MissionLifecycleService', operaciones=[('submit', 'MissionResponse', [('id', 'Long')]), ('prepare', 'MissionResponse', [('id', 'Long')]),
                                                     ('start', 'MissionResponse', [('id', 'Long')]), ('pause', 'MissionResponse', [('id', 'Long'), ('reason', 'String')]),
                                                     ('resume', 'MissionResponse', [('id', 'Long')]), ('finish', 'MissionResponse', [('id', 'Long'), ('observations', 'String')]),
                                                     ('cancel', 'MissionResponse', [('id', 'Long'), ('reason', 'String')])], est='Service')
pub = v.clase('MissionEventPublisher', [('listeners', 'List<MissionEventListener>')], [('publish', 'void', [('event', 'MissionEvent')])])
iObsM = iObs.model
v.agregar_miembros(iObsM, operaciones=[('onMissionEvent', 'void', [('event', 'MissionEvent')])])
mev = v.clase('MissionEvent', [('type', 'MissionEventType'), ('mission', 'Mission'), ('detail', 'String')], est='record')
al = v.clase('AuditMissionListener', operaciones=[('onMissionEvent', 'void', [('event', 'MissionEvent')])])
nl = v.clase('NotificationMissionListener', operaciones=[('onMissionEvent', 'void', [('event', 'MissionEvent')])])
fps = v.clase('FlightPlanService', operaciones=[('generate', 'void', [('mission', 'Mission')])], est='Service')
resolver = v.clase('MissionStrategyResolver', operaciones=[('forType', 'MissionStrategy', [('type', 'MissionType')])])
iStrM = iStr.model
v.agregar_miembros(iStrM, operaciones=[('name', 'String'), ('supportedTypes', 'Set<MissionType>'), ('minimumBatteryPercent', 'int'), ('compatibleDroneTypes', 'Set<DroneType>'),
                                       ('cruiseSpeedKmh', 'double'), ('batteryDrainPerMinute', 'double'),
                                       ('generateFlightPlan', 'List<PlannedWaypoint>', [('center', 'GeoPoint'), ('altitudeM', 'int')])])
absst = v.clase('AbstractMissionStrategy', operaciones=[('generateFlightPlan', 'List<PlannedWaypoint>', [('center', 'GeoPoint'), ('altitudeM', 'int')]),
                                                       ('generatePattern', 'List<PlannedWaypoint>', [('center', 'GeoPoint'), ('altitudeM', 'int')])], est='abstract')
protegida(absst, 'generatePattern', abstracta=True)
strats = []
for n in ('InspectionStrategy', 'AgricultureStrategy', 'SurveillanceStrategy', 'MappingStrategy'):
    m = v.clase(n, operaciones=[('generatePattern', 'List<PlannedWaypoint>', [('center', 'GeoPoint'), ('altitudeM', 'int')])])
    protegida(m, 'generatePattern'); strats.append(m)
sim = v.clase('TelemetrySimulator', [('LOW_BATTERY_THRESHOLD', 'int')], [('tick', 'void')], est='Scheduled')
factory = v.clase('DroneFactory', [('SPECS', 'Map<DroneType, DroneSpec>')],
                  [('createDrone', 'Drone', [('request', 'DroneRequest'), ('nextSequence', 'long')]), ('specOf', 'DroneSpec', [('type', 'DroneType')])])
estatica(factory, 'specOf'); estatica(factory, 'SPECS')
dsvc = v.clase('DroneService', operaciones=[('create', 'DroneResponse', [('req', 'DroneRequest')])], est='Service')

pc(mission, 40, 40, 330, 400, NARANJA); P['MissionStatus'] = v.figura(d3b, st, 40, 520, 330, 300)
pc(mev, 40, 900, 330, 90)
pc(builder, 470, 40, 500, 200); pc(msvc, 470, 290, 400, 60); pc(mlc, 470, 400, 400, 180)
pc(pub, 470, 660, 400, 80); pc(iObsM, 470, 820, 400, 70)
pc(al, 470, 980, 260, 70); pc(nl, 750, 980, 270, 70)
pc(fps, 1040, 40, 300, 60); pc(resolver, 1040, 150, 360, 60); pc(sim, 1560, 40, 330, 90)
pc(iStrM, 1040, 280, 480, 180); pc(absst, 1040, 560, 470, 90)
for i, m in enumerate(strats): pc(m, 1580, 520 + i * 90, 440, 70)
pc(factory, 1040, 900, 440, 100); pc(dsvc, 1040, 1080, 360, 60)
P['Drone'] = v.figura(d3b, drone, 1580, 900, 300, 310)
# STATE
v.asociacion(d3b, P['Mission'], P['MissionStatus'], '0..*', '1', 'estado actual')
v.usa(d3b, P['MissionLifecycleService'], P['Mission'], 'changeStatus()', puntos=[(470, 420), (420, 420), (370, 420)])
# BUILDER
dep(d3b, P['MissionBuilder'], P['Mission'], 'build()', est='create', puntos=[(470, 140), (420, 140), (370, 140)])
v.usa(d3b, P['MissionService'], P['MissionBuilder'], 'arma la misión')
# OBSERVER
v.usa(d3b, P['MissionLifecycleService'], P['MissionEventPublisher'], 'publish(evento)')
v.asociacion(d3b, P['MissionEventPublisher'], P['MissionEventListener'], '1', '0..*', 'notifica a')
v.realiza(d3b, P['AuditMissionListener'], P['MissionEventListener'])
v.realiza(d3b, P['NotificationMissionListener'], P['MissionEventListener'])
dep(d3b, P['MissionEventPublisher'], P['MissionEvent'], 'publica', puntos=[(470, 700), (400, 700), (400, 930), (370, 930)])
dep(d3b, P['MissionEventListener'], P['MissionEvent'], 'recibe', puntos=[(470, 860), (430, 860), (430, 965), (370, 965)])
# STRATEGY + TEMPLATE METHOD
v.usa(d3b, P['FlightPlanService'], P['MissionStrategyResolver'], 'pide la estrategia')
v.usa(d3b, P['MissionStrategyResolver'], P['MissionStrategy'], 'elige según el tipo')
v.usa(d3b, P['TelemetrySimulator'], P['MissionStrategy'], 'velocidad y consumo', puntos=[(1720, 130), (1720, 370), (1520, 370)])
v.realiza(d3b, P['AbstractMissionStrategy'], P['MissionStrategy'])
for i, m in enumerate(strats):
    yy = 520 + i * 90 + 35
    v.linea(d3b, v.rel('Generalization', P['AbstractMissionStrategy'].model, P[m.name].model), P['AbstractMissionStrategy'], P[m.name],
            [(1510, 605), (1545, 605), (1545, yy), (1580, yy)])
# FACTORY
dep(d3b, P['DroneFactory'], P['Drone'], 'createDrone()', est='create', puntos=[(1480, 950), (1530, 950), (1580, 950)])
v.usa(d3b, P['DroneService'], P['DroneFactory'], 'fabrica el drone')
v.nota(d3b, 'STATE (variante con enum): contexto = Mission; estados = constantes de MissionStatus. Cada estado responde canTransitionTo(), isEditable(), isTerminal(); changeStatus() rechaza los pasos inválidos (409). Se usó enum porque los estados solo cambian reglas, no algoritmos.\n'
            'BUILDER: MissionService (director) arma la misión paso a paso; build() valida altitud 5–120 m, horario y cliente activo.\n'
            'OBSERVER: el ciclo de vida publica MissionEvent; auditoría y notificaciones reaccionan sin que la misión las conozca.\n'
            'STRATEGY + TEMPLATE METHOD: una estrategia por tipo de misión; generateFlightPlan() es la plantilla (despegue + patrón + aterrizaje) y cada subclase solo redefine generatePattern().\n'
            'FACTORY: DroneFactory completa batería, velocidad y autonomía según DroneType.',
       40, 1080, 940, 150)

# ================================================================== 4. DESARROLLO (clases por capas)
d4 = v.diagrama('ClassDiagram', '4 - Vista de desarrollo (clases por capas)', 2980, 1460)
CW = 380; GAP = 20
XC = {'auth': 40, 'misA': 440, 'misB': 840, 'dro': 1240, 'man': 1640, 'tel': 2040}   # x de cada columna
BANDS = [('Capa de presentación · frontend React (pages, api, http)', 20, 330, 'rgb(232, 246, 234)'),
         ('Capa de controladores · @RestController', 370, 160, 'rgb(235, 244, 252)'),
         ('Capa de servicios · @Service (reglas de negocio)', 680, 290, 'rgb(235, 244, 252)'),
         ('Capa de acceso a datos · MongoRepository', 990, 130, 'rgb(235, 244, 252)'),
         ('Capa de modelo · @Document (entidades)', 1140, 170, 'rgb(255, 244, 230)')]
capa = [v.paquete(d4, n, 20, y, 2420, h, c) for n, y, h, c in BANDS]
def en(i, nombre, at=(), ops=(), est=None):
    m = v.clase(nombre, at, ops, est=est, padre=capa[i].model); m.banda = capa[i]; return m
def put(m, col, y, h, dx=0, w=CW): return v.figura(d4, m, XC[col] + dx, y, w, h)
cx = lambda col, dx=CW / 2: XC[col] + dx
# presentación
pg = {'auth': ('LoginPage', [('handleSubmit', 'Promise<void>', [('e', 'FormEvent')])]),
      'misA': ('MissionsPage', [('create', 'Promise<void>', [('values', 'Record<string, unknown>')])]),
      'misB': ('MissionDetailPage', [('run', 'Promise<void>', [('action', '() => Promise<Mission>')])]),
      'dro': ('DronesPage', [('save', 'Promise<void>', [('values', 'Record<string, unknown>')]), ('deactivate', 'Promise<void>')]),
      'man': ('MaintenancePage', [('act', 'Promise<void>', [('fn', '() => Promise<Maintenance>')])]),
      'tel': ('TelemetryPanel', [])}
api = {'auth': ('authApi', [('login', 'Promise<LoginResponse>', [('identifier', 'string'), ('password', 'string')])]),
       'misA': ('missionApi', [('create', 'Promise<Mission>', [('body', 'unknown')]), ('start', 'Promise<Mission>', [('id', 'number')]),
                               ('telemetry', 'Promise<TelemetryFeed>', [('id', 'number')])]),
       'dro': ('droneApi', [('create', 'Promise<Drone>', [('body', 'unknown')])]),
       'man': ('maintenanceApi', [('start', 'Promise<Maintenance>', [('id', 'number')])]),
       'tel': ('useFetch', [('useFetch', '{data, loading}', [('loader', '() => Promise<T>'), ('deps', 'unknown[]'), ('pollMs', 'number')])])}
sPg = {k: put(en(0, n, ops=o, est='page'), k, 60, 70) for k, (n, o) in pg.items()}
sApi = {k: put(en(0, n, ops=o, est='module' if k != 'tel' else 'hook'), k, 160, 80) for k, (n, o) in api.items()}
http = en(0, 'http (client.ts)', [('baseURL', "'/api'")], [('interceptors.request', 'agrega Authorization: Bearer <token>')], est='axios')
sHttp = v.figura(d4, http, 40, 270, 2380, 66)
for k in pg:
    dst = sApi['misA'] if k == 'misB' else sApi[k]
    if k == 'misB':
        dep(d4, sPg[k], dst, '', puntos=[(cx('misB'), 130), (cx('misB'), 145), (cx('misA', 300), 145), (cx('misA', 300), 160)])
    else:
        dep(d4, sPg[k], dst, '')
for k in api:
    dep(d4, sApi[k], sHttp, '', puntos=[(cx(k), 240), (cx(k), 255), (cx(k) + 0.5, 270)])
# contratos DTO
F = {'LoginRequest': [('identifier', 'String'), ('password', 'String')], 'LoginResponse': [('token', 'String'), ('user', 'UserInfo')],
     'MissionRequest': [('name', 'String'), ('clientId', 'Long')], 'MissionResponse': [('id', 'Long'), ('code', 'String')],
     'AssignRequest': [('droneId', 'Long'), ('pilotId', 'Long')], 'ObservationRequest': [('text', 'String')],
     'DroneRequest': [('serialNumber', 'String'), ('type', 'DroneType')], 'DroneResponse': [('id', 'Long'), ('code', 'String')],
     'MaintenanceRequest': [('droneId', 'Long'), ('type', 'MaintenanceType')], 'MaintenanceResponse': [('id', 'Long'), ('status', 'String')],
     'TelemetryFeed': [('simulated', 'boolean'), ('points', 'List<TelemetryPoint>')], 'TelemetrySummary': [('distanceM', 'double'), ('batteryUsed', 'int')]}
sDto = {}
dto = {'auth': ('LoginRequest', 'LoginResponse'), 'misA': ('MissionRequest', 'MissionResponse'), 'misB': ('AssignRequest', 'ObservationRequest'),
       'dro': ('DroneRequest', 'DroneResponse'), 'man': ('MaintenanceRequest', 'MaintenanceResponse'), 'tel': ('TelemetryFeed', 'TelemetrySummary')}
for k, (a, b) in dto.items():
    sDto[a] = put(v.clase(a, F[a], est='record'), k, 560, 72, 0, 180); sDto[b] = put(v.clase(b, F[b], est='record'), k, 560, 72, 200, 180)
# controladores
ct = {'auth': ('AuthController', [('login', 'LoginResponse', [('request', 'LoginRequest')]), ('register', 'MessageResponse', [('request', 'RegisterRequest')]), ('me', 'UserInfo')]),
      'misA': ('MissionController', [('create', 'MissionResponse', [('request', 'MissionRequest')]), ('assign', 'MissionResponse', [('id', 'Long'), ('request', 'AssignRequest')]),
                                     ('start', 'MissionResponse', [('id', 'Long')]), ('finish', 'MissionResponse', [('id', 'Long'), ('request', 'ObservationRequest')])]),
      'dro': ('DroneController', [('findAll', 'List<DroneResponse>', [('q', 'String')]), ('create', 'DroneResponse', [('request', 'DroneRequest')]),
                                  ('changeStatus', 'DroneResponse', [('id', 'Long'), ('request', 'DroneStatusRequest')])]),
      'man': ('MaintenanceController', [('create', 'MaintenanceResponse', [('request', 'MaintenanceRequest')]), ('start', 'MaintenanceResponse', [('id', 'Long')]),
                                        ('complete', 'MaintenanceResponse', [('id', 'Long'), ('request', 'MaintenanceCompleteRequest')])]),
      'tel': ('TelemetryController', [('feed', 'TelemetryFeed', [('missionId', 'Long')])])}
sCt = {k: put(en(1, n, ops=o, est='Controller'), k, 410, 110) for k, (n, o) in ct.items()}
for k in ct:
    dep(d4, sHttp, sCt[k], 'HTTP /api', puntos=[(cx(k), 336), (cx(k), 373), (cx(k) + 0.5, 410)])
# servicios
sv = {'auth': ('AuthService', [('login', 'LoginResponse', [('request', 'LoginRequest')]), ('register', 'MessageResponse', [('request', 'RegisterRequest')]),
                               ('forgotPassword', 'ForgotPasswordResponse', [('request', 'ForgotPasswordRequest')])]),
      'misA': ('MissionService', [('create', 'MissionResponse', [('req', 'MissionRequest')]), ('update', 'MissionResponse', [('id', 'Long'), ('req', 'MissionRequest')]),
                                  ('flightPlan', 'FlightPlanResponse', [('id', 'Long')])]),
      'misB': ('MissionLifecycleService', [('start', 'MissionResponse', [('id', 'Long')]), ('finish', 'MissionResponse', [('id', 'Long'), ('observations', 'String')]),
                                           ('cancel', 'MissionResponse', [('id', 'Long'), ('reason', 'String')])]),
      'dro': ('DroneService', [('create', 'DroneResponse', [('req', 'DroneRequest')]), ('changeTechnicalStatus', 'DroneResponse', [('id', 'Long'), ('req', 'DroneStatusRequest')]),
                               ('chargeBattery', 'DroneResponse', [('id', 'Long')])]),
      'man': ('MaintenanceService', [('create', 'MaintenanceResponse', [('req', 'MaintenanceRequest')]), ('start', 'MaintenanceResponse', [('id', 'Long')]),
                                     ('complete', 'MaintenanceResponse', [('id', 'Long'), ('req', 'MaintenanceCompleteRequest')])]),
      'tel': ('TelemetryService', [('feed', 'TelemetryFeed', [('missionId', 'Long')]), ('summarize', 'TelemetrySummary', [('missionId', 'Long')])])}
sSv = {k: put(en(2, n, ops=o, est='Service'), k, 720, 100) for k, (n, o) in sv.items()}
sv2 = {'misA': en(2, 'MissionMapper', ops=[('toResponse', 'MissionResponse', [('m', 'Mission'), ('actions', 'List<String>')])], est='Mapper'),
       'misB': en(2, 'AssignmentService', ops=[('assign', 'MissionResponse', [('missionId', 'Long'), ('request', 'AssignRequest')]),
                                                ('validateReadyForFlight', 'MissionAssignment', [('mission', 'Mission')])], est='Service'),
       'dro': en(2, 'DroneMapper', ops=[('toResponse', 'DroneResponse', [('d', 'Drone')]), ('apply', 'void', [('req', 'DroneRequest'), ('drone', 'Drone')])], est='Mapper'),
       'tel': en(2, 'TelemetrySimulator', ops=[('tick', 'void')], est='Scheduled')}
for m in (sv2['misA'], sv2['dro']): estatica(m, 'toResponse')
estatica(sv2['dro'], 'apply')
sSv2 = {k: put(m, k, 880, 80, 90, CW - 90) for k, m in sv2.items()}
for k in ct:
    dep(d4, sCt[k], sSv[k], 'usa', puntos=[(cx(k), 520), (cx(k), 620), (cx(k) + 0.5, 720)])
dep(d4, sCt['misA'], sSv['misB'], 'usa', puntos=[(XC['misA'] + CW, 470), (cx('misB'), 470), (cx('misB'), 560), (cx('misB') + 0.5, 720)])
for k in ('misA', 'dro'):
    dep(d4, sSv[k], sSv2[k], 'convierte', puntos=[(cx(k, 240), 820), (cx(k, 240), 850), (cx(k, 240) + 0.5, 880)])
dep(d4, sSv['misB'], sSv2['misB'], 'valida', puntos=[(cx('misB', 240), 820), (cx('misB', 240), 850), (cx('misB', 240) + 0.5, 880)])
# repositorios
rp = {'auth': ('UserRepository', [('existsByEmailIgnoreCase', 'boolean', [('email', 'String')])]),
      'misA': ('MissionRepository', [('findByClientIdOrderByScheduledDateDesc', 'List<Mission>', [('clientId', 'Long')])]),
      'dro': ('DroneRepository', [('existsBySerialNumber', 'boolean', [('serial', 'String')])]),
      'man': ('MaintenanceRepository', [('existsByDroneIdAndStatus', 'boolean', [('droneId', 'Long'), ('status', 'MaintenanceStatus')])]),
      'tel': ('TelemetryRepository', [('findByMissionIdOrderByIdAsc', 'List<Telemetry>', [('missionId', 'Long')])])}
sRp = {k: put(en(3, n, ops=o, est='Repository'), k, 1030, 70) for k, (n, o) in rp.items()}
for k in rp:
    dep(d4, sSv[k], sRp[k], 'usa', puntos=[(cx(k, 50), 820), (cx(k, 50), 975), (cx(k, 50) + 0.5, 1030)])
dep(d4, sSv2['tel'], sRp['tel'], 'guarda lecturas', puntos=[(cx('tel', 230), 960), (cx('tel', 230), 995), (cx('tel', 230) + 0.5, 1030)])
dep(d4, sSv['misB'], sRp['misA'], 'usa', puntos=[(cx('misB', 30), 820), (cx('misB', 30), 990), (cx('misA', 300), 990), (cx('misA', 300), 1030)])
# modelo
mo = {'auth': ('User', [('email', 'String'), ('passwordHash', 'String'), ('role', 'Role')]),
      'misA': ('Mission', [('code', 'String'), ('status', 'MissionStatus'), ('flightPlan', 'List<Waypoint>')]),
      'misB': ('MissionAssignment', [('drone', 'Drone'), ('pilot', 'Pilot'), ('active', 'boolean')]),
      'dro': ('Drone', [('code', 'String'), ('status', 'DroneStatus'), ('batteryLevel', 'Integer')]),
      'man': ('Maintenance', [('type', 'MaintenanceType'), ('status', 'MaintenanceStatus'), ('cost', 'BigDecimal')]),
      'tel': ('Telemetry', [('altitudeM', 'Double'), ('battery', 'Integer'), ('simulated', 'boolean')])}
sMo = {k: put(en(4, n, at, est='Document'), k, 1180, 110) for k, (n, at) in mo.items()}
for k in rp:
    dep(d4, sRp[k], sMo[k], 'persiste', puntos=[(cx(k), 1100), (cx(k), 1140), (cx(k) + 0.5, 1180)])
# transversal
trans = v.paquete(d4, 'Transversal · seguridad, errores y persistencia', 2600, 20, 360, 1290, 'rgb(240, 236, 250)')
TR = [('SecurityConfig', [('securityFilterChain', 'SecurityFilterChain', [('http', 'HttpSecurity')]), ('passwordEncoder', 'PasswordEncoder')], 'Configuration'),
      ('JwtAuthenticationFilter', [('doFilterInternal', 'void', [('req', 'HttpServletRequest'), ('res', 'HttpServletResponse'), ('chain', 'FilterChain')])], 'Component'),
      ('JwtService', [('generateToken', 'String', [('user', 'User')]), ('extractValidSubject', 'Optional<String>', [('token', 'String')])], 'Service'),
      ('GlobalExceptionHandler', [('handleBusiness', 'ResponseEntity<ApiError>', [('ex', 'BusinessRuleException')]), ('handleNotFound', 'ResponseEntity<ApiError>', [('ex', 'ResourceNotFoundException')])], 'RestControllerAdvice'),
      ('CurrentUserService', [('get', 'AuthenticatedUser'), ('getEntity', 'User')], 'Service'),
      ('MongoUnitOfWorkTransactionManager', [('doBegin', 'void'), ('doCommit', 'void'), ('doRollback', 'void')], None),
      ('MongoLifecycleListener', [('onBeforeConvert', 'void', [('event', 'BeforeConvertEvent<Object>')])], 'Component'),
      ('SequenceGeneratorService', [('next', 'long', [('collectionName', 'String')])], 'Service')]
sTr = {}
for i, (n, o, e) in enumerate(TR):
    sTr[n] = v.figura(d4, v.clase(n, operaciones=o, est=e, padre=trans.model), 2615, 60 + i * 155, 330, 100)
dep(d4, sTr['SecurityConfig'], sTr['JwtAuthenticationFilter'], 'registra')
dep(d4, sTr['JwtAuthenticationFilter'], sTr['JwtService'], 'valida token')
dep(d4, sTr['MongoLifecycleListener'], sTr['SequenceGeneratorService'], 'pide id')
dep(d4, sTr['JwtAuthenticationFilter'], capa[1], 'filtra antes de', puntos=[(2615, 290), (2530, 290), (2530, 420), (2440, 420)])
dep(d4, sTr['GlobalExceptionHandler'], capa[1], 'atiende errores de', puntos=[(2615, 560), (2560, 560), (2560, 500), (2440, 500)])
dep(d4, capa[2], sTr['CurrentUserService'], 'usuario actual', puntos=[(2440, 730), (2530, 730), (2615, 730)])
dep(d4, capa[2], sTr['MongoUnitOfWorkTransactionManager'], '@Transactional', puntos=[(2440, 880), (2530, 880), (2615, 880)])
dep(d4, sTr['MongoLifecycleListener'], capa[4], 'rellena id y fechas', puntos=[(2615, 1040), (2530, 1040), (2530, 1220), (2440, 1220)])
v.nota(d4, 'Diseño por capas: cada petición baja Presentación → Controller → Service → Repository → Modelo y nunca salta capas. Fila «record» entre controladores y servicios = contratos DTO que viajan como JSON: el Controller recibe un Request y devuelve un Response; los Mapper convierten la entidad en Response.\n'
           'Las capas son LÓGICAS: todas corren dentro del mismo app.jar (ver vista de despliegue). Cada módulo del backend repite esta estructura; aquí se muestran 5 (Misiones ocupa dos columnas).\n'
           'Columna transversal: seguridad JWT, manejo global de errores, usuario actual, transacciones e ids numéricos de MongoDB.',
       20, 1340, 1500, 80)

# ================================================================== 5a. DESPLIEGUE (producción)
d5 = v.diagrama('DeploymentDiagram', '5a - Vista de despliegue (producción en Render)', 1700, 860)
eq = v.nodo(d5, 'Equipo del usuario (PC o celular)', 'device', 30, 80, 380, 420)
nav = v.nodo(d5, 'Navegador web', 'executionEnvironment', 60, 150, 320, 320, CELESTE)
spaA = v.artefacto(d5, 'SPA React (index.html + JS)', 100, 230)
v.artefacto(d5, 'Service worker (PWA, sw.js)', 100, 330)
srv = v.nodo(d5, 'Servidor de aplicación · Render', 'node', 600, 60, 520, 600)
dock = v.nodo(d5, 'Contenedor Docker (imagen eclipse-temurin:17-jre)', 'executionEnvironment', 630, 130, 460, 500, CELESTE)
jvm = v.nodo(d5, 'JVM · Java 17', 'executionEnvironment', 660, 200, 400, 250, CELESTE2)
jar = v.artefacto(d5, 'app.jar (Spring Boot + frontend en /static)', 690, 290, 340, 60)
upl = v.artefacto(d5, 'uploads/ (evidencias · disco efímero)', 690, 520, 340, 60, 'file')
atl5 = v.nodo(d5, 'Servicio administrado MongoDB Atlas', 'node', 1280, 160, 380, 330)
mgs = v.nodo(d5, 'MongoDB Server 7', 'executionEnvironment', 1310, 240, 320, 200, CELESTE2)
v.artefacto(d5, 'base de datos «dronemission»', 1350, 320, 240, 60, 'database')
ruta(d5, eq, srv, ':443', 'HTTPS', '*', '1', puntos=[(410, 150), (505, 150), (600, 150)])
ruta(d5, srv, atl5, 'mongodb+srv :27017', 'TLS', '1', '1', puntos=[(1120, 400), (1200, 400), (1280, 400)])
dep(d5, spaA, jar, 'se descarga de /static', puntos=[(330, 258), (520, 258), (520, 320), (690, 320)])
dep(d5, jar, upl, 'escribe archivos')
v.nota(d5, 'Un solo contenedor: el Dockerfile compila el frontend y lo mete en el .jar; Spring Boot lo sirve (SpaWebConfig). No hay Nginx ni Node en producción.\n'
           'Controller, Service y Repository NO son servidores: corren juntos dentro de app.jar.\n'
           'Render recibe HTTPS :443 y lo reenvía al puerto $PORT del contenedor; la conexión a Atlas usa mongodb+srv con TLS.\n'
           'Riesgo: uploads/ vive en disco efímero; en Render gratis las evidencias se pierden al reiniciar.', 30, 560, 520, 170)

# ================================================================== 5b. DESPLIEGUE (desarrollo local)
d5b = v.diagrama('DeploymentDiagram', '5b - Vista de despliegue (desarrollo local)', 1620, 660)
pcd = v.nodo(d5b, 'Computador del desarrollador (Windows)', 'device', 30, 30, 1560, 600)
br = v.nodo(d5b, 'Navegador', 'executionEnvironment', 60, 110, 250, 170, CELESTE)
vt = v.nodo(d5b, 'Node.js 20 · Vite (dev server)', 'executionEnvironment', 470, 110, 260, 170, CELESTE)
jv = v.nodo(d5b, 'JVM · Java 17 (JDK 17 o superior)', 'executionEnvironment', 890, 110, 270, 170, CELESTE)
mg = v.nodo(d5b, 'MongoDB Server local · perfil «mongo»', 'executionEnvironment', 1310, 70, 260, 180, CELESTE)
me = v.nodo(d5b, 'MongoDB embebido (Flapdoodle) · perfil «embedded»', 'executionEnvironment', 1310, 300, 260, 180, CELESTE2)
v.artefacto(d5b, 'SPA React (index.html + módulos ES)', 75, 180, 220, 60)
v.artefacto(d5b, 'módulos TS transpilados en memoria (HMR)', 485, 180, 230, 60)
v.artefacto(d5b, 'dronemission-backend (spring-boot:run)', 905, 180, 240, 60)
v.artefacto(d5b, 'mongodb-local/data (persistente)', 1325, 150, 230, 60, 'database')
v.artefacto(d5b, 'base temporal (se borra al cerrar)', 1325, 380, 230, 60, 'database')
ruta(d5b, br, vt, ':5173', 'HTTP', puntos=[(310, 195), (390, 195), (470, 195)])
ruta(d5b, vt, jv, ':8080 (proxy /api)', 'HTTP', puntos=[(730, 195), (810, 195), (890, 195)])
ruta(d5b, jv, mg, ':27017', 'TCP', puntos=[(1160, 160), (1235, 160), (1310, 160)])
ruta(d5b, jv, me, 'puerto aleatorio', 'TCP', puntos=[(1160, 250), (1235, 250), (1235, 390), (1310, 390)])
v.nota(d5b, 'Perfil «mongo»: iniciar-mongodb.bat + iniciar-backend-mongo-local.bat; los datos quedan en mongodb-local/data.\n'
            'Perfil «embedded» (por defecto): el backend descarga y arranca un MongoDB 7 temporal; sirve para probar sin instalar nada.\n'
            'Vite reenvía /api al backend (proxy en vite.config.ts), así el navegador ve un solo origen.\n'
            'El código compila con Java 17 (pom.xml y Dockerfile); en local corre con JDK 17 o superior.',
       60, 360, 760, 120)

v.guardar(os.path.join(OUT, 'DroneMission-Pro-Vistas.vpp'), os.path.join(OUT, 'png'), SOLO)
from PIL import Image
for f in os.listdir(os.path.join(OUT, 'png')):
    if f.endswith('.png'):
        ruta_png = os.path.join(OUT, 'png', f); im = Image.open(ruta_png).convert('RGBA')
        fondo = Image.new('RGB', im.size, 'white'); fondo.paste(im, mask=im.split()[3]); fondo.save(ruta_png)

