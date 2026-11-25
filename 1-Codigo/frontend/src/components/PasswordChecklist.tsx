// ============================================================================================
// DOCUMENTACIÓN · src/components/PasswordChecklist.tsx
//
// QUÉ ES:        La lista en vivo de requisitos de contraseña.
// QUÉ HACE:      Mientras escribes, marca en verde lo que ya cumple (mayúscula,
//                número, símbolo...).
// SI LO CAMBIAS: Las reglas están en utils/password.ts, no aquí.
// ============================================================================================
import { Box, LinearProgress, Typography } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import RadioButtonUncheckedIcon from '@mui/icons-material/RadioButtonUnchecked';
import { passwordRules } from '../utils/password';

/** Muestra en vivo qué requisitos de contraseña segura se cumplen. */
export default function PasswordChecklist({ password, personal = [] }: { password: string; personal?: string[] }) {
  const rules = passwordRules(password, personal);
  const passed = rules.filter((r) => r.ok).length;
  const color = passed === rules.length ? 'success' : passed >= 3 ? 'warning' : 'error';
  return (
    <Box>
      <LinearProgress variant="determinate" value={(passed / rules.length) * 100} color={color} sx={{ height: 6, borderRadius: 3, mb: 1 }} />
      {rules.map((r) => (
        <Box key={r.label} sx={{ display: 'flex', alignItems: 'center', gap: 0.75, color: r.ok ? 'success.main' : 'text.secondary' }}>
          {r.ok ? <CheckCircleIcon sx={{ fontSize: 16 }} /> : <RadioButtonUncheckedIcon sx={{ fontSize: 16 }} />}
          <Typography variant="caption">{r.label}</Typography>
        </Box>
      ))}
    </Box>
  );
}
