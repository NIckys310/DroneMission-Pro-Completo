// ============================================================================================
// DOCUMENTACIÓN · src/components/StatusChip.tsx
//
// QUÉ ES:        La "pastilla" de color para los estados.
// QUÉ HACE:      Recibe el valor (ej. EN_VUELO) y un diccionario de labels.ts que dice
//                el texto y el color.
// SI LO CAMBIAS: Los textos y colores se cambian en utils/labels.ts.
// ============================================================================================
import { Chip, type ChipProps } from '@mui/material';
import type { ChipColor } from '../utils/labels';

interface Props {
  /** Valor. */
  value: string;
  dict: Record<string, { label: string; color: ChipColor }>;
  /** Tamaño (opcional). */
  size?: ChipProps['size'];
  /** Variante (opcional). */
  variant?: ChipProps['variant'];
}

/** Chip de color para cualquier estado (misión, drone, piloto, mantenimiento...). */
export default function StatusChip({ value, dict, size = 'small', variant = 'filled' }: Props) {
  const meta = dict[value] ?? { label: value, color: 'default' as ChipColor };
  return <Chip label={meta.label} color={meta.color} size={size} variant={variant} />;
}
