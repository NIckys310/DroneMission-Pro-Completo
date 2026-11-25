// ============================================================================================
// DOCUMENTACIÓN · src/utils/validation.ts
//
// QUÉ ES:        Las validaciones de los formularios.
// QUÉ HACE:      Revisa obligatorios, correos, teléfonos, mínimos y máximos antes de
//                enviar. El backend SIEMPRE vuelve a validar.
// SI LO CAMBIAS: Tipo de validación nuevo: agrégalo en validateField().
// ============================================================================================
// Validaciones del lado del cliente. Mejoran la experiencia, pero el backend
// SIEMPRE vuelve a validar (nunca se confía solo en el frontend).

export type FieldType = 'text' | 'email' | 'number' | 'date' | 'time' | 'select' | 'textarea' | 'password' | 'checkbox';

export interface FieldConfig {
  /** Nombre. */
  name: string;
  /** Etiqueta. */
  label: string;
  /** Tipo (opcional). */
  type?: FieldType;
  /** Required (opcional). */
  required?: boolean;
  options?: { value: string | number; label: string }[];
  /** Mínima (opcional). */
  min?: number;
  /** Máxima (opcional). */
  max?: number;
  /** Length mínima (opcional). */
  minLength?: number;
  /** Length máxima (opcional). */
  maxLength?: number;
  pattern?: { regex: RegExp; message: string };
  /** Text de helper (opcional). */
  helperText?: string;
  /** Ancho en la grilla de 12 columnas (por defecto 6). */
  cols?: number;
  /** Disabled (opcional). */
  disabled?: boolean;
  /** Regla adicional: devuelve el mensaje de error o null. */
  validate?: (value: unknown, values: Record<string, unknown>) => string | null;
}

/** Constante EMAIL_REGEX: expresión regular de correo. */
export const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
/** Constante PHONE_REGEX: expresión regular de teléfono. */
export const PHONE_REGEX = /^[0-9+()\s-]{7,30}$/;

/** Valida campo. */
export function validateField(field: FieldConfig, value: unknown, values: Record<string, unknown>): string | null {
  const empty = value === undefined || value === null || value === '';
  if (field.required && empty) return `${field.label} es obligatorio`;
  if (empty) return null;

  const text = String(value);
  if (field.type === 'email' && !EMAIL_REGEX.test(text)) return 'El correo no es válido';
  if (field.type === 'number') {
    const n = Number(value);
    if (Number.isNaN(n)) return 'Debe ser un número';
    if (field.min !== undefined && n < field.min) return `El valor mínimo es ${field.min}`;
    if (field.max !== undefined && n > field.max) return `El valor máximo es ${field.max}`;
  }
  if (field.minLength && text.length < field.minLength) return `Mínimo ${field.minLength} caracteres`;
  if (field.maxLength && text.length > field.maxLength) return `Máximo ${field.maxLength} caracteres`;
  if (field.pattern && !field.pattern.regex.test(text)) return field.pattern.message;
  return field.validate ? field.validate(value, values) : null;
}

/** Valida todos. */
export function validateAll(fields: FieldConfig[], values: Record<string, unknown>): Record<string, string> {
  const errors: Record<string, string> = {};
  for (const field of fields) {
    const error = validateField(field, values[field.name], values);
    if (error) errors[field.name] = error;
  }
  return errors;
}

/** Convierte strings vacíos en null y los campos numéricos en number antes de enviar. */
export function normalize(fields: FieldConfig[], values: Record<string, unknown>): Record<string, unknown> {
  const result: Record<string, unknown> = { ...values };
  for (const field of fields) {
    const v = values[field.name];
    if (v === '' || v === undefined) result[field.name] = null;
    else if (field.type === 'number') result[field.name] = Number(v);
  }
  return result;
}
