// ============================================================================================
// DOCUMENTACIÓN · src/pages/home/HomePage.tsx
//
// QUÉ ES:        El "repartidor" del inicio.
// QUÉ HACE:      Según el rol muestra el dashboard de operaciones, el inicio del
//                piloto o el del cliente.
// SI LO CAMBIAS: Rol nuevo con inicio propio: agrega aquí su caso.
// ============================================================================================
import { useAuth } from '../../auth/AuthContext';
import OpsDashboard from './OpsDashboard';
import PilotHome from './PilotHome';
import ClientHome from './ClientHome';

/** La pantalla de inicio depende del rol: dashboard de operaciones, piloto o cliente. */
export default function HomePage() {
  const { hasRole } = useAuth();
  if (hasRole('PILOTO')) return <PilotHome />;
  if (hasRole('CLIENTE')) return <ClientHome />;
  return <OpsDashboard />;
}
