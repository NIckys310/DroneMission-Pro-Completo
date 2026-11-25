// ============================================================================================
// DOCUMENTACIÓN · src/theme.ts
//
// QUÉ ES:        LOS COLORES Y LA LETRA de toda la aplicación.
// QUÉ HACE:      Define el azul principal, el cian, los colores de éxito/alerta/error,
//                el fondo, la letra, lo redondeado de las esquinas y el estilo de
//                botones, tarjetas y tablas.
// SI LO CAMBIAS: Cambia "primary.main" y TODOS los botones y la barra cambian de
//                color. borderRadius cambia lo redondeado. Nada se rompe: solo cambia
//                la apariencia.
// ============================================================================================
import { createTheme } from '@mui/material/styles';
import { esES } from '@mui/material/locale';

// Identidad visual: azul aeronáutico + cian técnico, limpio y profesional.
export const theme = createTheme(
  {
    palette: {
      mode: 'light',
      // >> COLOR PRINCIPAL (azul): botones, barra superior, enlaces. Cámbialo y cambia toda la app.
      primary: { main: '#0e5a9c', dark: '#0b3a63', light: '#4f8fcf' },
      // >> Color secundario (cian).
      secondary: { main: '#00838f' },
      // >> Verde de "todo bien" (ej. Disponible, Finalizada).
      success: { main: '#2e7d32' },
      // >> Naranja de alerta (ej. batería media, En preparación).
      warning: { main: '#ed8b00' },
      // >> Rojo de error o peligro (ej. Cancelada, botón Borrar).
      error: { main: '#c62828' },
      info: { main: '#0277bd' },
      // >> Fondo gris claro de la app y blanco de las tarjetas.
      background: { default: '#f3f6fa', paper: '#ffffff' },
      text: { primary: '#132238', secondary: '#5b6b80' },
      divider: '#e2e8f0',
    },
    // >> Qué tan redondeadas son las esquinas (0 = cuadradas).
    shape: { borderRadius: 10 },
    typography: {
      // >> Tipo de letra de toda la app.
      fontFamily: '"Inter", "Segoe UI", Roboto, Helvetica, Arial, sans-serif',
      h4: { fontWeight: 700, letterSpacing: -0.5 },
      h5: { fontWeight: 700, letterSpacing: -0.3 },
      h6: { fontWeight: 650 },
      subtitle2: { fontWeight: 600 },
      // >> Los botones no van en MAYÚSCULAS. Pon 'uppercase' para que sí.
      button: { textTransform: 'none', fontWeight: 600 },
    },
    components: {
      MuiCard: {
        defaultProps: { elevation: 0 },
        styleOverrides: { root: { border: '1px solid #e2e8f0' } },
      },
      MuiPaper: { styleOverrides: { rounded: { borderRadius: 12 } } },
      MuiButton: { defaultProps: { disableElevation: true } },
      MuiTableCell: {
        styleOverrides: {
          head: { fontWeight: 600, color: '#5b6b80', backgroundColor: '#f8fafc', fontSize: 13 },
        },
      },
      MuiChip: { styleOverrides: { root: { fontWeight: 600 } } },
      MuiTextField: { defaultProps: { size: 'small', fullWidth: true } },
    },
  },
  esES,
);
