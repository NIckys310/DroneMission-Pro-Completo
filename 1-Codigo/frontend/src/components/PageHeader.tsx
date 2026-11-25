// ============================================================================================
// DOCUMENTACIÓN · src/components/PageHeader.tsx
//
// QUÉ ES:        El título de cada página.
// QUÉ HACE:      Muestra título, subtítulo, migas de pan (breadcrumbs) y botones de la
//                derecha.
// SI LO CAMBIAS: Cambia el estilo del encabezado en todas las pantallas a la vez.
// ============================================================================================
import { Box, Breadcrumbs, Link, Typography } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import type { ReactNode } from 'react';

interface Props {
  /** Título. */
  title: string;
  /** Subtítulo (opcional). */
  subtitle?: string;
  /** Acciones (opcional). */
  actions?: ReactNode;
  breadcrumbs?: { label: string; to?: string }[];
}

/** Componente React PageHeader: lo que se ve en pantalla. Props: title, subtitle, actions, breadcrumbs. */
export default function PageHeader({ title, subtitle, actions, breadcrumbs }: Props) {
  return (
    <Box sx={{ mb: 3 }}>
      {breadcrumbs && (
        <Breadcrumbs sx={{ mb: 1, fontSize: 13 }}>
          {breadcrumbs.map((b) =>
            b.to ? (
              <Link key={b.label} component={RouterLink} to={b.to} underline="hover" color="inherit">{b.label}</Link>
            ) : (
              <Typography key={b.label} fontSize={13} color="text.primary">{b.label}</Typography>
            ),
          )}
        </Breadcrumbs>
      )}
      <Box sx={{ display: 'flex', alignItems: { xs: 'flex-start', sm: 'center' }, justifyContent: 'space-between', gap: 2, flexDirection: { xs: 'column', sm: 'row' } }}>
        <Box>
          <Typography variant="h5">{title}</Typography>
          {subtitle && <Typography color="text.secondary" variant="body2" sx={{ mt: 0.5 }}>{subtitle}</Typography>}
        </Box>
        {actions && <Box sx={{ display: 'flex', gap: 1, flexWrap: 'wrap' }}>{actions}</Box>}
      </Box>
    </Box>
  );
}
