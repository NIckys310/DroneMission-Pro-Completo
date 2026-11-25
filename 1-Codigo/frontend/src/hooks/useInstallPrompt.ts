// ============================================================================================
// DOCUMENTACIÓN · src/hooks/useInstallPrompt.ts
//
// QUÉ ES:        Lo que permite instalar la app como aplicación (PWA).
// QUÉ HACE:      Detecta si el navegador deja instalarla y muestra el botón
//                "Instalar".
// SI LO CAMBIAS: Si lo quitas, la app sigue funcionando pero sin botón de instalar.
// ============================================================================================
import { useEffect, useState } from 'react';

interface BeforeInstallPromptEvent extends Event {
  /** Aviso. */
  prompt: () => Promise<void>;
  /** Choice de usuario. */
  userChoice: Promise<{ outcome: 'accepted' | 'dismissed' }>;
}

/**
 * Detecta si el navegador permite instalar la aplicación (Chrome, Edge, Android)
 * y expone una función para mostrar el diálogo de instalación.
 */
export function useInstallPrompt() {
  // Estado del componente: evento.
  const [event, setEvent] = useState<BeforeInstallPromptEvent | null>(null);
  // Estado del componente: instalada.
  const [installed, setInstalled] = useState(() => window.matchMedia?.('(display-mode: standalone)').matches ?? false);

  useEffect(() => {
    const onPrompt = (e: Event) => { e.preventDefault(); setEvent(e as BeforeInstallPromptEvent); };
    const onInstalled = () => { setInstalled(true); setEvent(null); };
    window.addEventListener('beforeinstallprompt', onPrompt);
    window.addEventListener('appinstalled', onInstalled);
    return () => {
      window.removeEventListener('beforeinstallprompt', onPrompt);
      window.removeEventListener('appinstalled', onInstalled);
    };
  }, []);

  const install = async () => {
    if (!event) return;
    await event.prompt();
    await event.userChoice;
    setEvent(null);
  };

  return { canInstall: !!event && !installed, installed, install };
}
