// ============================================================================================
// DOCUMENTACIÓN · src/components/Feedback.tsx
//
// QUÉ ES:        Tres mensajitos de estado.
// QUÉ HACE:      Loading (cargando), ErrorBox (error con botón reintentar) y
//                EmptyState (no hay datos).
// SI LO CAMBIAS: Si cambias el texto o el estilo aquí, cambia en todas las pantallas.
// ============================================================================================
import { Alert, Box, Button, CircularProgress, Typography } from '@mui/material';
import InboxOutlinedIcon from '@mui/icons-material/InboxOutlined';

/** Componente React Loading: lo que se ve en pantalla. */
export function Loading() {
  return (
    <Box sx={{ display: 'grid', placeItems: 'center', py: 8 }}>
      <CircularProgress />
    </Box>
  );
}

/** Componente React ErrorBox: lo que se ve en pantalla. Props: message, onRetry. */
export function ErrorBox({ message, onRetry }: { message: string; onRetry?: () => void }) {
  return (
    <Alert severity="error" action={onRetry && <Button color="inherit" size="small" onClick={onRetry}>Reintentar</Button>}>
      {message}
    </Alert>
  );
}

/** Componente React EmptyState: lo que se ve en pantalla. Props: text. */
export function EmptyState({ text }: { text: string }) {
  return (
    <Box sx={{ textAlign: 'center', py: 5, color: 'text.secondary' }}>
      <InboxOutlinedIcon sx={{ fontSize: 40, opacity: 0.5 }} />
      <Typography variant="body2" sx={{ mt: 1 }}>{text}</Typography>
    </Box>
  );
}
