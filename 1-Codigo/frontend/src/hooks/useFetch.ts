// ============================================================================================
// DOCUMENTACIÓN · src/hooks/useFetch.ts
//
// QUÉ ES:        El "cargador de datos" reutilizable.
// QUÉ HACE:      Llama al backend y entrega { data, loading, error, reload }. Si le
//                pasas pollMs, vuelve a preguntar cada X milisegundos (así se mueve la
//                telemetría en vivo).
// SI LO CAMBIAS: Si lo cambias, cambia en TODAS las pantallas. Para cambiar la
//                frecuencia de una pantalla, cambia el número que esa pantalla le
//                pasa.
// ============================================================================================
import { useCallback, useEffect, useRef, useState } from 'react';
import { errorMessage } from '../api/client';

/**
 * Carga datos del backend y expone { data, loading, error, reload }.
 * Si se indica pollMs, vuelve a consultar periódicamente (telemetría en vivo).
 */
export function useFetch<T>(loader: () => Promise<T>, deps: unknown[] = [], pollMs?: number) {
  // Estado del componente: datos.
  const [data, setData] = useState<T | null>(null);
  // Estado del componente: cargando.
  const [loading, setLoading] = useState(true);
  // Estado del componente: error.
  const [error, setError] = useState<string | null>(null);
  const loaderRef = useRef(loader);
  loaderRef.current = loader;

  const reload = useCallback(async (silent = false) => {
    if (!silent) setLoading(true);
    try {
      setData(await loaderRef.current());
      setError(null);
    } catch (e) {
      setError(errorMessage(e));
    } finally {
      if (!silent) setLoading(false);
    }
  }, []);

  useEffect(() => {
    reload();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  useEffect(() => {
    if (!pollMs) return undefined;
    const timer = setInterval(() => reload(true), pollMs);
    return () => clearInterval(timer);
  }, [pollMs, reload]);

  return { data, loading, error, reload, setData };
}
