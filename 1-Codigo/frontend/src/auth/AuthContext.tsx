// ============================================================================================
// DOCUMENTACIÓN · src/auth/AuthContext.tsx
//
// QUÉ ES:        La "memoria" de la sesión.
// QUÉ HACE:      Guarda quién está conectado y da can("PERMISO") para mostrar u
//                ocultar cosas según el rol. Maneja login y logout.
// SI LO CAMBIAS: Para mostrar algo solo a un rol: const { can } = useAuth(); y
//                {can("PERMISO") && ...}.
// ============================================================================================
import { createContext, useCallback, useContext, useEffect, useMemo, useState, type ReactNode } from 'react';
import { authApi } from '../api/endpoints';
import { TOKEN_KEY } from '../api/client';
import type { RoleName, UserInfo } from '../types';

interface AuthState {
  /** Usuario. */
  user: UserInfo | null;
  /** Cargando. */
  loading: boolean;
  /** Inicio de sesión. */
  login: (identifier: string, password: string) => Promise<UserInfo>;
  /** Logout. */
  logout: () => Promise<void>;
  /** Puede. */
  can: (permission: string) => boolean;
  /** Rol de tiene. */
  hasRole: (...roles: RoleName[]) => boolean;
}

/** AuthContext: context de auth. */
const AuthContext = createContext<AuthState | null>(null);

/** Guarda la sesión del usuario y expone helpers de permisos para toda la app. */
export function AuthProvider({ children }: { children: ReactNode }) {
  // Estado del componente: usuario.
  const [user, setUser] = useState<UserInfo | null>(null);
  // Estado del componente: cargando.
  const [loading, setLoading] = useState(true);

  // Al recargar la página, si hay token se recupera el usuario desde /auth/me
  useEffect(() => {
    if (!localStorage.getItem(TOKEN_KEY)) {
      setLoading(false);
      return;
    }
    authApi.me()
      .then(setUser)
      .catch(() => localStorage.removeItem(TOKEN_KEY))
      .finally(() => setLoading(false));
  }, []);

  const login = useCallback(async (identifier: string, password: string) => {
    const response = await authApi.login(identifier, password);
    localStorage.setItem(TOKEN_KEY, response.token);
    setUser(response.user);
    return response.user;
  }, []);

  const logout = useCallback(async () => {
    try {
      await authApi.logout();
    } finally {
      localStorage.removeItem(TOKEN_KEY);
      setUser(null);
    }
  }, []);

  const value = useMemo<AuthState>(() => ({
    user,
    loading,
    login,
    logout,
    can: (permission) => !!user?.permissions.includes(permission),
    hasRole: (...roles) => !!user && roles.includes(user.role),
  }), [user, loading, login, logout]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

/** Hook: usa auth. */
export function useAuth(): AuthState {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth debe usarse dentro de AuthProvider');
  return ctx;
}
