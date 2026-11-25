// ============================================================================================
// DOCUMENTACIÓN · src/pages/ForbiddenPage.tsx
//
// QUÉ ES:        La pantalla "Acceso prohibido" (403).
// QUÉ HACE:      Se muestra cuando intentas entrar a una página sin el permiso.
// SI LO CAMBIAS: Solo texto y un botón para volver.
// ============================================================================================
import { Box, Button, Typography } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { Link } from 'react-router-dom';

/** Componente React ForbiddenPage: lo que se ve en pantalla. */
export default function ForbiddenPage() {
  return (
    <Box sx={{ textAlign: 'center', py: 10 }}>
      <LockOutlinedIcon sx={{ fontSize: 56, color: 'text.secondary' }} />
      <Typography variant="h5" sx={{ mt: 2 }}>Acceso restringido</Typography>
      <Typography color="text.secondary" sx={{ mb: 3 }}>Tu rol no tiene permiso para ver esta sección.</Typography>
      <Button component={Link} to="/" variant="contained">Volver al inicio</Button>
    </Box>
  );
}
