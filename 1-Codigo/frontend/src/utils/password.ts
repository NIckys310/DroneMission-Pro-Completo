// ============================================================================================
// DOCUMENTACIÓN · src/utils/password.ts
//
// QUÉ ES:        Las reglas de contraseña del lado de la pantalla.
// QUÉ HACE:      Las mismas que PasswordPolicy.java: largo, mayúscula, minúscula,
//                número, símbolo, sin datos personales.
// SI LO CAMBIAS: Si cambias una regla aquí, cámbiala también en el backend.
// ============================================================================================
// Requisitos de contraseña segura (los mismos que valida el backend en PasswordPolicy.java).
export interface PasswordRule { label: string; ok: boolean }

/** Reglas de contraseña. */
export function passwordRules(password: string, personal: string[] = []): PasswordRule[] {
  const lower = password.toLowerCase();
  const containsPersonal = personal.some((p) => p && p.length >= 3 && lower.includes(p.toLowerCase()));
  return [
    { label: 'Al menos 8 caracteres', ok: password.length >= 8 },
    { label: 'Una letra mayúscula', ok: /[A-ZÁÉÍÓÚÑ]/.test(password) },
    { label: 'Una letra minúscula', ok: /[a-záéíóúñ]/.test(password) },
    { label: 'Un número', ok: /\d/.test(password) },
    { label: 'No contiene tu nombre, usuario o correo', ok: password.length > 0 && !containsPersonal },
  ];
}

/** Función isStrongPassword: Indica si es contraseña segura. */
export const isStrongPassword = (password: string, personal: string[] = []) =>
  passwordRules(password, personal).every((r) => r.ok);

/** Constante USERNAME_REGEX: expresión regular de nombre de usuario. */
export const USERNAME_REGEX = /^[a-zA-Z0-9._-]{3,30}$/;
