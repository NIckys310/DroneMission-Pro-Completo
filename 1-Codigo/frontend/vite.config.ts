// ============================================================================================
// DOCUMENTACIÓN · vite.config.ts
//
// QUÉ ES:        La configuración de Vite (la herramienta que arranca y compila el
//                frontend).
// QUÉ HACE:      Abre el frontend en el puerto 5173 y redirige /api y /demo al backend
//                (8080) para evitar problemas de CORS. También separa las librerías
//                grandes en archivos aparte.
// SI LO CAMBIAS: Si el backend corre en otro puerto, cambia "backend" o usa
//                VITE_BACKEND_URL. Si cambias el 5173, agrégalo también en CORS
//                (application.yml).
// ============================================================================================
import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';

// En desarrollo, Vite redirige /api y /demo al backend (evita problemas de CORS).
// >> Dirección del backend al que se mandan /api y /demo. Si el backend corre en otro puerto, cámbialo aquí.
const backend = process.env.VITE_BACKEND_URL ?? 'http://localhost:8080';

export default defineConfig({
  plugins: [react()],
  server: {
    // >> Puerto del frontend en desarrollo (http://localhost:5173).
    port: 5173,
    proxy: {
      '/api': backend,
      '/demo': backend,
    },
  },
  build: {
    // El simulador 3D (Three.js) pesa ~530 kB y se carga aparte, solo al abrirlo
    chunkSizeWarningLimit: 600,
    rollupOptions: {
      output: {
        // Separa las librerías grandes en archivos propios (mejor caché del navegador)
        manualChunks: {
          react: ['react', 'react-dom', 'react-router-dom'],
          mui: ['@mui/material', '@mui/icons-material', '@emotion/react', '@emotion/styled'],
          charts: ['recharts'],
        },
      },
    },
  },
  test: {
    environment: 'node',
  },
});
