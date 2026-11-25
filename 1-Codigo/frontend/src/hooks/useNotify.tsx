// ============================================================================================
// DOCUMENTACIÓN · src/hooks/useNotify.tsx
//
// QUÉ ES:        Los mensajitos emergentes (abajo de la pantalla).
// QUÉ HACE:      notify("Guardado") muestra un aviso verde, rojo, azul o naranja que
//                se oculta solo.
// SI LO CAMBIAS: autoHideDuration es cuánto dura en pantalla (5000 = 5 s).
// ============================================================================================
import { createContext, useCallback, useContext, useState, type ReactNode } from 'react';
import { Alert, Snackbar } from '@mui/material';

type Severity = 'success' | 'error' | 'info' | 'warning';
type Notify = (message: string, severity?: Severity) => void;

/** Función NotifyContext: Context de avisar. */
const NotifyContext = createContext<Notify>(() => undefined);

/** Mensajes emergentes (snackbar) para confirmar acciones o mostrar errores. */
export function NotifyProvider({ children }: { children: ReactNode }) {
  // Estado del componente: estado.
  const [state, setState] = useState<{ open: boolean; message: string; severity: Severity }>({
    open: false, message: '', severity: 'success',
  });
  const notify = useCallback<Notify>((message, severity = 'success') => setState({ open: true, message, severity }), []);

  return (
    <NotifyContext.Provider value={notify}>
      {children}
      <Snackbar
        open={state.open}
        autoHideDuration={5000}
        onClose={() => setState((s) => ({ ...s, open: false }))}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
      >
        <Alert variant="filled" severity={state.severity} onClose={() => setState((s) => ({ ...s, open: false }))}>
          {state.message}
        </Alert>
      </Snackbar>
    </NotifyContext.Provider>
  );
}

/** Función useNotify: Hook: usa avisar. */
export const useNotify = () => useContext(NotifyContext);
