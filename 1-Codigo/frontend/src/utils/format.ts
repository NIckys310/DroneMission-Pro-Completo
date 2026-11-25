// ============================================================================================
// DOCUMENTACIÓN · src/utils/format.ts
//
// QUÉ ES:        Formateadores.
// QUÉ HACE:      Fechas en español, hora, "hace 5 min", dinero en pesos (COP), números
//                y duraciones.
// SI LO CAMBIAS: Cambia el formato de fecha o de moneda aquí y cambia en toda la app.
// ============================================================================================
import dayjs from 'dayjs';
import 'dayjs/locale/es';

dayjs.locale('es');

/** Función formatDate: Da formato a fecha. */
export const formatDate = (value?: string | null) => (value ? dayjs(value).format('DD MMM YYYY') : '—');
/** Función formatDateTime: Da formato a hora de fecha. */
export const formatDateTime = (value?: string | null) => (value ? dayjs(value).format('DD MMM YYYY, HH:mm') : '—');
/** Función formatTime: Da formato a hora. */
export const formatTime = (value?: string | null) => (value ? value.slice(0, 5) : '—');
/** Función fromNow: Desde ahora. */
export const fromNow = (value: string) => {
  const minutes = dayjs().diff(dayjs(value), 'minute');
  if (minutes < 1) return 'hace un momento';
  if (minutes < 60) return `hace ${minutes} min`;
  const hours = Math.floor(minutes / 60);
  if (hours < 24) return `hace ${hours} h`;
  return formatDate(value);
};

/** money: dinero. */
const money = new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 });
/** Función formatMoney: Da formato a dinero. */
export const formatMoney = (value: number) => money.format(value);

/** Función formatNumber: Da formato a número. */
export const formatNumber = (value: number, decimals = 0) =>
  new Intl.NumberFormat('es-CO', { maximumFractionDigits: decimals, minimumFractionDigits: decimals }).format(value);

/** Función formatDuration: Da formato a duración. */
export const formatDuration = (seconds: number) => {
  const m = Math.floor(seconds / 60);
  const s = seconds % 60;
  return `${m}:${String(s).padStart(2, '0')}`;
};

/** Función today: Hoy. */
export const today = () => dayjs().format('YYYY-MM-DD');
