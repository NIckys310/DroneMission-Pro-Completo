// ============================================================================================
// DOCUMENTACIÓN · src/components/InfoItem.tsx
//
// QUÉ ES:        Un par "etiqueta: valor".
// QUÉ HACE:      Se usa en las páginas de detalle (drone, piloto, misión).
// SI LO CAMBIAS: Si cambias el estilo aquí, cambia en todos los detalles.
// ============================================================================================
import { Box, Typography } from '@mui/material';
import type { ReactNode } from 'react';

/** Par etiqueta / valor para las vistas de detalle. */
export default function InfoItem({ label, value }: { label: string; value: ReactNode }) {
  return (
    <Box sx={{ minWidth: 0 }}>
      <Typography variant="caption" color="text.secondary" sx={{ textTransform: 'uppercase', letterSpacing: 0.4, fontWeight: 600 }}>
        {label}
      </Typography>
      <Box sx={{ mt: 0.25, fontSize: 14, wordBreak: 'break-word' }}>{value ?? '—'}</Box>
    </Box>
  );
}
