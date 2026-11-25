// ============================================================================================
// DOCUMENTACIÓN · src/main.tsx
//
// QUÉ ES:        El punto de arranque del frontend.
// QUÉ HACE:      Monta React en la página, aplica el tema de colores (theme.ts),
//                activa el router, la sesión (AuthProvider) y los mensajes emergentes.
//                En producción registra el service worker (app instalable).
// SI LO CAMBIAS: Si quitas un Provider de aquí, toda la app pierde esa función (por
//                ejemplo, sin AuthProvider nadie puede iniciar sesión).
// ============================================================================================
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { CssBaseline, ThemeProvider } from '@mui/material';
import '@fontsource/inter/400.css';
import '@fontsource/inter/500.css';
import '@fontsource/inter/600.css';
import '@fontsource/inter/700.css';
import App from './App';
import { theme } from './theme';
import { AuthProvider } from './auth/AuthContext';
import { NotifyProvider } from './hooks/useNotify';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <AuthProvider>
          <NotifyProvider>
            <App />
          </NotifyProvider>
        </AuthProvider>
      </BrowserRouter>
    </ThemeProvider>
  </React.StrictMode>,
);

// Aplicación instalable (PWA): el service worker solo se registra en producción
if ('serviceWorker' in navigator && import.meta.env.PROD) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/sw.js').catch(() => undefined);
  });
}
