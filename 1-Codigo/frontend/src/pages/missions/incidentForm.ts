// ============================================================================================
// DOCUMENTACIÓN · src/pages/missions/incidentForm.ts
//
// QUÉ ES:        Los campos del formulario para reportar un incidente.
// QUÉ HACE:      Tipo, gravedad y descripción.
// SI LO CAMBIAS: Campo nuevo: una línea aquí + el backend.
// ============================================================================================
import { incidentType, options, severity } from '../../utils/labels';
import type { FieldConfig } from '../../utils/validation';

/** Lista incidentFields: campos de incidente. */
export const incidentFields: FieldConfig[] = [
  { name: 'type', label: 'Tipo de incidente', type: 'select', required: true, options: options(incidentType) },
  { name: 'severity', label: 'Severidad', type: 'select', required: true, options: options(severity), helperText: 'Crítica: pausa automáticamente una misión en vuelo' },
  { name: 'description', label: 'Descripción', type: 'textarea', required: true, maxLength: 1000, cols: 12 },
];
