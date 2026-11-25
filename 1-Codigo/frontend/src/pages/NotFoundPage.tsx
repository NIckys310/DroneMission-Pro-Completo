// ============================================================================================
// DOCUMENTACIÓN · src/pages/NotFoundPage.tsx
//
// QUÉ ES:        La pantalla "Página no encontrada" (404).
// QUÉ HACE:      Se ve cuando escribes una dirección que no existe.
// SI LO CAMBIAS: Solo texto y un botón para volver.
// ============================================================================================
import { Box, Button, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

/** Componente React NotFoundPage: lo que se ve en pantalla. */
export default function NotFoundPage() {
  return (
    <Box sx={{ textAlign: 'center', py: 10 }}>
      <Typography variant="h3" color="primary">404</Typography>
      <Typography variant="h6" sx={{ mb: 3 }}>La página no existe</Typography>
      <Button component={Link} to="/" variant="contained">Volver al inicio</Button>
    </Box>
  );
}
