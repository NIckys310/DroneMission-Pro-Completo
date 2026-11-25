// ============================================================================================
// DOCUMENTACIÓN · src/pages/clients/clientForm.ts
//
// QUÉ ES:        Los campos del formulario de cliente.
// QUÉ HACE:      Cada objeto es un campo: nombre, tipo, si es obligatorio, largo
//                máximo.
// SI LO CAMBIAS: Campo nuevo: agrega una línea aquí (y el atributo en el backend).
// ============================================================================================
import { clientType, options } from '../../utils/labels';
import { PHONE_REGEX, type FieldConfig } from '../../utils/validation';

/** Lista clientFields: campos de cliente. */
export const clientFields: FieldConfig[] = [
  { name: 'type', label: 'Tipo de cliente', type: 'select', required: true, options: options(clientType), cols: 4 },
  { name: 'name', label: 'Nombre o razón social', required: true, maxLength: 150, cols: 8 },
  { name: 'document', label: 'Documento / NIT', required: true, maxLength: 30, cols: 4 },
  { name: 'email', label: 'Correo', type: 'email', required: true, cols: 4 },
  { name: 'phone', label: 'Teléfono', required: true, cols: 4, pattern: { regex: PHONE_REGEX, message: 'El teléfono no es válido' } },
  { name: 'address', label: 'Dirección', required: true, maxLength: 200, cols: 8 },
  { name: 'city', label: 'Ciudad', required: true, maxLength: 80, cols: 4 },
];
